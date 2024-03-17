package com.brandonhc.clocksampleapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brandonhc.clocksampleapp.databinding.FragmentTimesBinding

class TimesFragment : Fragment() {
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
}