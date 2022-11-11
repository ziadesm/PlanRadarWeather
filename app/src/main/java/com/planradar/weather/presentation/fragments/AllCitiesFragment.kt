package com.planradar.weather.presentation.fragments
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.planradar.weather.R
import com.planradar.weather.base.ParentFragment
import com.planradar.weather.databinding.FragmentAllCitiesBinding
import com.planradar.weather.domain.model.City
import com.planradar.weather.listeners.OnItemClickedGeneric
import com.planradar.weather.presentation.MainViewModel
import com.planradar.weather.presentation.fragments.adapter.AllCityAdapter
import com.planradar.weather.presentation.navigators.AuthNavigatorIntent
import com.planradar.weather.presentation.navigators.MainNavigatorController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCitiesFragment : ParentFragment<FragmentAllCitiesBinding>(), OnItemClickedGeneric<City> {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentAllCitiesBinding::inflate

    private val mainModel: MainViewModel by activityViewModels()
    private val mAdapter by lazy { AllCityAdapter() }
    private var mNavigator: MainNavigatorController? = null

    override fun initializeComponents(view: View) {
        mNavigator = MainNavigatorController(findNavController())
        setupRecyclerView()
        observeAllLocalCities()
        binding.run {
            appToolbarAdd.tvTitle.text = getString(R.string.cities)
            appToolbarAdd.appToolBar.setNavigationOnClickListener {
                // TODO(1) Show edit text to make our adding city
            }
        }
    }

    private fun observeAllLocalCities() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            mainModel.allCities.collect {
                it?.let {
                    mAdapter.submitList(it)
                } ?: Toast.makeText(this@AllCitiesFragment.requireContext(), "No Cities", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvAllCities.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@AllCitiesFragment.requireContext())
        }
        mAdapter.setOnItemClickListener(this)
    }

    override fun onItemClick(type: String, data: City, position: Int) {
        when(type) {
            AllCityAdapter.CITY_MODEL -> mNavigator?.navigate(AuthNavigatorIntent.AllCitiesToCityDetails(data.name))
            AllCityAdapter.CITY_INFO -> mNavigator?.navigate(AuthNavigatorIntent.AllCitiesToCityInfo(data.name))
        }
    }
}