package com.flixster.flixsterpart2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var personNameTextView: TextView
    private lateinit var movieNameTextView: TextView
    private lateinit var overviewTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        backdrop = findViewById(R.id.backdrop)
        poster = findViewById(R.id.poster)
        personNameTextView = findViewById(R.id.personName)
        movieNameTextView = findViewById(R.id.movieName)
        overviewTextView = findViewById(R.id.overview)

        val person = intent.getSerializableExtra(PEOPLE_EXTRA) as PopularPeople.Result
        val knownFor = person.knownFor?.get(0)

        personNameTextView.text = person.name
        knownFor?.let {
            movieNameTextView.text =  "Known For : ${it.name ?: it.title}"
        }
        overviewTextView.text = knownFor?.overview
        Glide.with(this)
            .load("$MOVIE_BACKDROP_BASE_URL${person.profilePath}")
            .into(backdrop)
        Glide.with(this)
            .load("$MOVIE_BACKDROP_BASE_URL${knownFor?.posterPath}")
            .into(poster)
    }
}