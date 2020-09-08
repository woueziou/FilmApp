package tg.vaguenone.filmapp.ui.search_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import tg.vaguenone.filmapp.data.api.POST_PER_PAGE
import tg.vaguenone.filmapp.data.api.TheMovieInterface
import tg.vaguenone.filmapp.data.models.Movie
import tg.vaguenone.filmapp.data.repository.MovieDataSource
import tg.vaguenone.filmapp.data.repository.NetworkState
import tg.vaguenone.filmapp.data.repository.search_repos.SearchDSFactory
import tg.vaguenone.filmapp.data.repository.search_repos.SearchMovieDataSource

class SearchPagedListRepository(private val apiService: TheMovieInterface) {
    private lateinit var moviePagedList: LiveData<PagedList<Movie>>
    private lateinit var searchMovieSourceFactory: SearchDSFactory

    fun searchMoviePagedList(
        compositeDisposable: CompositeDisposable,
        query: String
    ): LiveData<PagedList<Movie>> {
        searchMovieSourceFactory = SearchDSFactory(apiService, compositeDisposable, query)
        val config: PagedList.Config =
            PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(
                POST_PER_PAGE
            ).build()
        moviePagedList = LivePagedListBuilder(searchMovieSourceFactory, config).build()
        return moviePagedList
    }

    fun getNetWorkState(): LiveData<NetworkState> {
        return Transformations.switchMap<SearchMovieDataSource, NetworkState>(
            searchMovieSourceFactory.moviesLiveDataSource,
            SearchMovieDataSource::networkState
        )
    }
}