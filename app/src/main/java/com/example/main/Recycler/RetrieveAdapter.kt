package com.example.main.Recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val film = films[position]

        holder.movieTitle.text = film.title
        holder.movieDescriptiom.text = film.overview
        holder.movieID.text = film.movieID

        Glide.with(context).load(POSTER_BASE_URL + film.poster).into(holder.moviePoster)

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            checkIfFavourite(holder, film.movieID)
        }


    }

    private fun checkIfFavourite(holder: MovieViewHolder, movieID: String) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        reference.child(firebaseAuth.uid.toString()).child("Favourites")
            .child(movieID)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    isInMyFavourite = dataSnapshot.exists()
                    films[holder.adapterPosition].isFavorite = isInMyFavourite
                    updateFavoriteButton(holder)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error if needed
                }
            })

        holder.favButton.setOnClickListener {
            toggleFavoriteStatus(holder)
        }
    }

    private fun updateFavoriteButton(holder: MovieViewHolder) {
        val movie = films[holder.adapterPosition]

        if (movie.isFavorite) {
            holder.favButton.setImageResource(R.drawable.baseline_favorite_24_white)
        } else {
            holder.favButton.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    private fun toggleFavoriteStatus(holder: MovieViewHolder) {
        val movie = films[holder.adapterPosition]

        if (movie.isFavorite) {
            removeFromFavourite(context, movie.movieID)
        } else {
            addToFavourite(context, movie.movieID, movie.title,movie.movieID, movie.overview)
        }
    }

}
