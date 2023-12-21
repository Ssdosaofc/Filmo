package com.example.main.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.main.R
import com.example.main.Recycler.ViewAdapter
import com.example.main.api.Data
import com.example.main.api.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : Fragment() {

    lateinit var adapter: ViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        getMovies()
        return view

    }
/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getMovies()
    }
*/
    private fun getMovies() {
        val film = MovieService.movieInterface.getMovies("en-US", 1)
        film.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                val data = response.body()
                if (data != null) {
                    Log.d("Filmopedia", data.toString())
                    adapter = ViewAdapter(requireContext(), data.results)
                    view?.findViewById<RecyclerView>(R.id.popularlist)?.adapter = adapter
                    view?.findViewById<RecyclerView>(R.id.popularlist)?.layoutManager = LinearLayoutManager(requireContext())
                }

            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.d("Filmopedia", "Error", t)
            }
        })
    }


}