package com.example.uxmapp2.data.characterData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Thumbnail(
    val extension: String,
    val path: String
):Parcelable