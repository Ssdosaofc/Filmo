package com.example.main.Recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.main.MainActivity2
import com.example.main.R
import com.example.main.api.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID

const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
class ViewAdapter(val context: Context, val films: List<Result>): Adapter<ViewAdapter.MovieViewHolder>() {
    var isInMyFavourite:Boolean = false
    private lateinit var firebaseAuth: FirebaseAuth

    class MovieViewHolder(itemView: View): ViewHolder(itemView){
        var moviePoster = itemView.findViewById<ImageView>(R.id.movieimage)
        var movieTitle = itemView.findViewById<TextView>(R.id.movietitle)
        var movieDescriptiom = itemView.findViewById<TextView>(R.id.moviedetails)
        var favButton = itemView.findViewById<ImageButton>(R.id.favButton)
        var movieID = itemView.findViewById<TextView>(R.id.movieID)
        var lang = itemView.findViewById<TextView>(R.id.lang)
        var pop = itemView.findViewById<TextView>(R.id.pop)
        var gen = itemView.findViewById<TextView>(R.id.gen)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val position = holder.adapterPosition
        if (position != RecyclerView.NO_POSITION && position < films.size) {
            val film = films[position]

            holder.movieTitle.text = film.title
            holder.movieDescriptiom.text = film.overview
            holder.movieID.text = film.id.toString()

            holder.pop.text = film.popularity.toString()

            if (film.originalLanguage.isNotEmpty()){
                when (film.originalLanguage){
                    "an"->holder.lang.text = "Aragonese"
                    "ak"->holder.lang.text = "Akan"
                    "cr"->holder.lang.text = "Cree"
                    "az"->holder.lang.text = "Azerbaijani"
                    "cs"->holder.lang.text = "Czech"
                    "aa"->holder.lang.text = "Afar"
                    "br"->holder.lang.text = "Breton"
                    "af"->holder.lang.text = "Afrikaans"
                    "bo"->holder.lang.text = "Tibetan"
                    "ce"->holder.lang.text = "Chechen"
                    "kw"->holder.lang.text = "Cornish"
                    "fo"->holder.lang.text = "Faroese"
                    "la"->holder.lang.text = "Latin"
                    "ng"->holder.lang.text = "Ndonga"
                    "sc"->holder.lang.text = "Sardinian"
                    "ti"->holder.lang.text = "Tigrinya"
                    "tn"->holder.lang.text = "Tswana"
                    "tr"->holder.lang.text = "Turkish"
                    "pa"->holder.lang.text = "Punjabi"
                    "et"->holder.lang.text = "Estonian"
                    "fr"->holder.lang.text = "French"
                    "ha"->holder.lang.text = "Hausa"
                    "is"->holder.lang.text = "Icelandic"
                    "li"->holder.lang.text = "Limburgish"
                    "ln"->holder.lang.text = "Lingala"
                    "ss"->holder.lang.text = "Swati"
                    "ab"->holder.lang.text = "Abkhazian"
                    "sh"->holder.lang.text = "Serbo-Croatian"
                    "eu"->holder.lang.text = "Basque"
                    "fy"->holder.lang.text = "Frisian"
                    "ja"->holder.lang.text = "Japanese"
                    "oj"->holder.lang.text = "Ojibwa"
                    "or"->holder.lang.text = "Oriya"
                    "pi"->holder.lang.text = "Pali"
                    "su"->holder.lang.text = "Sundanese"
                    "th"->holder.lang.text = "Thai"
                    "ig"->holder.lang.text = "Igbo"
                    "id"->holder.lang.text = "Indonesian"
                    "kk"->holder.lang.text = "Kazakh"
                    "ki"->holder.lang.text = "Kikuyu"
                    "ug"->holder.lang.text = "Uighur"
                    "ve"->holder.lang.text = "Venda"
                    "rw"->holder.lang.text = "Kinyarwanda"
                    "mi"->holder.lang.text = "Maori"
                    "nv"->holder.lang.text = "Navajo"
                    "hi"->holder.lang.text = "Hindi"
                    "pt"->holder.lang.text = "Portuguese"
                    "sg"->holder.lang.text = "Sango"
                    "sk"->holder.lang.text = "Slovak"
                    "sr"->holder.lang.text = "Serbian"
                    "ty"->holder.lang.text = "Tahitian"
                    "xh"->holder.lang.text = "Xhosa"
                    "ar"->holder.lang.text = "Arabic"
                    "co"->holder.lang.text = "Corsican"
                    "bi"->holder.lang.text = "Bislama"
                    "eo"->holder.lang.text = "Esperanto"
                    "hz"->holder.lang.text = "Herero"
                    "fi"->holder.lang.text = "Finnish"
                    "iu"->holder.lang.text = "Inuktitut"
                    "lv"->holder.lang.text = "Latvian"
                    "it"->holder.lang.text = "Italian"
                    "nl"->holder.lang.text = "Dutch"
                    "kn"->holder.lang.text = "Kannada"
                    "sa"->holder.lang.text = "Sanskrit"
                    "sq"->holder.lang.text = "Albanian"
                    "tl"->holder.lang.text = "Tagalog"
                    "lb"->holder.lang.text = "Letzeburgesch"
                    "ts"->holder.lang.text = "Tsonga"
                    "ml"->holder.lang.text = "Malayalam"
                    "vo"->holder.lang.text = "Volapük"
                    "zu"->holder.lang.text = "Zulu"
                    "os"->holder.lang.text = "Ossetian; Ossetic"
                    "sm"->holder.lang.text = "Samoan"
                    "za"->holder.lang.text = "Zhuang"
                    "bn"->holder.lang.text = "Bengali"
                    "cu"->holder.lang.text = "Slavic"
                    "ga"->holder.lang.text = "Irish"
                    "gv"->holder.lang.text = "Manx"
                    "hu"->holder.lang.text = "Hungarian"
                    "jv"->holder.lang.text = "Javanese"
                    "kr"->holder.lang.text = "Kanuri"
                    "km"->holder.lang.text = "Khmer"
                    "ky"->holder.lang.text = "Kirghiz"
                    "na"->holder.lang.text = "Nauru"
                    "nr"->holder.lang.text = "Ndebele"
                    "oc"->holder.lang.text = "Occitan"
                    "ro"->holder.lang.text = "Romanian"
                    "ru"->holder.lang.text = "Russian"
                    "hy"->holder.lang.text = "Armenian"
                    "ch"->holder.lang.text = "Chamorro"
                    "xx"->holder.lang.text = "No Language"
                    "ba"->holder.lang.text = "Bashkir"
                    "gl"->holder.lang.text = "Galician"
                    "io"->holder.lang.text = "Ido"
                    "lu"->holder.lang.text = "Luba-Katanga"
                    "mh"->holder.lang.text = "Marshall"
                    "mg"->holder.lang.text = "Malagasy"
                    "mo"->holder.lang.text = "Moldavian"
                    "mn"->holder.lang.text = "Mongolian"
                    "nd"->holder.lang.text = "Ndebele"
                    "no"->holder.lang.text = "Norwegian"
                    "pl"->holder.lang.text = "Polish"
                    "sw"->holder.lang.text = "Swahili"
                    "tg"->holder.lang.text = "Tajik"
                    "to"->holder.lang.text = "Tonga"
                    "wa"->holder.lang.text = "Walloon"
                    "yi"->holder.lang.text = "Yiddish"
                    "en"->holder.lang.text = "English"
                    "as"->holder.lang.text = "Assamese"
                    "gd"->holder.lang.text = "Gaelic"
                    "kl"->holder.lang.text = "Kalaallisut"
                    "my"->holder.lang.text = "Burmese"
                    "qu"->holder.lang.text = "Quechua"
                    "sn"->holder.lang.text = "Shona"
                    "uk"->holder.lang.text = "Ukrainian"
                    "fa"->holder.lang.text = "Persian"
                    "ka"->holder.lang.text = "Georgian"
                    "gu"->holder.lang.text = "Gujarati"
                    "av"->holder.lang.text = "Avaric"
                    "ae"->holder.lang.text = "Avestan"
                    "fo"->holder.lang.text = "Faroese"
                    "gd"->holder.lang.text = "Gaelic"
                    "gl"->holder.lang.text = "Galician"
                    "lg"->holder.lang.text = "Ganda"
                    "ga"->holder.lang.text = "Irish"
                    "ja"->holder.lang.text = "Japanese"
                    "ki"->holder.lang.text = "Kikuyu"
                    "lv"->holder.lang.text = "Latvian"
                    "li"->holder.lang.text = "Limburgish"
                    "ga"->holder.lang.text = "Manx"
                    "mh"->holder.lang.text = "Marshallese"
                    "os"->holder.lang.text = "Ossetian"
                    "pi"->holder.lang.text = "Pali"
                    "fa"->holder.lang.text = "Persian"
                    "pl"->holder.lang.text = "Polish"
                    "gd"->holder.lang.text = "Scots Gaelic"
                    "se"->holder.lang.text = "Northern Sami"
                    "sn"->holder.lang.text = "Shona"
                    "sr"->holder.lang.text = "Serbian"
                    "za"->holder.lang.text = "Zhuang"

                }
            }

            if (film.genreIds.isNotEmpty()) {
                when (film.genreIds[0]){
                    28 -> holder.gen.text = "Action"
                    12 -> holder.gen.text = "Adventure"
                    16 -> holder.gen.text = "Animation"
                    35 -> holder.gen.text = "Comedy"
                    80 -> holder.gen.text = "Crime"
                    99 -> holder.gen.text = "Documentary"
                    18 -> holder.gen.text = "Drama"
                    10751 -> holder.gen.text = "Family"
                    14 -> holder.gen.text = "Fantasy"
                    36 -> holder.gen.text = "History"
                    27 -> holder.gen.text = "Horror"
                    10402 -> holder.gen.text = "Music"
                    9648 -> holder.gen.text = "Mystery"
                    10749 -> holder.gen.text = "Romance"
                    878 -> holder.gen.text = "Science Fiction"
                    10770 -> holder.gen.text = "TV Movie"
                    53 -> holder.gen.text = "Thriller"
                    10752 -> holder.gen.text = "War"
                    37 -> holder.gen.text = "Western"

                }
            }

            Glide.with(context).load(POSTER_BASE_URL + film.posterPath).into(holder.moviePoster)

            firebaseAuth = FirebaseAuth.getInstance()

            if (firebaseAuth.currentUser != null) {
                checkIfFavourite(holder, film.id.toString(), position)
            }
        }
    }

    private fun checkIfFavourite(holder: MovieViewHolder, movieID: String, position: Int) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        reference.child(firebaseAuth.uid.toString()).child("Favourites")
            .child(movieID)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    isInMyFavourite = dataSnapshot.exists()
                    if (position < films.size) {
                        films[position].isFavorite = isInMyFavourite
                        updateFavoriteButton(holder, position)
                        notifyItemChanged(position)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error if needed
                }
            })

        holder.favButton.setOnClickListener {
            toggleFavoriteStatus(holder, position)
        }
    }

    private fun updateFavoriteButton(holder: MovieViewHolder, position: Int) {
        if (position < films.size) {
            val movie = films[position]

            if (movie.isFavorite) {
                holder.favButton.setImageResource(R.drawable.baseline_favorite_24_white)
            } else {
                holder.favButton.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }
    }

    private fun toggleFavoriteStatus(holder: MovieViewHolder, position: Int) {
        if (position < films.size) {
            val movie = films[position]

            if (movie.isFavorite) {
                removeFromFavourite(context, movie.id.toString())
            } else {
                addToFavourite(context, movie.id.toString(), movie.title, movie.posterPath, movie.overview,movie.originalLanguage,movie.popularity.toString(), movie.genreIds[0])
            }
        }
    }




}

fun removeFromFavourite(context: Context, movieID: String) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    ref.child(firebaseAuth.uid.toString()).child("Favourites")
        .child(movieID)
        .removeValue()
        .addOnSuccessListener {
            Toast.makeText(context, "Removed from favourites", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Could not Remove from Favourites", Toast.LENGTH_SHORT)
                .show()
        }
}

fun addToFavourite(context: Context, movieID: String,title: String, poster: String, overview: String,lang: String,pop: String, gen:Int) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val hashMap: HashMap<String, Any> = HashMap()
    hashMap["movieID"] = movieID
    hashMap["title"] = title
    hashMap["poster"] = poster
    hashMap["overview"] = overview
    hashMap["language"] = lang
    hashMap["popularity"] = pop
    hashMap["genre"] = gen

    val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    ref.child(firebaseAuth.uid.toString()).child("Favourites")
        .child(movieID)
        .setValue(hashMap)
        .addOnSuccessListener {
            Toast.makeText(context, "Added to favourites", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Could not Add to Favourites", Toast.LENGTH_SHORT).show()
        }
}
