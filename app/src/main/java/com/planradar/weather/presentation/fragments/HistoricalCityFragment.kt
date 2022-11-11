package com.planradar.weather.presentation.fragments
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.planradar.weather.base.ParentFragment
import com.planradar.weather.databinding.FragmentHistoricalCityBinding

class HistoricalCityFragment : ParentFragment<FragmentHistoricalCityBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentHistoricalCityBinding::inflate

    override fun initializeComponents(view: View) {
        
    }
}