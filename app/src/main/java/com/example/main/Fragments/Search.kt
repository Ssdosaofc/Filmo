package com.example.main.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.main.R

class Search : Fragment() {
/*
    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: Transition.ViewAdapter
*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search, container, false)
        return view
    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.searchResults.observe(viewLifecycleOwner, Observer { movies ->
            // Update the adapter with the new list of movies
            adapter.submitList(movies)
        })
    }
*/
}