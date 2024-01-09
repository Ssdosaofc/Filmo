package com.example.main.ui.Search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.ProgressBar
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

    private var searchList: RecyclerView? = null

    var isSelected:Boolean = false
    private var allFilmsList: List<Result> = emptyList()

    private var selectedGenres: MutableSet<Int> = HashSet()
    private var selectedPopularity: Int? = null


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
        searchList= binding.searchList
        val searchbar: SearchView = binding.searchBar
        //val filter:Button = binding.filter
        val progressBar = binding.progress
        val action:Button = binding.Action
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

        val above1000: Button = binding.above1000
        val above500: Button = binding.to1000
        val above100: Button = binding.to500
        val above0: Button = binding.to100


        dashboardViewModel.text.observe(viewLifecycleOwner) {

            searchbar.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (isAdded) {
                        progressBar.visibility = View.VISIBLE
                        searchResults(searchList!!,searchbar,progressBar,null)

                    }
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    val text = newText.lowercase(Locale.getDefault())
                    if (text.isNotEmpty()){
                        searchResults(searchList!!,searchbar,progressBar,null)
                    }

                    return false
                }


            })

            filterButton(action, 28)
            filterButton(adventure, 12)
            filterButton(animation, 16)
            filterButton(comedy, 35)
            filterButton(crime, 80)
            filterButton(documentary, 99)
            filterButton(drama, 18)
            filterButton(family, 10751)
            filterButton(fantasy, 14)
            filterButton(history, 36)
            filterButton(horror, 27)
            filterButton(music, 10402)
            filterButton(mystery, 9648)
            filterButton(romance, 10749)
            filterButton(scienceFiction, 878)
            filterButton(tvMovie, 10770)
            filterButton(thriller, 53)
            filterButton(war, 10752)
            filterButton(western, 37)

            popularityButton(above1000)
            popularityButton2(above500)
            popularityButton3(above100)
            popularityButton4(above0)


        }

        return root
    }

    private fun searchResults(searchList: RecyclerView, searchbar: SearchView, progressBar: ProgressBar,language: String?) {
        val film = SearchService.searchInterface.getMovies(searchbar.query.toString(),language)
        film.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (isAdded) {
                    val data = response.body()
                    if (data != null) {
                        Log.d("Filmopedia", data.toString())
                        adapter = ViewAdapter(requireContext(), data.results)
                        allFilmsList = data.results
                        searchList.adapter = adapter
                        searchList.layoutManager = LinearLayoutManager(requireContext())
                    }
                    progressBar.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                if (isAdded) {
                    Log.d("Filmopedia", "Error", t)
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun filterButton(button: Button, id:Int){
        button.setOnClickListener {
            isSelected = !isSelected

            if (isSelected) {
                val genreFilms = adapter.films.filter { it.genreIds.contains(id) }
                requireActivity().runOnUiThread {
                    adapter.updateFilms(genreFilms)
                    button.setBackgroundResource(R.drawable.filterbuttonselected)
                }
            } else {
                requireActivity().runOnUiThread {
                    adapter.updateFilms(allFilmsList)
                    button.setBackgroundResource(R.drawable.filterbutton)
                }
            }
        }
    }

    private fun popularityButton(button: Button){
        button.setOnClickListener {
            isSelected = !isSelected

            if (isSelected) {
                val actionGenreFilms = adapter.films.filter { it.popularity > 1000}
                requireActivity().runOnUiThread {
                    adapter.updateFilms(actionGenreFilms)
                    button.setBackgroundResource(R.drawable.filterbuttonselected)
                }
            } else {
                requireActivity().runOnUiThread {
                    adapter.updateFilms(allFilmsList)
                    button.setBackgroundResource(R.drawable.filterbutton)
                }
            }
        }
    }
    private fun popularityButton2(button: Button){
        button.setOnClickListener {
            isSelected = !isSelected

            if (isSelected) {
                val actionGenreFilms = adapter.films.filter { it.popularity in 500.0..1000.0 }
                requireActivity().runOnUiThread {
                    adapter.updateFilms(actionGenreFilms)
                    button.setBackgroundResource(R.drawable.filterbuttonselected)
                }
            } else {
                requireActivity().runOnUiThread {
                    adapter.updateFilms(allFilmsList)
                    button.setBackgroundResource(R.drawable.filterbutton)
                }
            }
        }
    }
    private fun popularityButton3(button: Button){
        button.setOnClickListener {
            isSelected = !isSelected

            if (isSelected) {
                val actionGenreFilms = adapter.films.filter { it.popularity in 100.0..500.0 }
                requireActivity().runOnUiThread {
                    adapter.updateFilms(actionGenreFilms)
                    button.setBackgroundResource(R.drawable.filterbuttonselected)
                }
            } else {
                requireActivity().runOnUiThread {
                    adapter.updateFilms(allFilmsList)
                    button.setBackgroundResource(R.drawable.filterbutton)
                }
            }
        }
    }
    private fun popularityButton4(button: Button){
        button.setOnClickListener {
            isSelected = !isSelected

            if (isSelected) {
                val actionGenreFilms = adapter.films.filter { it.popularity < 100.0}
                requireActivity().runOnUiThread {
                    adapter.updateFilms(actionGenreFilms)
                    button.setBackgroundResource(R.drawable.filterbuttonselected)
                }
            } else {
                requireActivity().runOnUiThread {
                    adapter.updateFilms(allFilmsList)
                    button.setBackgroundResource(R.drawable.filterbutton)
                }
            }
        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        searchList?.adapter = null
        _binding = null
    }
}