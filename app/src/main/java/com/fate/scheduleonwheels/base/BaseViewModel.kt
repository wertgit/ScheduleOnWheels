package com.fate.scheduleonwheels.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.qualifier.named


/**
 * Class contains the base ViewModel for all different project viewModels.
 * Contains similar functionality
 * KoinComponent interface is implemented because the [BaseViewModel] abstract class is not
 * supported by koin.
 * we use get() for eager access for instance and its named.
 */

abstract class BaseViewModel : ViewModel() , KoinComponent{

    private var compositeDisposable: CompositeDisposable = get(named("CompositeDisposable"))

    fun disposable(): CompositeDisposable {
        return compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()

    }

}