package com.example.deezer.presentation.tracklist

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
import com.squareup.picasso.Picasso
import javax.inject.Inject



class TrackListAdapter @Inject constructor(
    var trackList: ArrayList<ListTrackModel>?,
    private val trackOnItemClickListener: TrackOnItemClickListener,
    ):
    RecyclerView.Adapter<TrackListAdapter.ViewHolder>()
{
    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        fun binding(model: ListTrackModel,
                    trackOnItemClickListener: TrackOnItemClickListener,
                    previewUrl: String,
        ){
            with(itemView){
                val trackName=model.title
                val trackDurationString =model.duration
                val trackImageMd5=model.md5_image

                val trackDuration= trackDurationString?.toIntOrNull() ?:0

                val minutes=trackDuration/60
                val seconds=trackDuration%60

                val formattedDuration=String.format("%d:%02d",minutes,seconds)

                findViewById<TextView>(R.id.tv_track_title).text=trackName
                findViewById<TextView>(R.id.tv_track_duration).text=formattedDuration

                val imageView=findViewById<ImageView>(R.id.iv_track_image)
                val baseUrl = "https://e-cdns-images.dzcdn.net/images/cover/"
                val imageUrl = "$baseUrl$trackImageMd5/250x250-000000-80-0-0.jpg"

                val favoriteButton=findViewById<AppCompatImageButton>(R.id.btn_favorite)

                favoriteButton.setImageResource(
                    if(model.isLiked)R.drawable.ic_favorite_full
                    else R.drawable.ic_favorite
                )

                favoriteButton.setOnClickListener {
                    trackOnItemClickListener.onFavoriteButtonClicked(model)
                }

                Picasso.get().load(imageUrl).into(imageView)

                findViewById<CardView>(R.id.cv_track).setOnClickListener{
                    trackOnItemClickListener.onItemClicked(previewUrl)
                }

                findViewById<AppCompatImageButton>(R.id.btn_favorite).setOnClickListener{
                    trackOnItemClickListener.onFavoriteButtonClicked(model)
                }



            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_track,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trackList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track= trackList?.get(position)
        val previewUrl= track?.preview ?:""
        if (track != null) {
            holder.binding(track, trackOnItemClickListener,previewUrl)
        }
    }


}