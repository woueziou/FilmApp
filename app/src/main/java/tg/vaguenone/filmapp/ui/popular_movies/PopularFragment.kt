package tg.vaguenone.filmapp.ui.popular_movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import tg.vaguenone.filmapp.R
import tg.vaguenone.filmapp.data.api.TheMovieClient
import tg.vaguenone.filmapp.data.api.TheMovieInterface
import tg.vaguenone.filmapp.data.repository.NetworkState

class PopularFragment : Fragment() {

    private lateinit var popVM: PopularMoviesActivityViewModel
    lateinit var moviePageListRepository: MoviePageListRepository


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val apiService: TheMovieInterface = TheMovieClient.getClient()
        moviePageListRepository = MoviePageListRepository(apiService)
        popVM = getViewModel()
        val activity = activity as Context

        val root = inflater.inflate(R.layout.fragment_popular_movies, container, false)

        val movieAdapter = PopularMovieAdapter(activity)
        val gridLayoutManager = GridLayoutManager(activity, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                return if (viewType == movieAdapter.MOVIE_VIEW_TYPE) 1
                else 3
            }
        }
        val list: RecyclerView = root.findViewById(R.id.rv_movie_list)

        list.layoutManager = gridLayoutManager
        list.setHasFixedSize(true)
        list.adapter = movieAdapter

        popVM.moviePagedList.observe(viewLifecycleOwner, Observer { movieAdapter.submitList(it) })
        popVM.networkState.observe(viewLifecycleOwner, Observer {
            progress_bar_popular.visibility =
                if (popVM.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility =
                if (popVM.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!popVM.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })

        @Suppress("DEPRECATION")
        popVM = ViewModelProviders.of(this).get(PopularMoviesActivityViewModel::class.java)
        return root

//        return super.onCreateView(inflater, container, savedInstanceState)
    }


    private fun getViewModel(): PopularMoviesActivityViewModel {
        @Suppress("DEPRECATION")
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PopularMoviesActivityViewModel(moviePageListRepository) as T
            }
        })[PopularMoviesActivityViewModel::class.java]
    }
}