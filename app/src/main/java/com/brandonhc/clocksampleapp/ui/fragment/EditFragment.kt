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
import com.brandonhc.clocksampleapp.databinding.FragmentEditBinding
import com.brandonhc.clocksampleapp.ui.fragment.recyclerview.adapter.TimeZoneIdAdapter
import com.brandonhc.clocksampleapp.ui.viewmodel.EditViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditFragment : Fragment(), TimeZoneIdAdapter.OnItemInteractionListener {
    private val editViewModel: EditViewModel by viewModel()
    private var binding: FragmentEditBinding? = null
    private lateinit var timeZoneIdAdapter: TimeZoneIdAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEditBinding.inflate(inflater, container, false).run {
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

    override fun onItemClick(timeZoneId: String, position: Int) {
        TimeZoneIdSelectionDialogFragment.show(
            childFragmentManager,
            editViewModel.getCurrentTimeZoneIdList()
        ) { updatedTimeZoneId ->
            viewLifecycleOwner.lifecycleScope.launch {
                binding?.let { fragmentEditBinding ->
                    fragmentEditBinding.showProgressing()
                    if (timeZoneId != updatedTimeZoneId) {
                        editViewModel.deleteUserTimeZoneInfo(timeZoneId)
                    }
                    val entity = editViewModel.loadTimeZoneInfo(updatedTimeZoneId, false)
                    entity?.let { ianaTimeZoneInfoDbEntity ->
                        val userTimeZoneInfoDbEntity =
                            ianaTimeZoneInfoDbEntity.toUserTimeZoneInfoDbEntity(System.currentTimeMillis())
                        editViewModel.saveUserTimeZoneInfo(userTimeZoneInfoDbEntity)
                        val entityList = editViewModel.loadUserTimeZoneInfoList(false)
                        fragmentEditBinding.tvNoData.isVisible = entityList.isEmpty()
                        timeZoneIdAdapter.submitList(entityList.map { it.timeZoneId })
                    } ?: run {
                        Snackbar.make(fragmentEditBinding.root, R.string.server_error, Snackbar.LENGTH_LONG).show()
                    }
                    fragmentEditBinding.hideProgressing()
                }
            }
        }
    }

    override fun onItemSelected(timeZoneId: String, position: Int) {
        editViewModel.selectTimeZoneId(timeZoneId)
    }

    override fun onItemUnselected(timeZoneId: String, position: Int) {
        editViewModel.unselectTimeZoneId(timeZoneId)
    }

    private fun FragmentEditBinding.initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            showProgressing()
            editViewModel.loadTimeZoneIdList(false)
            val entityList = editViewModel.loadUserTimeZoneInfoList(false)
            tvNoData.isVisible = entityList.isEmpty()
            timeZoneIdAdapter.submitList(entityList.map { it.timeZoneId })
            hideProgressing()
        }
    }

    private fun FragmentEditBinding.initViews() {
        ivLeftButton.setOnClickListener {
            findNavController().navigateUp()
        }
        timeZoneIdAdapter = TimeZoneIdAdapter(true, this@EditFragment)
        rvContent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvContent.adapter = timeZoneIdAdapter
        mbDelete.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                showProgressing()
                editViewModel.deleteUserTimeZoneInfo(editViewModel.getSelectedTimeZoneIdList())
                val entityList = editViewModel.loadUserTimeZoneInfoList(false)
                tvNoData.isVisible = entityList.isEmpty()
                timeZoneIdAdapter.submitList(entityList.map { it.timeZoneId })
                hideProgressing()
            }
        }
    }

    private fun FragmentEditBinding.showProgressing() {
        layoutProgressing.isVisible = true
    }

    private fun FragmentEditBinding.hideProgressing() {
        layoutProgressing.isVisible = false
    }
}