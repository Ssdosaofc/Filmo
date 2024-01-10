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

    private var searchList: RecyclerView? = null

    private var allFilmsList: List<Result> = emptyList()

    var isActionSelected: Boolean = false
    var isAdventureSelected: Boolean = false
    var isAnimationSelected: Boolean = false
    var isComedySelected: Boolean = false
    var isCrimeSelected: Boolean = false
    var isDocumentarySelected: Boolean = false
    var isDramaSelected: Boolean = false
    var isFamilySelected: Boolean = false
    var isFantasySelected: Boolean = false
    var isHistorySelected: Boolean = false
    var isHorrorSelected: Boolean = false
    var isMusicSelected: Boolean = false
    var isMysterySelected: Boolean = false
    var isRomanceSelected: Boolean = false
    var isScienceFictionSelected: Boolean = false
    var isTVMovieSelected: Boolean = false
    var isThrillerSelected: Boolean = false
    var isWarSelected: Boolean = false
    var isWesternSelected: Boolean = false

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
        val action: Button = binding.Action.apply { tag = "Action" }
        val adventure: Button = binding.Adventure.apply { tag = "Adventure" }
        val animation: Button = binding.Animation.apply { tag = "Animation" }
        val comedy: Button = binding.Comedy.apply { tag = "Comedy" }
        val crime: Button = binding.Crime.apply { tag = "Crime" }
        val documentary: Button = binding.Documentary.apply { tag = "Documentary" }
        val drama: Button = binding.Drama.apply { tag = "Drama" }
        val family: Button = binding.Family.apply { tag = "Family" }
        val fantasy: Button = binding.Fantasy.apply { tag = "Fantasy" }
        val history: Button = binding.History.apply { tag = "History" }
        val horror: Button = binding.Horror.apply { tag = "Horror" }
        val music: Button = binding.Music.apply { tag = "Music" }
        val mystery: Button = binding.Mystery.apply { tag = "Mystery" }
        val romance: Button = binding.Romance.apply { tag = "Romance" }
        val scienceFiction: Button = binding.ScienceFiction.apply { tag = "ScienceFiction" }
        val tvMovie: Button = binding.TVMovie.apply { tag = "TVMovie" }
        val thriller: Button = binding.Thriller.apply { tag = "Thriller" }
        val war: Button = binding.War.apply { tag = "War" }
        val western: Button = binding.Western.apply { tag = "Western" }
        val actionText: String = binding.Action.text.toString()

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

            filterButton(action, 28, isActionSelected)
            filterButton(adventure, 12, isAdventureSelected)
            filterButton(animation, 16, isAnimationSelected)
            filterButton(comedy, 35, isComedySelected)
            filterButton(crime, 80, isCrimeSelected)
            filterButton(documentary, 99, isDocumentarySelected)
            filterButton(drama, 18, isDramaSelected)
            filterButton(family, 10751, isFamilySelected)
            filterButton(fantasy, 14, isFantasySelected)
            filterButton(history, 36, isHistorySelected)
            filterButton(horror, 27, isHorrorSelected)
            filterButton(music, 10402, isMusicSelected)
            filterButton(mystery, 9648, isMysterySelected)
            filterButton(romance, 10749, isRomanceSelected)
            filterButton(scienceFiction, 878, isScienceFictionSelected)
            filterButton(tvMovie, 10770, isTVMovieSelected)
            filterButton(thriller, 53, isThrillerSelected)
            filterButton(war, 10752, isWarSelected)
            filterButton(western, 37, isWesternSelected)

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

    private fun filterButton(button: Button, id: Int, isSelected: Boolean) {
        button.setOnClickListener {
            val tag = button.tag as String
            when (tag) {
                "Action" -> isActionSelected = !isActionSelected
                "Adventure" -> isAdventureSelected = !isAdventureSelected
                "Animation" -> isAnimationSelected = !isAnimationSelected
                "Comedy" -> isComedySelected = !isComedySelected
                "Crime" -> isCrimeSelected = !isCrimeSelected
                "Documentary" -> isDocumentarySelected = !isDocumentarySelected
                "Drama" -> isDramaSelected = !isDramaSelected
                "Family" -> isFamilySelected = !isFamilySelected
                "Fantasy" -> isFantasySelected = !isFantasySelected
                "History" -> isHistorySelected = !isHistorySelected
                "Horror" -> isHorrorSelected = !isHorrorSelected
                "Music" -> isMusicSelected = !isMusicSelected
                "Mystery" -> isMysterySelected = !isMysterySelected
                "Romance" -> isRomanceSelected = !isRomanceSelected
                "ScienceFiction" -> isScienceFictionSelected = !isScienceFictionSelected
                "TVMovie" -> isTVMovieSelected = !isTVMovieSelected
                "Thriller" -> isThrillerSelected = !isThrillerSelected
                "War" -> isWarSelected = !isWarSelected
                "Western" -> isWesternSelected = !isWesternSelected
            }


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

    override fun onDestroyView() {
        super.onDestroyView()
        searchList?.adapter = null
        _binding = null
    }
}