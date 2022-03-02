package com.example.flixter

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
/*
* So, this one is a bit tricky, but here goes:
* To be able to use the recycler view we need a couple of things, and they are:
* A way to parse data that we are going to show(Movie data class),
* An adapter that takes the parsed data and binds it to a view holder,
* A view holder.
* */
const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val movies: List<Movie>):
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        // Getting the child views from the parent view(parent view here is the item_movie)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        /* Binding the parsed data to the view holder.
        *
        * We will attach the a click listener here, makes sense since this function is the
        * one responsible for giving the view our code data/logic.
        * */
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(movie: Movie){
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            /* Since we will be showing a different image if we change the orientation,
             * we need to make a new variable to pass the url for the image we are using and
            * do the implementation of choosing which of the two images to show else were.*/
            var movieImagePath = movie.posterIMageURL
            // logic to populate the movieImage variable
            val orientation = context.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Log.i(TAG, "portrait")
                movieImagePath = movie.posterIMageURL
                // ...
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.i(TAG, "landscape")
                movieImagePath = movie.backdropIMageURL
                // ...
            }

            Glide.with(context).load(movieImagePath).into(ivPoster)
        }

        override fun onClick(v: View?) {
            /*This click listener should:
            * 1. Tell us the movie that was clicked.
            * 2. Use the intent to navigate to the activity.
            * */
            // There is a method in the adapter that gets the adapter position.
            val movie = movies[adapterPosition]
            Toast.makeText(context, movie.title, LENGTH_SHORT).show() // Toast not working un emu but it works on physical device.
            Log.i("OnClick", "Movie clicked: ${movie.title}") // Logcat proves onClick works
            val intent = Intent(context,DetailActivity::class.java) // Context we are on and activity destination
            intent.putExtra(MOVIE_EXTRA,movie) // We make a Movie parceble to enable us to pas the entire object in this statement.
            context.startActivity(intent) // Starts the intent
        }
    }
    // onBind is a cheap operation, just assigns data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder $position")
        val movie = movies[position]
        holder.bind(movie)
    }
    // The onCreateViewHolder is a very expensive operation, creates/"inflates" the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        var view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movies.size
}
