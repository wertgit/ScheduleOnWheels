package com.fate.data.data.entities

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class EngineersResponce(

    @SerializedName("engineers")
    @Expose
    var data: List<Engineer>
) : Parcelable