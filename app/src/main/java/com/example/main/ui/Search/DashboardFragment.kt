package com.example.main.ui.Search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.main.Recycler.ViewAdapter
import com.example.main.api.Data
import com.example.main.api.SearchService
import com.example.main.databinding.FragmentDashboardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class DashboardFragment : Fragment() {
    lateinit var adapter: ViewAdapter

    private var _binding: FragmentDashboardBinding? = null

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
        val searchList: RecyclerView = binding.searchList
        val searchbar: SearchView = binding.searchBar
        val filter:Button = binding.filter
        val progressBar = binding.progress

        dashboardViewModel.text.observe(viewLifecycleOwner) {
            searchMovies(searchList, searchbar, progressBar)

            filter.setOnClickListener(object : OnClickListener {
                override fun onClick(v: View?) {
                    val dialog:FilterDialog = FilterDialog()
                    dialog.show(requireActivity().supportFragmentManager, "Filter Dialog")
                }

            })

        }

        return root
    }

    private fun searchMovies(searchList: RecyclerView, searchbar: SearchView, progressBar: ProgressBar) {
        searchbar.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                progressBar.visibility = View.VISIBLE
                val film = SearchService.searchInterface.getMovies(query, false, "en-US", 1)
                film.enqueue(object : Callback<Data> {
                    override fun onResponse(call: Call<Data>, response: Response<Data>) {
                        val data = response.body()
                        if (data != null) {
                            Log.d("Filmopedia", data.toString())
                            adapter = ViewAdapter(requireContext(), data.results)
                            searchList.adapter = adapter
                            searchList.layoutManager = LinearLayoutManager(requireContext())
                        }

                    }

                    override fun onFailure(call: Call<Data>, t: Throwable) {
                        Log.d("Filmopedia", "Error", t)
                    }
                })
                progressBar.visibility = View.GONE
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                val text = newText.toLowerCase(Locale.getDefault())
                if (text.isNotEmpty()){
                    val film = SearchService.searchInterface.getMovies(text, false, "en-US", 1)
                    film.enqueue(object : Callback<Data> {
                        override fun onResponse(call: Call<Data>, response: Response<Data>) {
                            val data = response.body()
                            if (data != null) {

                                Log.d("Filmopedia", data.toString())
                                adapter = ViewAdapter(requireContext(), data.results)
                                searchList.adapter = adapter
                                searchList.layoutManager = LinearLayoutManager(requireContext())

                            }

                        }

                        override fun onFailure(call: Call<Data>, t: Throwable) {
                            Log.d("Filmopedia", "Error", t)
                        }
                    })
                }

                return false
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
