package com.example.uxmapp2.data.characterData

import com.example.uxmapp2.data.model.CharacterModel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>,
    var isSelected: Boolean = false
) : Parcelable {
    fun toCharacter(): CharacterModel {
        return CharacterModel(
            id = id,
            name = name,
            description = description,
            thumbnail = thumbnail.path,
            thumbnailExt = thumbnail.extension,
            comics = comics.items.map {
                it.name
            }
        )
    }
}
