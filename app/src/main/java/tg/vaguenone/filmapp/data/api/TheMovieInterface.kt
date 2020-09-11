package tg.vaguenone.filmapp.data.api

import android.graphics.pdf.PdfDocument
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tg.vaguenone.filmapp.data.models.MovieDetailsModel
import tg.vaguenone.filmapp.data.models.MovieModel
import tg.vaguenone.filmapp.data.models.MovieResponse

interface TheMovieInterface {

//    @GET("/3/search/movie")
//    fun getMovies(@Query("key_word") key_word: String): Call<List<MovieModel>>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetailsModel>

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("search/movie")
    fun searchMovie(@Query("query") query: String, @Query("page") page: Int): Single<MovieResponse>
}