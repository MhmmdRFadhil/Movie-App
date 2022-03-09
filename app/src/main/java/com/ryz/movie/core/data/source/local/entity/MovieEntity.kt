package com.ryz.movie.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "poster")
    var poster: String,

    @ColumnInfo(name = "score")
    var score: Double,

    @ColumnInfo(name = "duration")
    var duration: Int,

    @ColumnInfo(name = "genre")
    var genre: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "isFav")
    var isFav: Boolean

) : Parcelable
