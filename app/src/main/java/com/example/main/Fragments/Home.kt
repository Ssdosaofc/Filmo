package com.example.main.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.main.R
import com.example.main.api.Data
import com.example.main.api.MovieInterface
import com.example.main.api.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : Fragment() {
    /*
        private lateinit var newRecyclerView: RecyclerView
        private lateinit var newArrayList: ArrayList<Recycler>
        lateinit var imageId: Array<Int>
        lateinit var title: Array<String>
        lateinit var description: Array<String>

     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)


        /*
                imageId = arrayOf(

                )

                title = arrayOf(

                )

                description = arrayOf(

                )


                newRecyclerView = view.findViewById(R.id.popularlist)
                val layoutManager = LinearLayoutManager(requireContext())
                newRecyclerView.layoutManager = layoutManager

                //newRecyclerView.layoutManager = LinearLayoutManager(this)
                newRecyclerView.setHasFixedSize(true)

                newArrayList = arrayListOf<Recycler>()
                getUserdata()
         */
        return view

    }

    /*
        private fun getUserdata() {
            for (i in imageId.indices){
                val movie = Recycler(imageId[i], title[i], description[i])
                newArrayList.add(movie)
            }

            newRecyclerView.adapter = ViewAdapter(newArrayList)
        }
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getMovies()
    }

    private fun getMovies() {
        val film = MovieService.movieInterface.getMovies("en-US", 1)
        film.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                val data = response.body()
                if (data != null) {
                    Log.d("Filmopedia", data.toString())
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.d("Filmopedia", "Error", t)
            }
        })
    }


}