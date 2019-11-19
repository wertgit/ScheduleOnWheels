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
import com.fate.scheduleonwheels.adapters.EngineersAdapter
import com.fate.scheduleonwheels.base.BaseFragment
import com.fate.scheduleonwheels.databinding.FragmentEngineersBinding
import com.fate.scheduleonwheels.viewmodels.EngineersViewModel
import com.fate.scheduleonwheels.viewmodels.SharedViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel


class EngineersFragment : BaseFragment<FragmentEngineersBinding, EngineersViewModel>() {

    private val mViewModel: EngineersViewModel by inject()
    private lateinit var mViewBinding: FragmentEngineersBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val engineersAdapter: EngineersAdapter by inject()
    private lateinit var sharedViewModel: SharedViewModel

    companion object {
        fun newInstance() = EngineersFragment()
        const val TAG = "ENGINEERSFRAGMENT"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_engineers
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): EngineersViewModel {
        return mViewModel
    }

    override fun onResume() {
        super.onResume()
        setUpAppBar()
        mViewModel.loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // assign shared viewModel using Koin with AndroidX
        sharedViewModel = getSharedViewModel()
        mViewBinding = getViewDataBinding()
        setupRecycler()
        observe()

        mViewBinding.buttonGenerateSchedule.setOnClickListener(View.OnClickListener {
            generateSchedule()
        })
    }

    private fun observe() {

        mViewModel.getDataEngineers().observe(this, androidx.lifecycle.Observer {
            if (!it.isNullOrEmpty())
                sharedViewModel.setEngineersList(it)
        })


        sharedViewModel.getEngineersListLiveData().observe(this, Observer {
            if (!it.isNullOrEmpty())
                engineersAdapter.assignList(it)
        })

    }

    private fun setupRecycler() {

        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        mViewBinding.recyclerEngineersList.apply {
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = engineersAdapter
        }
    }

    private fun generateSchedule() {
        sharedViewModel.onGenerateSchedule()
    }

    private fun setUpAppBar() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            title = "Engineers"
        }
    }


}
