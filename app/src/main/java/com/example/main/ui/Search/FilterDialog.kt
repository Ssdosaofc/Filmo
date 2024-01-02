package com.example.main.ui.Search

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.PopupMenu.OnMenuItemClickListener
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.view.menu.MenuView.ItemView
import com.example.main.R

/*
interface FilterListener {
    fun onFilterApplied(language: String?)
}

 */

class FilterDialog(//private val filterListener: FilterListener
): AppCompatDialogFragment(), OnItemSelectedListener  {

    //private var lang: String? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater:LayoutInflater = requireActivity().layoutInflater
        val view:View = inflater.inflate(R.layout.filter, null)

        builder.setView(view)
            .setTitle("Filter")
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->

            })
            .setPositiveButton("Apply", DialogInterface.OnClickListener { dialog, which ->
                //filterListener.onFilterApplied(lang)
            })

        val genre:TextView = view.findViewById(R.id.genre)
        val rating:TextView = view.findViewById(R.id.rating)
        val language:TextView = view.findViewById(R.id.language)
        val genreList:Spinner = view.findViewById(R.id.genreList)
        val ratingList:Spinner = view.findViewById(R.id.ratingList)
        val languageList:Spinner = view.findViewById(R.id.langList)

        val adapter:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.Genres, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genreList.adapter = adapter
        genreList.onItemSelectedListener = this

        val adapter1:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.Ratings, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ratingList.adapter = adapter1
        ratingList.onItemSelectedListener = this

        val adapter2:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.LangCodes, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageList.adapter = adapter2
        languageList.onItemSelectedListener = this

        return builder.create()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        //lang = parent.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

}