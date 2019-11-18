package com.fate.data.di.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class WeekDay(
    var date: Int,   // a date
    var week: String,
    val ids: List<Int>
): Parcelable
