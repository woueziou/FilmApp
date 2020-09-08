package tg.vaguenone.filmapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import tg.vaguenone.filmapp.data.api.TheMovieClient
import tg.vaguenone.filmapp.data.api.TheMovieInterface
import tg.vaguenone.filmapp.data.repository.NetworkState
import tg.vaguenone.filmapp.ui.popular_movies.MoviePageListRepository
import tg.vaguenone.filmapp.ui.popular_movies.PopularMovieAdapter
import tg.vaguenone.filmapp.ui.single_movie_details.SingleMovie
import tg.vaguenone.filmapp.ui.popular_movies.PopularMoviesActivityViewModel

class MainActivity : AppCompatActivity() {
    lateinit var moviePageListRepository: MoviePageListRepository
    private lateinit var viewModel: PopularMoviesActivityViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            val intent = Intent(this, SingleMovie::class.java)
            intent.putExtra("id", 550)
            this.startActivity(intent)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_popular_movies
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


//        val apiService: TheMovieInterface = TheMovieClient.getClient()
//        moviePageListRepository = MoviePageListRepository(apiService)
//        viewModel = getViewModel()
//
//        val movieAdapter = PopularMovieAdapter(this)
//        val gridLayoutManager = GridLayoutManager(this, 3)
//        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                val viewType = movieAdapter.getItemViewType(position)
//                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1
//                else return 3
//            }
//        };
//        rv_movie_list.layoutManager = gridLayoutManager
//        rv_movie_list.setHasFixedSize(true)
//        rv_movie_list.adapter = movieAdapter
//
//        viewModel.moviePagedList.observe(this, Observer { movieAdapter.submitList(it) })

//        viewModel.networkState.observe(this, Observer {
//            progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
//            txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE
//
//            if (!viewModel.listIsEmpty()) {
//                movieAdapter.setNetworkState(it)
//            }
//        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    private fun getViewModel(): PopularMoviesActivityViewModel {
//        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                @Suppress("UNCHECKED_CAST")
//                return PopularMoviesActivityViewModel(moviePageListRepository) as T
//            }
//        })[PopularMoviesActivityViewModel::class.java]
//    }

}