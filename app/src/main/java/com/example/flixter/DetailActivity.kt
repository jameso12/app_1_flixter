package com.example.flixter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView

private const val TAG = "DetailActivity"
class DetailActivity : AppCompatActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var tvOverview: TextView
    private lateinit var rbRatingBar: RatingBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        tvTitle = findViewById(R.id.tvTitle)
        tvOverview = findViewById(R.id.tvOverview)
        rbRatingBar = findViewById(R.id.rbMovieRatingAVG)
        // We cast to Movie(default is nullable).
        val movie = intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie
        Log.i(TAG, "Movie: ${movie.title}")
        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        rbRatingBar.rating = movie.rating.toFloat() // rating bar expects a float so we cast
    }
}