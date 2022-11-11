@file:Suppress("UNCHECKED_CAST")
package com.planradar.weather.base
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.viewbinding.ViewBinding

abstract class ParentFragment<out T : ViewBinding> :
    Fragment()
    /*AnalyticsLogger by AnalyticsLoggerImpl(),
    FragmentAnalyticsLogger by FragmentAnalyticsLoggerImpl()*/ {
    private var _binding: ViewBinding? = null
    protected val binding: T
        get() = _binding as T

    protected abstract val bindingInflater: (LayoutInflater) -> ViewBinding
    protected abstract fun initializeComponents(view: View)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = run {
        sharedElementEnterTransition = ChangeBounds()
        _binding = bindingInflater(inflater)
        return _binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeComponents(view)
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}