package com.example.main.Recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.main.R
import com.google.android.material.imageview.ShapeableImageView

class ViewAdapter(private val moviesList: ArrayList<Recycler>):
    RecyclerView.Adapter<ViewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = moviesList[position]
        holder.movieimage.setImageResource(currentItem.poster)
        holder.movietitle.text = currentItem.title
        holder.moviedetails.text = currentItem.description

    }

    override fun getItemCount(): Int {
       return moviesList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val movieimage: ShapeableImageView = itemView.findViewById(R.id.movieimage)
        val movietitle: TextView = itemView.findViewById(R.id.movietitle)
        val moviedetails: TextView = itemView.findViewById(R.id.moviedetails)
    }

}