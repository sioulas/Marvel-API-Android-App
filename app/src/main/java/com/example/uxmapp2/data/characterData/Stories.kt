package com.example.uxmapp2.data.characterData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXXX>,
    val returned: Int
):Parcelable