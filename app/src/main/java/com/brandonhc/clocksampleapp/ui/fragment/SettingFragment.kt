package com.brandonhc.clocksampleapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.brandonhc.clocksampleapp.R
import com.brandonhc.clocksampleapp.databinding.FragmentSettingBinding
import com.brandonhc.clocksampleapp.ui.fragment.recyclerview.adapter.TimeZoneIdAdapter
import com.brandonhc.clocksampleapp.ui.viewmodel.SettingViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : Fragment(), TimeZoneIdAdapter.OnItemInteractionListener {
    private val settingViewModel: SettingViewModel by viewModel()
    private var binding: FragmentSettingBinding? = null
    private lateinit var timeZoneIdAdapter: TimeZoneIdAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSettingBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.let {
            it.initViews()
            it.initData()
        }
    }

    override fun onPause() {
        super.onPause()
        TimeZoneIdSelectionDialogFragment.hide(childFragmentManager)
    }

    private fun FragmentSettingBinding.initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            showProgressing()
            settingViewModel.loadTimeZoneIdList(false)
            val entityList = settingViewModel.loadUserTimeZoneInfoList(false)
            tvNoData.isVisible = entityList.isEmpty()
            timeZoneIdAdapter.submitList(entityList.map { it.timeZoneId })
            hideProgressing()
        }
    }

    private fun FragmentSettingBinding.initViews() {
        timeZoneIdAdapter = TimeZoneIdAdapter(false, this@SettingFragment)
        rvContent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvContent.adapter = timeZoneIdAdapter
        mbEdit.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_editFragment)
        }
        mbAdd.setOnClickListener {
            TimeZoneIdSelectionDialogFragment.show(
                childFragmentManager,
                settingViewModel.getCurrentTimeZoneIdList()
            ) { timeZoneId ->
                viewLifecycleOwner.lifecycleScope.launch {
                    showProgressing()
                    val entity = settingViewModel.loadTimeZoneInfo(timeZoneId, false)
                    entity?.let {
                        val userTimeZoneInfoDbEntity = it.toUserTimeZoneInfoDbEntity(System.currentTimeMillis())
                        settingViewModel.saveUserTimeZoneInfo(userTimeZoneInfoDbEntity)
                        val entityList = settingViewModel.loadUserTimeZoneInfoList(false)
                        tvNoData.isVisible = entityList.isEmpty()
                        timeZoneIdAdapter.submitList(entityList.map { it.timeZoneId })
                    } ?: run {
                        Snackbar.make(root, R.string.server_error, Snackbar.LENGTH_LONG).show()
                    }
                    hideProgressing()
                }
            }
        }
    }

    private fun FragmentSettingBinding.showProgressing() {
        layoutProgressing.isVisible = true
    }

    private fun FragmentSettingBinding.hideProgressing() {
        layoutProgressing.isVisible = false
    }
}