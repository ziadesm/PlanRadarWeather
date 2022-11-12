@file:Suppress("UNCHECKED_CAST")
package com.planradar.weather.base
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.planradar.weather.R

abstract class ParentSheetFragment<out VB: ViewBinding> : BottomSheetDialogFragment() {
    protected abstract val isCancel: Boolean
    protected abstract val isFull: Boolean
    abstract fun initializeComponents(savedInstanceState: Bundle?)

    abstract val bindingInflater : (LayoutInflater) -> VB
    private var _binding: ViewBinding? = null

    protected val binding:VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(layoutInflater)
        return _binding?.root!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.AppBottomSheetDialogTheme
        )

        isCancelable = isCancel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeComponents(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        if (isFull) {
            dialog.setOnShowListener {

                val bottomSheetDialog = it as BottomSheetDialog
                val parentLayout =
                    bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                parentLayout?.let { parent ->
                    val behaviour = BottomSheetBehavior.from(parent)
                    setupFullHeight(parent)
                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}