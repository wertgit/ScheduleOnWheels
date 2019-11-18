package com.fate.data.di.entities

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ShiftItem(
    @Expose
    var name: String
): Parcelable
