package com.fate.scheduleonwheels.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fate.data.di.entities.Engineer
import com.fate.scheduleonwheels.commands.SharedViewModelCommand
import com.fate.scheduleonwheels.common.Event

/**
 * A ViewModel shared among the MainActivity and Fragments using the \
 * activity scope for commmunication purposes.
 * This will provide communication btn Activity and Fragment using LiveData and ViewModel due
 * to its advantage inculiding lifecycle awareness and reduced boilerplate code.
 *
 */
class SharedViewModel : ViewModel() {

    private var command: MutableLiveData<Event<SharedViewModelCommand>> = MutableLiveData()
    private var dataEngineersList: MutableLiveData<List<Engineer>> = MutableLiveData()

    fun command(): MutableLiveData<Event<SharedViewModelCommand>> {
        return command
    }

    fun getEngineersListLiveData(): MutableLiveData<List<Engineer>>  {
        return dataEngineersList
    }

    fun setEngineersList(list:List<Engineer>){
        dataEngineersList.value = list
    }

    fun onGenerateSchedule(){
        command.value = Event((SharedViewModelCommand.OnGenerateSchedule))
    }
}