package com.planradar.weather.presentation
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.planradar.weather.base.ParentActivity
import com.planradar.weather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ParentActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val mainModel: MainViewModel by viewModels()

    override fun initializeComponents() {}
}