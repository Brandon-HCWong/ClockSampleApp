package com.brandonhc.clocksampleapp.ui.custom

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Paint.Cap
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorRes
import com.brandonhc.clocksampleapp.R
import org.joda.time.LocalDateTime
import org.joda.time.Minutes.minutesBetween
import kotlin.math.abs

class AnalogClockDrawable(resources: Resources, private var backgroundVectorDrawable: VectorDrawable) :
    Drawable(), Animatable {
    private val facePaint: Paint
    private val rimPaint: Paint
    private val minAnimator: ValueAnimator
    private val hourAnimator: ValueAnimator
    private var rimRadius = 0f
    private var faceRadius = 0f
    private var screwRadius = 0f
    private val hourHandPath: Path
    private val minuteHandPath: Path
    private var remainingHourRotation = 0f
    private var remainingMinRotation = 0f
    private var targetHourRotation = 0f
    private var targetMinRotation = 0f
    private var currentHourRotation = 0f
    private var currentMinRotation = 0f
    private var hourAnimInterrupted = false
    private var minAnimInterrupted = false
    private var previousTime: LocalDateTime
    private var animateDays = true

    init {
        facePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        facePaint.color = resources.getColor(FACE_COLOR)
        facePaint.style = Paint.Style.FILL
        rimPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        rimPaint.color = resources.getColor(RIM_COLOR)
        rimPaint.style = Paint.Style.STROKE
        rimPaint.strokeCap = Cap.ROUND
        rimPaint.strokeWidth = 20f
        hourHandPath = Path()
        minuteHandPath = Path()
        hourAnimator = ValueAnimator.ofFloat(0f, 0f)
        hourAnimator.interpolator = AccelerateDecelerateInterpolator()
        hourAnimator.setDuration(ANIMATION_DURATION.toLong())
        hourAnimator.addUpdateListener { valueAnimator ->
            val fraction = valueAnimator.animatedValue as Float
            //d("ANIM", "Hfraction = " + fraction + ", remaining hour rotation = " + remainingHourRotation);
            remainingHourRotation = targetHourRotation - fraction
            currentHourRotation = fraction
            invalidateSelf()
        }
        hourAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (!hourAnimInterrupted) {
                    remainingHourRotation = 0f
                }
                //i("ANIM", "END! remaining hour rotation = " + remainingHourRotation);
            }
        })
        minAnimator = ValueAnimator.ofFloat(0f, 0f)
        minAnimator.interpolator = AccelerateDecelerateInterpolator()
        minAnimator.setDuration(ANIMATION_DURATION.toLong())
        minAnimator.addUpdateListener { valueAnimator ->
            val fraction = valueAnimator.animatedValue as Float
            //d("ANIM", "Mfraction = " + fraction + ", remaining minute rotation = " + remainingMinRotation);
            remainingMinRotation = targetMinRotation - fraction
            currentMinRotation = fraction
            invalidateSelf()
        }
        minAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (!minAnimInterrupted) {
                    remainingMinRotation = 0f
                }
                //i("ANIM", "END! remaining minute rotation = " + remainingMinRotation);
            }
        })
        previousTime = LocalDateTime.now().withTime(0, 0, 0, 0)
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)

//        rimPaint.setStrokeWidth();
        rimRadius = Math.min(bounds.width(), bounds.height()) / 2f - rimPaint.strokeWidth
        faceRadius = rimRadius - rimPaint.strokeWidth
        screwRadius = rimPaint.strokeWidth * 2
        val hourHandLength = (0.25 * faceRadius).toFloat()
        val minuteHandLength = (0.45 * faceRadius).toFloat()
        val top = bounds.centerY() - screwRadius
        hourHandPath.reset()
        hourHandPath.moveTo(bounds.centerX().toFloat(), bounds.centerY().toFloat())
        hourHandPath.addRect(
            bounds.centerX().toFloat(),
            top,
            bounds.centerX().toFloat(),
            top - hourHandLength,
            Path.Direction.CCW
        )
        hourHandPath.close()
        minuteHandPath.reset()
        minuteHandPath.moveTo(bounds.centerX().toFloat(), bounds.centerY().toFloat())
        minuteHandPath.addRect(
            bounds.centerX().toFloat(),
            top,
            bounds.centerX().toFloat(),
            top - minuteHandLength,
            Path.Direction.CCW
        )
        minuteHandPath.close()
    }

    override fun draw(canvas: Canvas) {
        val bounds = bounds
        backgroundVectorDrawable.bounds = calculateBackgroundDrawableRect(bounds)
        backgroundVectorDrawable.draw(canvas)

        canvas.drawCircle(
            bounds.centerX().toFloat(),
            bounds.centerY().toFloat(),
            screwRadius,
            rimPaint
        )
        var saveCount = canvas.save()
        canvas.rotate(currentHourRotation, bounds.centerX().toFloat(), bounds.centerY().toFloat())
        // draw hour hand
        canvas.drawPath(hourHandPath, rimPaint)
        canvas.restoreToCount(saveCount)
        saveCount = canvas.save()
        canvas.rotate(currentMinRotation, bounds.centerX().toFloat(), bounds.centerY().toFloat())
        // draw minute hand
        canvas.drawPath(minuteHandPath, rimPaint)
        canvas.restoreToCount(saveCount)
    }

    override fun setAlpha(alpha: Int) {
        rimPaint.alpha = alpha
        facePaint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        rimPaint.setColorFilter(colorFilter)
        invalidateSelf()
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun start() {
        hourAnimInterrupted = false
        minAnimInterrupted = false
        hourAnimator.start()
        minAnimator.start()
    }

    fun setAnimateDays(animateDays: Boolean) {
        this.animateDays = animateDays
    }

    fun start(newTime: LocalDateTime) {
        val minDiff: Int = getMinutesBetween(previousTime, newTime)
        // 60min ... 360grade
        // minDif .. minDelta
        val minDeltaRotation = minDiff.toFloat() * 360f / 60f
        // 720min ... 360grade = 12h ... 360grade
        // minDif ... hourDelta
        val hourDeltaRotation = minDiff.toFloat() * 360f / 720f
        remainingMinRotation += minDeltaRotation
        remainingHourRotation += hourDeltaRotation
        Log.d(
            "ANIM",
            "current hour rotation = $currentHourRotation, current min rotation = $currentMinRotation"
        )
        if (isRunning) {
            stop()
        }
        targetHourRotation = currentHourRotation + remainingHourRotation
        hourAnimator.setFloatValues(currentHourRotation, targetHourRotation)
        targetMinRotation = currentMinRotation + remainingMinRotation
        minAnimator.setFloatValues(currentMinRotation, targetMinRotation)
        start()
        previousTime = newTime
    }

    override fun stop() {
        hourAnimInterrupted = true
        minAnimInterrupted = true
        hourAnimator.cancel()
        minAnimator.cancel()
    }

    override fun isRunning(): Boolean {
        return hourAnimator.isRunning || minAnimator.isRunning
    }

    private fun getMinutesBetween(t1: LocalDateTime, t2: LocalDateTime): Int {
        if(animateDays) {
            return minutesBetween(t1, t2).minutes
        }
        return minutesBetween(t1, t2.withDate(t1.year, t1.monthOfYear, t1.dayOfMonth)).minutes
    }

    private fun calculateBackgroundDrawableRect(originalBounds: Rect): Rect {
        val xOffsetPercent: Float = 0f
        val yOffsetPercent: Float = 2f
        val xOffset = abs((originalBounds.width().toFloat() * (xOffsetPercent / 100f)).toInt())
        val yOffset = abs((originalBounds.height().toFloat() * (yOffsetPercent / 100f)).toInt())
        val maxOffsetPercent = abs(xOffsetPercent).coerceAtLeast(abs(yOffsetPercent))
        val backgroundWidth =
            (originalBounds.width().toFloat() * ((100f - maxOffsetPercent) / 100f)).toInt()
        val backgroundHeight =
            (originalBounds.height().toFloat() * ((100f - maxOffsetPercent) / 100f)).toInt()
        val backgroundBounds = Rect()
        if (xOffsetPercent > 0) {
            backgroundBounds.left = xOffset
        } else if (xOffsetPercent < 0) {
            backgroundBounds.left = 0
        } else {
            backgroundBounds.left = (originalBounds.width() - backgroundWidth) / 2
        }
        backgroundBounds.right = backgroundWidth + backgroundBounds.left

        if (yOffsetPercent > 0) {
            backgroundBounds.top = yOffset
        } else if (yOffsetPercent < 0) {
            backgroundBounds.top = 0
        } else {
            backgroundBounds.top = (originalBounds.height() - backgroundHeight) / 2
        }
        backgroundBounds.bottom = backgroundHeight + backgroundBounds.top

        return backgroundBounds
    }

    companion object {
        private const val ANIMATION_DURATION = 500

        @ColorRes
        private val FACE_COLOR = R.color.white

        @ColorRes
        private val RIM_COLOR: Int = R.color.black
    }
}

