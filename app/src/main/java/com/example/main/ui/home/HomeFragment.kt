package com.example.main.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.main.R
import com.example.main.Recycler.ViewAdapter
import com.example.main.api.Data
import com.example.main.api.MovieService
import com.example.main.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    lateinit var adapter: ViewAdapter

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val popularList: RecyclerView = binding.popularList
        homeViewModel.text.observe(viewLifecycleOwner) {
            val film = MovieService.movieInterface.getMovies("en-US", 1)
            film.enqueue(object : Callback<Data> {
                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    val data = response.body()
                    if (data != null) {
                        Log.d("Filmopedia", data.toString())
                        adapter = ViewAdapter(requireContext(), data.results)
                        popularList.adapter = adapter
                        popularList.layoutManager = LinearLayoutManager(requireContext())
                    }

                }

                override fun onFailure(call: Call<Data>, t: Throwable) {
                    Log.d("Filmopedia", "Error", t)
                }
            })
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
/*
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

 */
}