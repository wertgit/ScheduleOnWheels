package com.fate.data.di.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList


@Parcelize
data class Schedule(
    var date: Int,   // a date
    var week: String,
    var engineersSupporting: List<Engineer>, // list of engineers required,
    var listShifts: List<Shift>
): Parcelable
