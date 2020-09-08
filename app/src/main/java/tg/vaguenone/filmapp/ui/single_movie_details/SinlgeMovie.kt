package tg.vaguenone.filmapp.ui.single_movie_details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_single_movie.*
import tg.vaguenone.filmapp.R
import tg.vaguenone.filmapp.data.api.TheMovieInterface
import tg.vaguenone.filmapp.data.api.POSTER_BASE_URL
import tg.vaguenone.filmapp.data.api.TheMovieClient
import tg.vaguenone.filmapp.data.models.MovieDetailsModel
import tg.vaguenone.filmapp.data.repository.NetworkState

@Suppress("DEPRECATION")
class SingleMovie : AppCompatActivity() {
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository
//    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        super.onCreate(savedInstanceState, persistentState)


//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)
        val movieId: Int = intent.getIntExtra("id", 1)
        val theMovieService: TheMovieInterface = TheMovieClient.getClient()
        movieDetailsRepository = MovieDetailsRepository(theMovieService)
        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            bindUi(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    private fun bindUi(it: MovieDetailsModel) {
        movie_title.text = it.title
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.voteAverage.toString()
        movie_overview.text = it.overview
        movie_budget.text = it.budget.toString()
        movie_tagline.text = it.tagline
        movie_revenue.text = it.revenue.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        val moviePosterUrl = POSTER_BASE_URL + it.posterPath

        Glide.with(this).load(moviePosterUrl).into(iv_movie_poster)

    }


    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieDetailsRepository, movieId) as T

            }
        })[SingleMovieViewModel::class.java]

    }
}