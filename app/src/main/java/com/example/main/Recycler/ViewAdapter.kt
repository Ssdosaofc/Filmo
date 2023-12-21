package com.example.main.Recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.main.Fragments.Home
import com.example.main.R
import com.example.main.api.Result

class ViewAdapter(val context: Context, val films: List<Result>): Adapter<ViewAdapter.MovieViewHolder>() {
    class MovieViewHolder(itemView: View): ViewHolder(itemView){
        var moviePoster = itemView.findViewById<ImageView>(R.id.movieimage)
        var movieTitle = itemView.findViewById<TextView>(R.id.movietitle)
        var movieDescriptiom = itemView.findViewById<TextView>(R.id.moviedetails)
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
    }
}