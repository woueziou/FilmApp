package tg.vaguenone.filmapp.ui.search_movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import tg.vaguenone.filmapp.R
import tg.vaguenone.filmapp.data.models.Movie
import tg.vaguenone.filmapp.data.repository.NetworkState
import tg.vaguenone.filmapp.ui.popular_movies.PopularMovieAdapter

class SearchAdapter(public val context: Context) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(
    PopularMovieAdapter.MovieDiffCallBack()
) {
    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2
    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
            return PopularMovieAdapter.MovieItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.networ_state_item, parent, false)
            return PopularMovieAdapter.NetworkStateItemViewHolder(view)
        }    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}