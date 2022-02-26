package com.example.flixter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView
    /* Making a recycler view
    * 1. Define a data model class as the data source.
    * 2. Add the recycler view to the layout.
    * 3. Create a costume row layout XML file to visualize the items
    * 4.Create an adapter and view holder to render the item
    * 5.Bind the adapter to the data source to populate the RecyclerView
    * 6. Bind a layout manager to the recycler view
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMovies = findViewById(R.id.rvMovies)

        val movieAdapter = MovieAdapter(this,movies)

        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)
        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG,"onFailure: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess: $json")
                /* We removed the ? after the JSON type as a work around to android studio
                * warning. The reason it is safe to remove the nullable is because this method
                * will ONLY execute on a successful get request.*/
               try {
                   val movieJasonArray = json.jsonObject.getJSONArray("results")
                   movies.addAll(Movie.fromJsonArray(movieJasonArray))
                   movieAdapter.notifyDataSetChanged()
                   Log.i(TAG, "Movie list: $movies")
               } catch(e:JSONException){
                   Log.e(TAG,"Encountered exception $e")

               }

            }

        })
    }
}