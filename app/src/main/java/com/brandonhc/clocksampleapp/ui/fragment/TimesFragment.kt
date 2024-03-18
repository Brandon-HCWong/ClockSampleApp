package com.brandonhc.clocksampleapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.brandonhc.clocksampleapp.R
import com.brandonhc.clocksampleapp.data.ui.SupportedLanguage
import com.brandonhc.clocksampleapp.data.ui.SupportedSortingMethod
import com.brandonhc.clocksampleapp.databinding.FragmentTimesBinding
import com.brandonhc.clocksampleapp.ui.fragment.recyclerview.adapter.AnalogClockAdapter
import com.brandonhc.clocksampleapp.ui.viewmodel.TimesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimesFragment : Fragment() {
    private val timesViewModel: TimesViewModel by viewModel()
    private var binding: FragmentTimesBinding? = null
    private lateinit var analogClockAdapter: AnalogClockAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTimesBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.initViews()
            it.initData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun FragmentTimesBinding.initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            showProgressing()
            val entityList = timesViewModel.loadUserTimeZoneInfoList()
            tvNoData.isVisible = entityList.isEmpty()
            analogClockAdapter.submitList(entityList)
            hideProgressing()
        }
    }
    private fun FragmentTimesBinding.initViews() {
        mbSwitchLanguage.text = getString(R.string.switch_language, SupportedLanguage.getDefault().locale.displayName)
        mbSwitchLanguage.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setSingleChoiceItems(
                    SupportedLanguage.getCharSequenceArray(),
                    SupportedLanguage.getDefault().ordinal
                ) { dialog, which ->
                    timesViewModel.setCurrentLanguage(SupportedLanguage.fromIndex(which).locale.toLanguageTag())
                    dialog.dismiss()
                    requireActivity().recreate()
                }
                .create()
                .show()
        }

        val currentSortMethod = timesViewModel.sortingMethod
        mbSort.text = getString(R.string.switch_sort, getString(currentSortMethod.resId))
        mbSort.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setSingleChoiceItems(
                    SupportedSortingMethod.getCharSequenceArray(requireContext()),
                    currentSortMethod.ordinal
                ) { dialog, which ->
                    timesViewModel.sortingMethod = SupportedSortingMethod.fromIndex(which)
                    initData()
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        analogClockAdapter = AnalogClockAdapter()
        rvContent.layoutManager = GridLayoutManager(requireContext(), 2)
        rvContent.adapter = analogClockAdapter
    }

    private fun FragmentTimesBinding.showProgressing() {
        layoutProgressing.isVisible = true
    }

    private fun FragmentTimesBinding.hideProgressing() {
        layoutProgressing.isVisible = false
    }
}