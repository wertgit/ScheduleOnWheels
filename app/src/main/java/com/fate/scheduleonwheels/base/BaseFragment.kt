package com.fate.scheduleonwheels.base

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar

/**\
 * @BaseFragment project fragments will extend from this class. It contains basic similar functionality
 * among the fragments.
 */
abstract class BaseFragment<T : ViewDataBinding, V : ViewModel>() : Fragment() {

    private lateinit var mViewDataBinding: T
    private lateinit var mViewModel: V
    private lateinit var mRootView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner
        mRootView = mViewDataBinding.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()


    }

    /**
     * @return layout resource id
     * LayoutRes annotation is added because layout resource integer value is expected.
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    fun getViewDataBinding(): T {
        return mViewDataBinding
    }

    fun showSnackBar(message: String?, length: Int) {
        Snackbar.make(mViewDataBinding.root, Html.fromHtml(message), length).show()
    }

    fun showToast(message: String) {
        Toast.makeText(activity, Html.fromHtml(message), Toast.LENGTH_LONG).show()
    }

    fun dpToPx(dp: Int): Int {
        val density = resources
            .displayMetrics
            .density
        return Math.round(dp.toFloat() * density)
    }


}