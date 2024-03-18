package com.brandonhc.clocksampleapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.brandonhc.clocksampleapp.R
import com.brandonhc.clocksampleapp.databinding.DialogFragmentTimeZoneIdSelectionBinding
import com.brandonhc.clocksampleapp.ui.fragment.recyclerview.adapter.TimeZoneIdAdapter

class TimeZoneIdSelectionDialogFragment(
    val timeZoneIdList: List<String> = emptyList(),
    val onSelected: (timeZoneId: String) -> Unit = {}
): DialogFragment(), TimeZoneIdAdapter.OnItemInteractionListener {
    private var binding: DialogFragmentTimeZoneIdSelectionBinding? = null
    private lateinit var timeZoneIdAdapter: TimeZoneIdAdapter
    private var test = ArrayList(timeZoneIdList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_ClockSample_Dialog_FullScreenWithStatusBar)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DialogFragmentTimeZoneIdSelectionBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onItemClick(timeZoneId: String, position: Int) {
        onSelected(timeZoneId)
        dismiss()
    }

    private fun DialogFragmentTimeZoneIdSelectionBinding.initViews() {
        root.setOnClickListener {
            dismiss()
        }
        timeZoneIdAdapter = TimeZoneIdAdapter(false, this@TimeZoneIdSelectionDialogFragment).apply {
            submitList(timeZoneIdList)
        }
        rvTimeZoneId.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvTimeZoneId.adapter = timeZoneIdAdapter
    }

    companion object {
        fun show(fm: FragmentManager,
                 timeZoneIdList: List<String>,
                 onSelected: (timeZoneId: String) -> Unit
        ) {
            if (fm.isStateSaved) {
                return
            }
            val fragmentTag = TimeZoneIdSelectionDialogFragment::class.java.simpleName
            if (fm.findFragmentByTag(fragmentTag)?.isVisible == true) {
                return
            }
            TimeZoneIdSelectionDialogFragment(timeZoneIdList, onSelected).show(fm, fragmentTag)
        }

        fun hide(fm: FragmentManager) {
            val fragmentTag = TimeZoneIdSelectionDialogFragment::class.java.simpleName
            fm.findFragmentByTag(fragmentTag)?.let {
                if (it.isVisible) {
                    (it as TimeZoneIdSelectionDialogFragment).dismiss()
                }
            }
        }
    }
}