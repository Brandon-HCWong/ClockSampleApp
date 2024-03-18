package com.brandonhc.clocksampleapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brandonhc.clocksampleapp.databinding.FragmentTimesBinding
import com.brandonhc.clocksampleapp.ui.viewmodel.TimesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class TimesFragment : Fragment() {
    private val timesViewModel: TimesViewModel by viewModel()
    private var binding: FragmentTimesBinding? = null

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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    private fun FragmentTimesBinding.initViews() {
        mbSwitchLanguage.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setSingleChoiceItems(
                    arrayOf<CharSequence>("English", "繁體中文"),
                    if (Locale.getDefault().language.contains("zh")) 1 else 0
                ) { dialog, which ->
                    if (which == 0) {
                        timesViewModel.setCurrentLanguage("en")
                    } else {
                        timesViewModel.setCurrentLanguage("zh")
                    }
                    dialog.dismiss()
                    requireActivity().recreate()
                }
                .create()
                .show()
        }
    }
}