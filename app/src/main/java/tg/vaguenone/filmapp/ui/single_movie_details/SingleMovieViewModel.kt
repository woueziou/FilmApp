package tg.vaguenone.filmapp.ui.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import tg.vaguenone.filmapp.data.models.MovieDetailsModel
import tg.vaguenone.filmapp.data.models.MovieModel
import tg.vaguenone.filmapp.data.repository.NetworkState

class SingleMovieViewModel(private  val movieRepository:MovieDetailsRepository, movieId:Int) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val movieDetails : LiveData<MovieDetailsModel> by lazy {
        movieRepository.fetchSingleDetails(compositeDisposable, movieId)
    }

    val networkState :LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}