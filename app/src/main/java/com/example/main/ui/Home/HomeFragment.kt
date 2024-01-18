package com.example.main.ui.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.main.Recycler.ViewAdapter
import com.example.main.api.Data
import com.example.main.api.MovieService
import com.example.main.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    lateinit var adapter: ViewAdapter

    private lateinit var popularList: RecyclerView

    private var _binding: FragmentHomeBinding? = null
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

        popularList = binding.popularList
        homeViewModel.text.observe(viewLifecycleOwner) {

            popularList.postDelayed({
                getPopularMovies(popularList)
            }, 500)

            //getPopularMovies(popularList)

        }
        return root
    }

    private fun getPopularMovies(popularList: RecyclerView) {
//https://juliensalvi.medium.com/safe-delay-in-android-views-goodbye-handlers-hello-coroutines-cd47f53f0fbf

        val film = MovieService.movieInterface.getMovies("en-US", 1)
        film.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                val context = context
                if (context != null && isAdded) {
                    val data = response.body()
                    if (data != null) {
                        Log.d("Filmopedia", data.toString())

                        if (isAdded) {
                            adapter = ViewAdapter(context, data.results)
                            popularList.adapter = adapter
                            popularList.layoutManager = LinearLayoutManager(context)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                if (context != null && isAdded) {
                    Log.d("Filmopedia", "Error", t)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //popularList.adapter = null
        _binding = null
    }
}