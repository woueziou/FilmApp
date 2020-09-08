package tg.vaguenone.filmapp.data.models


import com.google.gson.annotations.SerializedName

data class Movie(
//    @SerializedName("adult")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,
    val title: String
)