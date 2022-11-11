package com.planradar.weather.presentation.navigators
import androidx.navigation.NavController
import com.planradar.weather.presentation.fragments.AllCitiesFragmentDirections

/**
 * @param navController
 * Is for holding controller object that specific for this navigation graph
 * And should be destroyed after finish to make sure there is no memory leak
 */
class MainNavigatorController
constructor(
    private val navController: NavController
) {

    fun navigate(state: AuthNavigatorIntent) {
        try {
            when(state) {
                is AuthNavigatorIntent.AllCitiesToCityDetails -> {
                    navController.navigate(
                        AllCitiesFragmentDirections.actionAllCitiesFragmentToCityDetailsFragment(
                            state.cityId
                        )
                    )
                }
                is AuthNavigatorIntent.AllCitiesToCityInfo -> {
                    navController.navigate(
                        AllCitiesFragmentDirections.actionAllCitiesFragmentToHistoricalCityFragment(
                            state.cityId
                        )
                    )
                }
            }
        } catch (e: Exception) { e.printStackTrace() }
    }

}

sealed class AuthNavigatorIntent {
    data class AllCitiesToCityDetails(
        val cityId: String,
        val removeFromBackStack: Boolean = false
    ): AuthNavigatorIntent()
    data class AllCitiesToCityInfo(
        val cityId: String,
        val removeFromBackStack: Boolean = false
    ): AuthNavigatorIntent()
}