package tg.vaguenone.filmapp.ui.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import tg.vaguenone.filmapp.data.api.POST_PER_PAGE
import tg.vaguenone.filmapp.data.api.TheMovieInterface
import tg.vaguenone.filmapp.data.models.Movie
import tg.vaguenone.filmapp.data.repository.MovieDataSource
import tg.vaguenone.filmapp.data.repository.MovieDataSourceFactory
import tg.vaguenone.filmapp.data.repository.NetworkState

class MoviePageListRepository(private val apiService: TheMovieInterface) {
    private lateinit var moviePagedList: LiveData<PagedList<Movie>>
    private lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        movieDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)
        val config: PagedList.Config =
            PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(
                POST_PER_PAGE
            ).build()
        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()
        return moviePagedList
    }

    fun getNetWorkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.moviesLiveDataSource,
            MovieDataSource::networkState
        )
    }
}