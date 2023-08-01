package com.example.deezer.presentation.albumlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.deezer.R
import com.example.deezer.domain.model.ListAlbumModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class AlbumListAdapter @Inject constructor(var albumList: ArrayList<ListAlbumModel>?,private val albumOnItemClickListener: AlbumOnItemClickListener):
    RecyclerView.Adapter<AlbumListAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view=
            LayoutInflater.from(parent.context).inflate(R.layout.item_album,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        albumList?.let { list->
        holder.binding(list[position],albumOnItemClickListener)}
    }


    override fun getItemCount(): Int {
        return albumList?.size ?: 0

    }



    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun binding(model: ListAlbumModel,albumOnItemClickListener: AlbumOnItemClickListener){
            with(itemView){
                val albumName= model.title
                val albumReleaseDate=model.release_date
                val albumImage=model.cover_medium

                findViewById<TextView>(R.id.tv_album_title).text=albumName
                findViewById<TextView>(R.id.tv_album_date).text=albumReleaseDate

                val imageView= findViewById<ImageView>(R.id.iv_album_image)
                Picasso.get().load(albumImage).into(imageView)

                findViewById<CardView>(R.id.cv_album)?.setOnClickListener{
                    albumOnItemClickListener.onItemClicked(model.id?:0,model.title?:"")
                }
            }
        }
    }

}