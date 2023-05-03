package com.flixster.flixsterpart2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity/"
private const val MOVIE_DB_API_KEY = BuildConfig.API_KEY
private const val PEOPLE_URL =
    "https://api.themoviedb.org/3/person/popular?api_key=${MOVIE_DB_API_KEY}&language=en-US"

class MainActivity : AppCompatActivity() {

    private val people  = mutableListOf<PopularPeople.Result>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val peopleRecyclerView = findViewById<RecyclerView>(R.id.rvPeople)
        val peopleAdapter = PeopleAdapter(this, people)

        peopleRecyclerView.adapter = peopleAdapter
        peopleRecyclerView.layoutManager = GridLayoutManager(this,2)

        val client = AsyncHttpClient()
        client.get(PEOPLE_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch popular people: $statusCode $response")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched popular people: $json")
                try {
                    val response = Gson().fromJson(json.jsonObject.toString(), PopularPeople::class.java)
                    response.results?.let { list ->
                        people.addAll(list)
                        peopleAdapter.notifyDataSetChanged()
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }
}