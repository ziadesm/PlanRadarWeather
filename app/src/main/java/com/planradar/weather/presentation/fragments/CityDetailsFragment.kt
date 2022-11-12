package com.planradar.weather.presentation.fragments
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.planradar.weather.R
import com.planradar.weather.base.ParentFragment
import com.planradar.weather.databinding.FragmentCityDetailsBinding
import com.planradar.weather.domain.model.City
import com.planradar.weather.presentation.MainEventHolder
import com.planradar.weather.presentation.MainViewModel
import com.planradar.weather.utils.DateHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class CityDetailsFragment : ParentFragment<FragmentCityDetailsBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCityDetailsBinding::inflate

    private val viewModel: MainViewModel by activityViewModels()
    private val args: CityDetailsFragmentArgs by navArgs()

    override fun initializeComponents(view: View) {
        showProgress(true)
        observeCityDetails()
        observeCityDetailsErrors()
        args.cityName?.let {
            viewModel.setState(MainEventHolder.GettingWeatherCity(it, args.cityDate))
        }
        binding.run {
            appToolbarBack.appToolBar.setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun observeCityDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cityDetails.collect {
                it?.let {
                    showProgress(false)
                    settingViews(it)
                }
            }
        }
    }
    private fun observeCityDetailsErrors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorDetails.collectLatest {
                it?.let {
                    showProgress(false)
                    Toast.makeText(
                        this@CityDetailsFragment.requireContext(),
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showProgress(b: Boolean) { binding.pbCityDetails.visibility = VISIBLE.takeIf { b } ?: GONE }
    private fun settingViews(weather: City) {
        binding.run {
            Glide.with(this@CityDetailsFragment)
                .load("https://openweathermap.org/img/w/${weather.weather_icon}.png")
                .into(ivCityWeatherIcon)
            tvWeatherInfo.text = String.format(
                getString(R.string.weather_info_details),
                weather.name,
                DateHelper.getDateFormatted(weather.date ?: Calendar.getInstance().timeInMillis)
            )
            tvWeatherStatus.text = weather.name
            tvWeatherDescription.text = weather.weather_main
            tvWeatherTemp.text = String.format(getString(R.string.weather_temp), "${weather.main_temp}")
            tvWeatherHumidity.text = String.format(getString(R.string.weather_humidity), "${weather.main_humidity}")
            tvWeatherWindSpeed.text = String.format(getString(R.string.weather_wind_speed), "${weather.wind_speed}")
        }
    }
}