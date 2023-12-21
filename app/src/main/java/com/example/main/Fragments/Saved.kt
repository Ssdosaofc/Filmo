package com.example.main.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.main.R


class Saved : Fragment() {
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_saved, container, false)

/*
        imageId = arrayOf(

        )

        title = arrayOf(

        )

        description = arrayOf(

        )


        newRecyclerView = view.findViewById(R.id.favouriteList)
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
}