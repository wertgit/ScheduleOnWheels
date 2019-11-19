package com.fate.scheduleonwheels.views

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fate.scheduleonwheels.BR
import com.fate.scheduleonwheels.R
import com.fate.scheduleonwheels.commands.SharedViewModelCommand
import com.fate.scheduleonwheels.databinding.ActivityMainBinding
import com.fate.scheduleonwheels.viewmodels.ActivityViewModel
import com.fate.scheduleonwheels.viewmodels.SharedViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel


/**
 * Uses Fragments to make moving to Navigation Graph and Single App Activity
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityMainBinding
    private val viewModel: ActivityViewModel by inject()
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        sharedViewModel = getViewModel()
        observe()
        displayEngineersScreen()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observe() {

        sharedViewModel.command().observe(this, androidx.lifecycle.Observer { it ->
            it.getContentIfNotHandled()?.let { command ->
                when (command) {
                    is SharedViewModelCommand.OnGenerateSchedule -> displayScheduleScreen()
                }
            }
        })
    }

    /**
     * function for performing data Binding between XML and view
     */
    private fun performDataBinding() {
        mViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewBinding.setVariable(BR.viewModel, viewModel)
        mViewBinding.executePendingBindings()
    }

    fun displayEngineersScreen() {
        loadFragment(EngineersFragment.newInstance(), EngineersFragment.TAG)
    }

    fun displayScheduleScreen() {
        loadFragment(ScheduleFragment.newInstance(), ScheduleFragment.TAG)
    }

    private fun loadFragment(fragment: Fragment, tag: String) {
        fragment.let {
            supportFragmentManager.beginTransaction()
                .replace(mViewBinding.container.id, it)
                .addToBackStack(tag)
                .commit()

        }
    }
}
