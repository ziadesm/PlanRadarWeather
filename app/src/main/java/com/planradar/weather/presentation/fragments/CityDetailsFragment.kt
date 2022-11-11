package com.planradar.weather.presentation.fragments
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.planradar.weather.base.ParentFragment
import com.planradar.weather.databinding.FragmentCityDetailsBinding

class CityDetailsFragment : ParentFragment<FragmentCityDetailsBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCityDetailsBinding::inflate

    override fun initializeComponents(view: View) {
        
    }
}