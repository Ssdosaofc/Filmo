package com.example.main.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.main.Recycler.RetrieveAdapter
import com.example.main.Recycler.ViewAdapter
import com.example.main.api.Retrieve
import com.example.main.databinding.FragmentNotificationsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.firestore.auth.User
import java.lang.reflect.Member

class NotificationsFragment : Fragment() {

    private lateinit var ref: DatabaseReference
    lateinit var adapter: RetrieveAdapter
    var list = arrayListOf<Retrieve>()

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val favList: RecyclerView = binding.favList
        ref = FirebaseDatabase.getInstance().getReference("Users")


        notificationsViewModel.text.observe(viewLifecycleOwner) {
            favList.setHasFixedSize(true)
            favList.layoutManager = LinearLayoutManager(requireContext())
            adapter = RetrieveAdapter(requireContext(), list)
            favList.adapter = adapter

            val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
            val userId = firebaseAuth.currentUser?.uid

            userId?.let { fetchFavoriteMovies(it) }
        }
        return root
    }

    private fun fetchFavoriteMovies(userID: String) {
        var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val favoritesRef = ref.child(firebaseAuth.uid.toString()).child("Favourites")

        favoritesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                synchronized(list) {
                    list.clear()

                    for (dataSnapshot: DataSnapshot in snapshot.children) {
                        val retrieve: Retrieve? = dataSnapshot.getValue(Retrieve::class.java)
                        retrieve?.let {
                            list.add(it)
                        }
                    }
                }

                if (adapter != null) {
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if needed
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}