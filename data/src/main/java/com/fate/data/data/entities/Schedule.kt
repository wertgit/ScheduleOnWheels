package com.fate.data.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Schedule(
    var date: Int,   // a date
    var week: String,
    var engineersSupporting: List<Engineer>, // list of engineers required,
    var listShifts: List<Shift>
): Parcelable
