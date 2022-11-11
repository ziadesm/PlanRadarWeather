@file:Suppress("UNCHECKED_CAST")
package com.planradar.weather.base
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class ParentActivity<out VB: ViewBinding> : AppCompatActivity() /*AnalyticsLogger by AnalyticsLoggerImpl()*/ {
    private var _binding: ViewBinding? = null
    abstract val bindingInflater : (LayoutInflater) -> VB
    protected abstract fun initializeComponents()

    protected val binding:VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(_binding?.root)

        initializeComponents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}