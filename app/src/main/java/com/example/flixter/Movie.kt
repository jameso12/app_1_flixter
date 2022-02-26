package com.example.flixter

import org.json.JSONArray

/*This file contains a data class. Why data class? Because kotlin has support for
* a special type of class class called "data class", this wll make it clear that we
* are just using this class to bundle up data. */
data class Movie (
    val movieID: Int,
    private val posterPath: String,
    val title: String,
    val overview: String,
    ){
    // Making sure we get the image size we want. Ideally one would see all the available sizes
    // and pick the ideal one.
    val posterIMageURL = "https://image.tmdb.org/t/p/w342/$posterPath"
    /*Companion objects allows us to call class methods without having an instance,
    * in other words, it will allow us to have static functions in kotlin.
    * */
    companion object{
        fun fromJsonArray(movieJasonArray: JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>()
            for(i in 0 until movieJasonArray.length()){
                val movieJson = movieJasonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview")
                    )
                )
            }
            return movies
        }
    }
}