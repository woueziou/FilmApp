package tg.vaguenone.filmapp.ui.search_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import tg.vaguenone.filmapp.data.models.Movie
import tg.vaguenone.filmapp.data.repository.NetworkState

class SearchViewModel(private val movieRep: SearchPagedListRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    lateinit var data: LiveData<PagedList<Movie>>
    fun moviePagedList(query: String): LiveData<PagedList<Movie>> {
        data = movieRep.searchMoviePagedList(compositeDisposable, query)
        return data
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRep.getNetWorkState()
    }

    fun listIsEmpty(): Boolean {
        return data.value?.isEmpty() ?: true
    }
}