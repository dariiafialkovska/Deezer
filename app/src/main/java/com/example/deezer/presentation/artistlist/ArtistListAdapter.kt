package com.example.deezer.presentation.artistlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.deezer.R
import com.example.deezer.domain.model.ListArtistModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ArtistListAdapter @Inject constructor(var artistList: ArrayList<ListArtistModel>?,private val artistOnItemClickListener: ArtistOnItemClickListener):
    RecyclerView.Adapter<ArtistListAdapter.ViewHolder>(){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
       val view=
           LayoutInflater.from(parent.context).inflate(R.layout.item_artist,parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        artistList?.let{list->
        holder.binding(list[position],artistOnItemClickListener)}

    }

    override fun getItemCount(): Int {
         return artistList?.size ?: 0
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun binding(model: ListArtistModel, artistOnItemClickListener: ArtistOnItemClickListener){
            with(itemView){
                val artistName= model.name
                val artistImage=model.picture_medium

                findViewById<TextView>(R.id.tv_artist_title).text=artistName

                val imageView= findViewById<ImageView>(R.id.iv_artist_image)

                Picasso.get().load(artistImage).into(imageView)


                findViewById<CardView>(R.id.cv_artist).setOnClickListener{
                    artistOnItemClickListener.onItemClicked(model.id?:0,model.name?:"",model.picture_medium?:"")
                }


            }
        }
    }



    }
