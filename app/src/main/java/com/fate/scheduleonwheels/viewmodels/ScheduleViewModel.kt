package com.fate.scheduleonwheels.viewmodels

import androidx.lifecycle.MutableLiveData
import com.fate.data.di.entities.Engineer
import com.fate.data.di.entities.Schedule
import com.fate.scheduleonwheels.base.BaseViewModel
import com.fate.scheduleonwheels.utils.CommonUtils.Companion.generateSchedule


class ScheduleViewModel() : BaseViewModel() {

    private var dataSchedule: MutableLiveData<ArrayList<Schedule>> = MutableLiveData()

    fun getDataSchedule(): MutableLiveData<ArrayList<Schedule>> {
        return dataSchedule
    }

    fun onGenerateSchedule(list: List<Engineer>) {
        dataSchedule.value = generateSchedule(list)
    }


}