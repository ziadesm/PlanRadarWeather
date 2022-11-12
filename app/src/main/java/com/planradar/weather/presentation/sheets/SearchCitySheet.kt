package com.planradar.weather.presentation.sheets
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.activityViewModels
import com.planradar.weather.base.ParentSheetFragment
import com.planradar.weather.databinding.SheetSearchCityBinding
import com.planradar.weather.presentation.MainEventHolder
import com.planradar.weather.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCitySheet : ParentSheetFragment<SheetSearchCityBinding>() {
    override val bindingInflater: (LayoutInflater) -> SheetSearchCityBinding
        get() = SheetSearchCityBinding::inflate
    override val isCancel: Boolean
        get() = true
    override val isFull: Boolean
        get() = false

    private val viewModel: MainViewModel by activityViewModels()

    override fun initializeComponents(savedInstanceState: Bundle?) {
        binding.run {
            etSearchCity.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.setState(MainEventHolder.GettingWeatherCity(etSearchCity.text.toString(), 0L))

                    return@OnEditorActionListener true
                }
                false
            })
        }
    }

    companion object {
        fun newInstance() = SearchCitySheet()
    }
}