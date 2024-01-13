package com.example.main.ui.Search

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.main.R
import com.example.main.Recycler.ViewAdapter
import com.example.main.api.Data
import com.example.main.api.Result
import com.example.main.api.SearchInterface
import com.example.main.api.SearchService
import com.example.main.databinding.FragmentDashboardBinding
import org.checkerframework.checker.units.qual.K
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class DashboardFragment : Fragment() {
    lateinit var adapter: ViewAdapter

    private var _binding: FragmentDashboardBinding? = null

    private lateinit var searchList: RecyclerView

    private var allFilmsList: List<Result> = emptyList()

    private var selectedButton: Button? = null
    private var filmsLoaded: Boolean = false

    private var isActionSelected: Boolean = false

    private lateinit var buttonIdPairs: List<Pair<Button, Int>>
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        searchList = binding.searchList
        searchList.itemAnimator = null

        adapter = ViewAdapter(requireContext(), emptyList())
        searchList.adapter = adapter
        searchList.layoutManager = LinearLayoutManager(requireContext())

        val searchbar: SearchView = binding.searchBar
        //val filter:Button = binding.filter
        //val progressBar = binding.progress
        val action: Button = binding.Action
        val adventure: Button = binding.Adventure
        val animation: Button = binding.Animation
        val comedy: Button = binding.Comedy
        val crime: Button = binding.Crime
        val documentary: Button = binding.Documentary
        val drama: Button = binding.Drama
        val family: Button = binding.Family
        val fantasy: Button = binding.Fantasy
        val history: Button = binding.History
        val horror: Button = binding.Horror
        val music: Button = binding.Music
        val mystery: Button = binding.Mystery
        val romance: Button = binding.Romance
        val scienceFiction: Button = binding.ScienceFiction
        val tvMovie: Button = binding.TVMovie
        val thriller: Button = binding.Thriller
        val war: Button = binding.War
        val western: Button = binding.Western
//      val actionText: String = binding.Action.text.toString()

        buttonIdPairs = listOf(
            action to 28,
            adventure to 12,
            animation to 16,
            comedy to 35,
            crime to 80,
            documentary to 99,
            drama to 18,
            family to 10751,
            fantasy to 14,
            history to 36,
            horror to 27,
            music to 10402,
            mystery to 9648,
            romance to 10749,
            scienceFiction to 878,
            tvMovie to 10770,
            thriller to 53,
            war to 10752,
            western to 37
        )



        dashboardViewModel.text.observe(viewLifecycleOwner) {

            if (!filmsLoaded && isAdded) {
                buttonIdPairs.forEach { (button, id) ->
                    filterButton(button, id)
                }
                filmsLoaded = true
            }

            searchbar.setOnQueryTextListener(object : OnQueryTextListener {

                override fun onQueryTextSubmit(query: String): Boolean {
                    if (isAdded) {
                        //binding?.progress?.visibility = View.VISIBLE
                        searchResults(
                            searchList,searchbar,
                            //progressBar,
                            null)

                    }
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    val text = newText.lowercase(Locale.getDefault())
                    if (text.isNotEmpty()){
                        searchResults(
                            searchList,searchbar,
                            //progressBar,
                            null)
                    }
                    return false
                }
            })
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ViewAdapter(requireContext(), emptyList())
        searchList.adapter = adapter
    }

    private fun searchResults(searchList: RecyclerView, searchbar: SearchView,
                              //progressBar: ProgressBar,
                              language: String?) {
        val film = SearchService.searchInterface.getMovies(searchbar.query.toString(),language)
        film.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (isAdded) {
                    val data = response.body()
                    if (data != null) {
                        Log.d("Filmopedia", data.toString())

                        adapter.updateFilms(data.results)

                        searchList.let { recyclerView ->
                            adapter.let {
                                recyclerView.adapter = it
                                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                                filmsLoaded = true
                            }
                        }

                        filmsLoaded = true
                    }
                    //progressBar?.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                if (isAdded) {
                    Log.d("Filmopedia", "Error", t)
                    //progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun filterButton(button: Button, id: Int) {
        button.setOnClickListener {
            if (button == selectedButton) {
                selectedButton?.setBackgroundResource(R.drawable.filterbutton)
                selectedButton = null
                isActionSelected = false
                updateMovies()
            } else {
                selectedButton?.setBackgroundResource(R.drawable.filterbutton)
                isActionSelected = true

                button.setBackgroundResource(R.drawable.filterbuttonselected)
                selectedButton = button

                val genreFilms = if (isActionSelected) {
                    adapter.films.filter { it.genreIds.contains(id) }
                } else {
                    allFilmsList
                }

                Log.d("Filmopedia", "Filtered Films: ${genreFilms.size}")
                requireActivity().runOnUiThread {
                    adapter.updateFilms(genreFilms)
                }
            }
        }
    }

    private fun updateMovies() {
        if (selectedButton == null) {
            isActionSelected = false
            requireActivity().runOnUiThread {
                adapter.updateFilms(allFilmsList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchList?.adapter = null
        _binding = null
    }
}