package com.example.main.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import com.example.main.api.MovieService
import com.example.main.api.SearchService
import com.example.main.databinding.FragmentDashboardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    lateinit var adapter: ViewAdapter

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        dashboardViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }
/*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        val searchList: RecyclerView = binding.searchList

        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!=null){
                    val film = SearchService.searchInterface.getMovies(query,false,"en-US", 1)
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
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }


 */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}