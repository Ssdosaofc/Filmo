package com.example.main.ui.Search

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.view.menu.MenuView.ItemView
import com.example.main.R

class FilterDialog: AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater:LayoutInflater = requireActivity().layoutInflater
        val view:View = inflater.inflate(R.layout.filter, null)

        builder.setView(view)
            .setTitle("Filter")
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->

            })
            .setPositiveButton("Apply", DialogInterface.OnClickListener { dialog, which ->  })

        val genre:TextView = view.findViewById(R.id.genre)
        val rating:TextView = view.findViewById(R.id.rating)
        val language:TextView = view.findViewById(R.id.language)
        val genreList:Spinner = view.findViewById(R.id.genreList)
        val ratingList:Spinner = view.findViewById(R.id.ratingList)
        val languageList:Spinner = view.findViewById(R.id.langList)

        return builder.create()
    }
}