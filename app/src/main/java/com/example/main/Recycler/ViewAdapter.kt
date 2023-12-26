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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID

const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
class ViewAdapter(val context: Context, val films: List<Result>): Adapter<ViewAdapter.MovieViewHolder>() {
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

        val position = holder.adapterPosition
        if (position != RecyclerView.NO_POSITION && position < films.size)  {
            val film = films[position]

            holder.movieTitle.text = film.title
            holder.movieDescriptiom.text = film.overview
            holder.movieID.text = film.id.toString()

            Glide.with(context).load(POSTER_BASE_URL + film.posterPath).into(holder.moviePoster)

            firebaseAuth = FirebaseAuth.getInstance()

            if (firebaseAuth.currentUser != null) {
                checkIfFavourite(holder, film.id.toString(), position)
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
                removeFromFavourite(context, movie.id.toString())
            } else {
                addToFavourite(context, movie.id.toString(), movie.title, movie.posterPath, movie.overview)
            }
        }
    }




}

fun removeFromFavourite(context: Context, movieID: String) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    ref.child(firebaseAuth.uid.toString()).child("Favourites")
        .child(movieID)
        .removeValue()
        .addOnSuccessListener {
            Toast.makeText(context, "Removed from favourites", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Could not Remove from Favourites", Toast.LENGTH_SHORT)
                .show()
        }
}

fun addToFavourite(context: Context, movieID: String,title: String, poster: String, overview: String) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val hashMap: HashMap<String, Any> = HashMap()
    hashMap["movieID"] = movieID
    hashMap["title"] = title
    hashMap["poster"] = poster
    hashMap["overview"] = overview

    val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    ref.child(firebaseAuth.uid.toString()).child("Favourites")
        .child(movieID)
        .setValue(hashMap)
        .addOnSuccessListener {
            Toast.makeText(context, "Added to favourites", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Could not Add to Favourites", Toast.LENGTH_SHORT).show()
        }
}

