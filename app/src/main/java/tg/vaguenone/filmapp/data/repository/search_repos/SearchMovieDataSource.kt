package tg.vaguenone.filmapp.data.repository.search_repos

import android.app.DownloadManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tg.vaguenone.filmapp.data.api.FIRST_PAGE
import tg.vaguenone.filmapp.data.api.TheMovieInterface
import tg.vaguenone.filmapp.data.models.Movie
import tg.vaguenone.filmapp.data.repository.NetworkState

class SearchMovieDataSource(
    private val apiService: TheMovieInterface,
    private val compositeDisposable: CompositeDisposable,
    val query: String
) : PageKeyedDataSource<Int, Movie>() {
    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(apiService.searchMovie(query, page).subscribeOn(Schedulers.io())
            .subscribe(
                {
                    callback.onResult(it.movies, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                },
                {
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("MovieDataSource", it.message)
                }
            ))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.searchMovie(query, page).subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.movies, params.key + 1)
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState.END_OF_LIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message)
                    })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("Not yet implemented")
    }
}