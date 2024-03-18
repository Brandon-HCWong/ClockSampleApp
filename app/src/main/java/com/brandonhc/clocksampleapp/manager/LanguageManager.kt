package com.brandonhc.clocksampleapp.manager

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.brandonhc.clocksampleapp.data.repository.SharedPreferenceRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Locale


object LanguageManager : KoinComponent  {
    private val sharedPreferenceRepository: SharedPreferenceRepository by inject()

    fun wrapContextWithLanguage(context: Context): ContextWrapper {
        val config: Configuration = context.resources.configuration
        val sysLocale: Locale = config.locales.get(0)
        if (sysLocale.language != sharedPreferenceRepository.currentLanguage) {
            val locale = if (sharedPreferenceRepository.currentLanguage.contains("zh")) {
                Locale.TRADITIONAL_CHINESE
            } else {
                Locale.ENGLISH
            }
            Locale.setDefault(locale)
            config.setLocale(locale)
        }
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(config.locales.get(0)))
        return ContextWrapper(context.createConfigurationContext(config))
    }
}