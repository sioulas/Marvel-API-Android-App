package com.example.uxmapp2.data.characterData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Item(
    val name: String,
    val resourceURI: String
):Parcelable