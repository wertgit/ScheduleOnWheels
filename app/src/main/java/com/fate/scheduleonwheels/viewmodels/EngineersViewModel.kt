package com.fate.scheduleonwheels.viewmodels

import androidx.lifecycle.MutableLiveData
import com.fate.data.data.entities.Engineer
import com.fate.scheduleonwheels.base.BaseViewModel
import com.fate.scheduleonwheels.repo.EngineersRepository
import com.fate.scheduleonwheels.utils.MockData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * The ViewModel used in [EngineersFragment]
 */
class EngineersViewModel(private val repository: EngineersRepository) : BaseViewModel() {


    private var dataEngineers: MutableLiveData<List<Engineer>> = MutableLiveData()

    init {
        loadData()
        //mockData()
    }

    fun getDataEngineers(): MutableLiveData<List<Engineer>> {
        return dataEngineers
    }

    /**
     * Assign Mocked Data to Avoid Limit Calls to Apiary API bettwe solution would be
     * Cache Data or Store in Room but this is faster for testing.
     */
    fun mockData() {
        dataEngineers.value = MockData.mockedEngineersList
    }

    fun loadData() {

        disposable().add(repository.getEngineers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    dataEngineers.value = result.data
                },
                { error ->
                    Timber.d(error.message.toString())
                }

            ))
    }


}