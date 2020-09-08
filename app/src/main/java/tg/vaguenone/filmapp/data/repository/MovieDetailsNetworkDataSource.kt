package tg.vaguenone.filmapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tg.vaguenone.filmapp.data.api.TheMovieInterface
import tg.vaguenone.filmapp.data.models.MovieDetailsModel
import java.lang.Exception

class MovieDetailsNetworkDataSource(
    private val theMovieService: TheMovieInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetailsModel>()
    val downloadedMovieResponse: LiveData<MovieDetailsModel>
        get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(theMovieService.getMovieDetails(movieId).subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _downloadedMovieDetailsResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDetailsSource", it.message)
                    }
                ))
        } catch (
            e: Exception
        ) {
            Log.e("MovieDetailsSource", e.message)
        }
    }


}