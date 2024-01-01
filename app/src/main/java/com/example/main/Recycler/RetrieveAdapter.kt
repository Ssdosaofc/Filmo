package com.example.main.Recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.main.MainActivity2
import com.example.main.R
import com.example.main.api.Result
import com.example.main.api.Retrieve
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID

class RetrieveAdapter(val context: Context, val films: List<Retrieve>): Adapter<RetrieveAdapter.MovieViewHolder>() {
    var isInMyFavourite:Boolean = false
    private lateinit var firebaseAuth: FirebaseAuth

    class MovieViewHolder(itemView: View): ViewHolder(itemView){
        var moviePoster = itemView.findViewById<ImageView>(R.id.movieimage)
        var movieTitle = itemView.findViewById<TextView>(R.id.movietitle)
        var movieDescriptiom = itemView.findViewById<TextView>(R.id.moviedetails)
        var favButton = itemView.findViewById<ImageButton>(R.id.favButton)
        var movieID = itemView.findViewById<TextView>(R.id.movieID)
        var lang = itemView.findViewById<TextView>(R.id.lang)
        var pop = itemView.findViewById<TextView>(R.id.pop)
        var gen = itemView.findViewById<TextView>(R.id.gen)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        if (position != RecyclerView.NO_POSITION && position < films.size) {
            val film = films[position]

            holder.movieTitle.text = film.title
            holder.movieDescriptiom.text = film.overview
            holder.movieID.text = film.movieID
            holder.lang.text = film.originalLanguage
            holder.pop.text = film.popularity.toString()

            when (film.genre){
                28 -> holder.gen.text = "Action"
                12 -> holder.gen.text = "Adventure"
                16 -> holder.gen.text = "Animation"
                35 -> holder.gen.text = "Comedy"
                80 -> holder.gen.text = "Crime"
                99 -> holder.gen.text = "Documentary"
                18 -> holder.gen.text = "Drama"
                10751 -> holder.gen.text = "Family"
                14 -> holder.gen.text = "Fantasy"
                36 -> holder.gen.text = "History"
                27 -> holder.gen.text = "Horror"
                10402 -> holder.gen.text = "Music"
                9648 -> holder.gen.text = "Mystery"
                10749 -> holder.gen.text = "Romance"
                878 -> holder.gen.text = "Science Fiction"
                10770 -> holder.gen.text = "TV Movie"
                53 -> holder.gen.text = "Thriller"
                10752 -> holder.gen.text = "War"
                37 -> holder.gen.text = "Western"

            }

            Glide.with(context).load(POSTER_BASE_URL + film.poster).into(holder.moviePoster)

            firebaseAuth = FirebaseAuth.getInstance()

            if (firebaseAuth.currentUser != null) {
                checkIfFavourite(holder, film.movieID, position)
            }
        }
    }

    private fun checkIfFavourite(holder: MovieViewHolder, movieID: String, position: Int) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        reference.child(firebaseAuth.uid.toString()).child("Favourites")
            .child(movieID)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    isInMyFavourite = dataSnapshot.exists()
                    if (position < films.size) {
                        films[position].isFavorite = isInMyFavourite
                        updateFavoriteButton(holder, position)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error if needed
                }
            })

        holder.favButton.setOnClickListener {
            toggleFavoriteStatus(holder, position)
        }
    }

    private fun updateFavoriteButton(holder: MovieViewHolder, position: Int) {
        if (position < films.size) {
            val movie = films[position]

            if (movie.isFavorite) {
                holder.favButton.setImageResource(R.drawable.baseline_favorite_24_white)
            } else {
                holder.favButton.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }
    }

    private fun toggleFavoriteStatus(holder: MovieViewHolder, position: Int) {
        if (position < films.size) {
            val movie = films[position]

            if (movie.isFavorite) {
                removeFromFavourite(context, movie.movieID)
            } else {
                addToFavourite(context, movie.movieID, movie.title, movie.poster, movie.overview,movie.originalLanguage,movie.popularity.toString(),movie.genre)
            }
        }
    }

}
