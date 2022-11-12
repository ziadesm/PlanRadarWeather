@file:Suppress("UNCHECKED_CAST")
package com.planradar.weather.base
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class ParentDialogFragment<out VB : ViewBinding> : DialogFragment() {
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB
    protected abstract val cancellable: Boolean
    protected abstract val listener: DialogInterface.OnCancelListener?
    protected abstract val dismiss: DialogInterface.OnDismissListener?

    protected val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        _binding = method.invoke(null, layoutInflater, container, false) as VB
        initializeComponents()
        return _binding?.root
    }

    protected abstract fun initializeComponents()
}