package com.example.deezer.presentation.genreList

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.deezer.R
import com.example.deezer.domain.model.ListGenreModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class GenreListAdapter @Inject constructor(val genreList: ArrayList<ListGenreModel>?,private val genreOnItemClickListener: GenreOnItemClickListener):
    RecyclerView.Adapter<GenreListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):ViewHolder{
        val view=
            LayoutInflater.from(parent.context).inflate(R.layout.item_genre,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        genreList?.let {
            holder.binding(genreList[position],genreOnItemClickListener)
        }
    }

    override fun getItemCount(): Int =genreList?.size?:0


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun binding(model: ListGenreModel, genreOnItemClickListener: GenreOnItemClickListener){
            with(itemView){
                val genreName= model.name
                val genreImage=model.picture_medium

                Log.d("GENRE NAMES",genreImage?:"")

                findViewById<TextView>(R.id.tv_genre_title).text=genreName

                val imageView= findViewById<ImageView>(R.id.iv_genre_image)
                Picasso.get().load(genreImage).into(imageView)


                findViewById<CardView>(R.id.cv_genre).setOnClickListener{
                    genreOnItemClickListener.onItemClicked(model.id?:0,model.name?:"")
                }
            }
        }


    }
}