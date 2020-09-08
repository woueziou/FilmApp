package tg.vaguenone.filmapp.ui.single_movie_details

import androidx.lifecycle.LiveData
import io.reactivex.disposables.CompositeDisposable
import tg.vaguenone.filmapp.data.api.TheMovieInterface
import tg.vaguenone.filmapp.data.models.MovieDetailsModel
import tg.vaguenone.filmapp.data.repository.MovieDetailsNetworkDataSource
import tg.vaguenone.filmapp.data.repository.NetworkState

class MovieDetailsRepository(private val theMovieService: TheMovieInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource
    fun fetchSingleDetails(
        conpositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetailsModel> {
        movieDetailsNetworkDataSource =
            MovieDetailsNetworkDataSource(theMovieService, conpositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)
        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }


    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}