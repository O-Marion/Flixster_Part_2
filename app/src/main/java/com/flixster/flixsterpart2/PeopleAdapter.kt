package com.flixster.flixsterpart2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val PEOPLE_EXTRA = "PEOPLE_EXTRA"
const val MOVIE_BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w500/"
private const val TAG = "MoviesAdapter"

class PeopleAdapter(private val context: Context, private val people: List<PopularPeople.Result>) :
    RecyclerView.Adapter<PeopleAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = people[position]
        holder.bind(person)
    }

    override fun getItemCount() = people.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        private val profileImageView = itemView.findViewById<ImageView>(R.id.profile)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(person: PopularPeople.Result) {
            Glide.with(context)
                .load("$MOVIE_BACKDROP_BASE_URL${person.profilePath}")
                .into(profileImageView)
        }

        override fun onClick(p0: View?) {
            val person = people[adapterPosition]
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(PEOPLE_EXTRA, person)
            context.startActivity(intent)
        }
    }
}