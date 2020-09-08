package tg.vaguenone.filmapp.data.repository.search_repos

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import tg.vaguenone.filmapp.data.api.TheMovieClient
import tg.vaguenone.filmapp.data.api.TheMovieInterface
import tg.vaguenone.filmapp.data.models.Movie

class SearchDSFactory(
    private val apiService: TheMovieInterface,
    private val compositeDisposable: CompositeDisposable,
    val query: String
) : DataSource.Factory<Int, Movie>() {
    val moviesLiveDataSource = MutableLiveData<SearchMovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val dataSource = SearchMovieDataSource(apiService, compositeDisposable, query)
        moviesLiveDataSource.postValue(dataSource)
        return dataSource
    }

}