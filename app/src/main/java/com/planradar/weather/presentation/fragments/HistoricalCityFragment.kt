package com.planradar.weather.presentation.fragments
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.planradar.weather.R
import com.planradar.weather.base.ParentFragment
import com.planradar.weather.data.cache.model.CachedHistoryCity
import com.planradar.weather.databinding.FragmentHistoricalCityBinding
import com.planradar.weather.listeners.OnItemClickedGeneric
import com.planradar.weather.presentation.MainEventHolder
import com.planradar.weather.presentation.MainViewModel
import com.planradar.weather.presentation.fragments.adapter.AllCityHistoryAdapter
import com.planradar.weather.presentation.navigators.AuthNavigatorIntent
import com.planradar.weather.presentation.navigators.MainNavigatorController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HistoricalCityFragment : ParentFragment<FragmentHistoricalCityBinding>(),
    OnItemClickedGeneric<CachedHistoryCity> {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentHistoricalCityBinding::inflate

    private val viewModel: MainViewModel by activityViewModels()
    private val args: HistoricalCityFragmentArgs by navArgs()
    private val mAdapter by lazy { AllCityHistoryAdapter() }

    private var mNavigator: MainNavigatorController? = null

    override fun initializeComponents(view: View) {
        mNavigator = MainNavigatorController(findNavController())
        setupRecyclerView()
        observeCityHistory()
        args.cityName?.let {
            viewModel.setState(MainEventHolder.GettingCityHistory(it))
        } ?: findNavController().popBackStack()
        binding.run {
            appToolbarBack.appToolBar.setNavigationOnClickListener { findNavController().popBackStack() }
            appToolbarBack.tvTitle.text = getString(R.string.city_historical, args.cityName)
        }
    }

    private fun observeCityHistory() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.allCityHistory.collectLatest {
                it?.let {
                    mAdapter.submitList(it)
                    if (it.isEmpty())
                        Toast.makeText(
                            this@HistoricalCityFragment.requireContext(),
                            "No history yet",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvAllHistories.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@HistoricalCityFragment.requireContext())
        }
        mAdapter.setOnItemClickListener(this)
    }

    override fun onItemClick(type: String, data: CachedHistoryCity, position: Int) {
        when(type) {
            AllCityHistoryAdapter.CITY_HISTORY_MODEL -> {
                mNavigator?.navigate(AuthNavigatorIntent.AllHistoryToCityDetails(
                    cityId = data.name, date = data.date
                ))
            }
        }
    }
}