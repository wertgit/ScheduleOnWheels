package com.fate.scheduleonwheels.viewmodels

import androidx.lifecycle.MutableLiveData
import com.fate.data.di.entities.Engineer
import com.fate.scheduleonwheels.base.BaseViewModel
import com.fate.scheduleonwheels.repo.EngineersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class EngineersViewModel(private val repository: EngineersRepository) : BaseViewModel() {


    // toggles visibility of button
    var buttonVisibility: MutableLiveData<Boolean> = MutableLiveData()
    private var dataEngineers: MutableLiveData<List<Engineer>> = MutableLiveData()

    init {
      //  loadData()
    }

    fun getDataEngineers(): MutableLiveData<List<Engineer>> {
        return dataEngineers
    }

    fun loadData() {

        disposable().add(repository.getEngineers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    dataEngineers.value = result.data
                    buttonVisibility.value = true
                },
                { error ->
                    Timber.d(error.message.toString())
                }

            ))
    }



}