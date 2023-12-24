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

        Glide.with(context).load(POSTER_BASE_URL + film.posterPath).into(holder.moviePoster)

        firebaseAuth = FirebaseAuth.getInstance()
/*
        if (firebaseAuth.currentUser != null) {
            checkIfFavourite(holder, film.title, film.overview, film.posterPath)
        }

 */
    }

    private fun checkIfFavourite(holder: MovieViewHolder, title: String, overview: String, poster: String){
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        reference.child(firebaseAuth.uid.toString()).child("Favourites")
            .child(title)
            .child(overview)
            .child(poster)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    isInMyFavourite = dataSnapshot.exists()
                    if (isInMyFavourite){
                        holder.favButton.setImageResource(R.drawable.baseline_favorite_24_white)
                    }else{
                        holder.favButton.setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })

        holder.favButton.setOnClickListener{
            if (isInMyFavourite){
                removeFromFavourite(context, holder.movieTitle.toString(), holder.movieDescriptiom.toString(), holder.moviePoster)
            }else{
                addToFavourite(context, holder.movieTitle.toString(), holder.movieDescriptiom.toString(), holder.moviePoster)
            }
        }
    }


}

fun removeFromFavourite(context: Context, title: String, overview: String, poster: ImageView) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val hashMap: HashMap<String, Any> = HashMap()
    hashMap["title"] = title
    hashMap["overview"] = overview
    hashMap["poster"] = poster

    val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    ref.child(firebaseAuth.uid.toString()).child("Favourites")
        .child(title)
        .child(overview)
        .child(poster.toString())
        .removeValue()
        .addOnSuccessListener {
            Toast.makeText(context, "Removed from favourites", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Could not Remove from Favourites", Toast.LENGTH_SHORT)
                .show()
        }
}

fun addToFavourite(context: Context, title: String, overview: String, poster: ImageView) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val hashMap: HashMap<String, Any> = HashMap()
    hashMap["title"] = title
    hashMap["overview"] = overview
    hashMap["poster"] = poster

    val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    ref.child(firebaseAuth.uid.toString()).child("Favourites")
        .child(title)
        .child(overview)
        .child(poster.toString())
        .setValue(hashMap)
        .addOnSuccessListener {
            Toast.makeText(context, "Added to favourites", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Could not Add to Favourites", Toast.LENGTH_SHORT).show()
        }


}
