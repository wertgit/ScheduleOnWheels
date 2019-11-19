package com.fate.scheduleonwheels.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fate.scheduleonwheels.BR
import com.fate.scheduleonwheels.R
import com.fate.scheduleonwheels.adapters.ScheduleAdapter
import com.fate.scheduleonwheels.base.BaseFragment
import com.fate.scheduleonwheels.databinding.FragmentScheduleBinding
import com.fate.scheduleonwheels.viewmodels.ScheduleViewModel
import com.fate.scheduleonwheels.viewmodels.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.android.ext.android.inject


class ScheduleFragment : BaseFragment<FragmentScheduleBinding, ScheduleViewModel>() {

    private val mViewModel: ScheduleViewModel by inject()
    private lateinit var mViewBinding: FragmentScheduleBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val scheduleAdapter: ScheduleAdapter by inject()
    private lateinit var sharedViewModel: SharedViewModel

    companion object {
        fun newInstance() = ScheduleFragment()
        const val TAG = "SCHEDULEFRAGMENT"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_schedule
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): ScheduleViewModel {
        return mViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // assign shared viewModel using Koin with AndroidX
        sharedViewModel = getSharedViewModel()
        mViewBinding = getViewDataBinding()
        setupRecycler()
        observe()

    }

    override fun onResume() {
        super.onResume()
        setUpAppBar()
    }

    private fun setupRecycler() {

        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        mViewBinding.recyclerScheduleList.apply {
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = scheduleAdapter
        }
    }


    private fun observe() {

        mViewModel.getDataSchedule().observe(this, androidx.lifecycle.Observer {
            scheduleAdapter.assignList(it)
        })

        sharedViewModel.getEngineersListLiveData().observe(this, Observer {
            if (!it.isNullOrEmpty())
                mViewModel.generateSchedule(it)
        })
    }

    private fun setUpAppBar() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Schedule"
        }

    }

}
