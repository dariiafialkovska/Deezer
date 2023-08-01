package com.example.deezer.presentation.likedlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.deezer.R
import com.example.deezer.domain.model.ListTrackModel
import com.example.deezer.presentation.tracklist.TrackOnItemClickListener
import com.squareup.picasso.Picasso
import javax.inject.Inject

class LikedTrackListAdapter @Inject constructor(
    private var trackList: List<ListTrackModel>?,
    private val trackOnItemClickListener: TrackOnItemClickListener,
    private val onDeleteItemClickListener: OnDeleteItemClickListener):
    RecyclerView.Adapter<LikedTrackListAdapter.ViewHolder>()
{
        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            fun binding(model: ListTrackModel,
                        trackOnItemClickListener: TrackOnItemClickListener,
                        onDeleteItemClickListener: OnDeleteItemClickListener,
                        previewUrl: String){
                with(itemView){
                    val trackName=model.title
                    val trackDurationString=model.duration
                    val trackImageMd5=model.md5_image

                    val trackDuration=trackDurationString?.toIntOrNull()?:0

                    val minutes=trackDuration/60
                    val seconds=trackDuration%60

                    val formattedDuration=String.format("%d:%02d",minutes,seconds)

                    findViewById<TextView>(R.id.tv_liked_track_title).text=trackName
                    findViewById<TextView>(R.id.tv_liked_track_duration).text=formattedDuration

                    val imageView=findViewById<ImageView>(R.id.iv_liked_track_image)
                    val baseUrl = "https://e-cdns-images.dzcdn.net/images/cover/"
                    val imageUrl = "$baseUrl$trackImageMd5/250x250-000000-80-0-0.jpg"


                    Picasso.get().load(imageUrl).into(imageView)

                    findViewById<CardView>(R.id.cv_liked_track).setOnClickListener{
                        trackOnItemClickListener.onItemClicked(previewUrl)
                    }

                    findViewById<AppCompatImageButton>(R.id.btn_liked_favorite).setOnClickListener {
                        trackOnItemClickListener.onFavoriteButtonClicked(model)
                    }

                }

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_liked_track,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trackList?.size?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track= trackList?.let { list->
            list[position]
        }
        val previewUrl= track?.preview ?:""
        if (track != null) {
            holder.binding(track,trackOnItemClickListener,onDeleteItemClickListener,previewUrl)
        }

        holder.itemView.findViewById<AppCompatImageButton>(R.id.btn_liked_favorite).setOnClickListener{
            if (track != null) {
                onDeleteItemClickListener.onDeleteClicked(track)
            }
        }
    }




}