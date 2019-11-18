package com.fate.scheduleonwheels.viewmodels

import androidx.lifecycle.MutableLiveData
import com.fate.data.di.entities.Engineer
import com.fate.scheduleonwheels.repo.EngineersRepository
import com.fate.scheduleonwheels.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ActivityViewModel(private val repository: EngineersRepository) : BaseViewModel() {

    private var dataEngineers: MutableLiveData<List<Engineer>> = MutableLiveData()

    init {
        //loadData()
    }

    fun getDataEngineers(): MutableLiveData<List<Engineer>> {
        return dataEngineers
    }

    private fun loadData() {

        disposable().add(repository.getEngineers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                   //Timber.d(result.toString())
                    dataEngineers.value = result.data
                },
                { error ->
                    Timber.d(error.message.toString())
                }

            ))
    }

}