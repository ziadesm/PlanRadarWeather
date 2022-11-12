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
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.databinding.FragmentAllCitiesBinding
import com.planradar.weather.listeners.OnItemClickedGeneric
import com.planradar.weather.presentation.MainViewModel
import com.planradar.weather.presentation.fragments.adapter.AllCityAdapter
import com.planradar.weather.presentation.navigators.AuthNavigatorIntent
import com.planradar.weather.presentation.navigators.MainNavigatorController
import com.planradar.weather.presentation.sheets.SearchCitySheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AllCitiesFragment : ParentFragment<FragmentAllCitiesBinding>(), OnItemClickedGeneric<CachedCity> {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentAllCitiesBinding::inflate

    private val mainModel: MainViewModel by activityViewModels()
    private val mAdapter by lazy { AllCityAdapter() }
    private val mSearchSheet by lazy { SearchCitySheet.newInstance() }
    private var mNavigator: MainNavigatorController? = null

    override fun initializeComponents(view: View) {
        mNavigator = MainNavigatorController(findNavController())
        setupRecyclerView()
        observeCityError()
        observeAllLocalCities()
        binding.run {
            appToolbarAdd.tvTitle.text = getString(R.string.cities)
            fabAddCity.setOnClickListener {
                if (!mSearchSheet.isAdded) mSearchSheet.show(childFragmentManager, "SearchCities")
            }
        }
    }

    private fun observeAllLocalCities() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            mainModel.allCities.collectLatest {
                it?.let {
                    //TODO(1) Uncomment these line ONLY for test
                    /*if (it.isEmpty()) {}
                    else*/ mAdapter.submitList(it)
                } ?: Toast.makeText(this@AllCitiesFragment.requireContext(), "No Cities yet", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun observeCityError() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            mainModel.errorDetails.collectLatest {
                it?.let {
                    Toast.makeText(
                        this@AllCitiesFragment.requireContext(),
                        "${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
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

    override fun onItemClick(type: String, data: CachedCity, position: Int) {
        when(type) {
            AllCityAdapter.CITY_MODEL -> mNavigator?.navigate(AuthNavigatorIntent.AllCitiesToCityDetails(data.name))
            AllCityAdapter.CITY_INFO -> mNavigator?.navigate(AuthNavigatorIntent.AllCitiesToCityInfo(data.name))
        }
    }
}