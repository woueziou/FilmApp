package tg.vaguenone.filmapp.ui.search_movie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_search_ui.*
import tg.vaguenone.filmapp.R
import tg.vaguenone.filmapp.data.api.TheMovieClient
import tg.vaguenone.filmapp.data.api.TheMovieInterface
import tg.vaguenone.filmapp.data.repository.NetworkState

class SearchFragment : Fragment() {
    private lateinit var uiViewModel: SearchViewModel
    lateinit var moviePageListRep: SearchPagedListRepository
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val apiService: TheMovieInterface = TheMovieClient.getClient()
        moviePageListRep = SearchPagedListRepository(apiService)
        uiViewModel = getViewModel()
        val root = inflater.inflate(R.layout.fragment_search_ui, container, false)
        val searchEntryField: TextInputEditText = root.findViewById(R.id.search_ui_textfield)
        val searchBtn: Button = root.findViewById(R.id.search_ui_btn)

        searchBtn.setOnClickListener {

            val query: String = searchEntryField.text.toString();
            Log.d("SEARCH VIEW", query)
            searchFilm(root, query)

        }
        return root
    }

    private fun searchFilm(rootView: View, query: String) {
        val activity = activity as Context
        val adapter = SearchAdapter(activity)
        val gridLayoutManager = GridLayoutManager(activity, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position)
                return if (viewType == adapter.MOVIE_VIEW_TYPE) 1
                else 3
            }
        }
        val recyclerView: RecyclerView = rootView.findViewById(R.id.search_ui_recycler_view)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        uiViewModel.moviePagedList(query)
            .observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        uiViewModel.networkState.observe(viewLifecycleOwner, Observer {
            search_ui_progress_bar.visibility =
                if (uiViewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE

            search_ui_txt_error.visibility =
                if (uiViewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!uiViewModel.listIsEmpty()) {
                adapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel(): SearchViewModel {
        @Suppress("DEPRECATION")
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(moviePageListRep) as T
            }
        })[SearchViewModel::class.java]
    }
}