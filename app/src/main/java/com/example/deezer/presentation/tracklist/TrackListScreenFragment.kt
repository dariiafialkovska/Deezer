package com.example.deezer.presentation.tracklist

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.deezer.data.model.response.TrackModel
import com.example.deezer.databinding.FragmentTrackListScreenBinding
import com.example.deezer.domain.model.ListTrackModel
import com.example.deezer.presentation.MainActivity
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrackListScreenFragment: Fragment() {
    lateinit var binding: FragmentTrackListScreenBinding
    val viewModel: TrackListViewModel by viewModels()
    private lateinit var adapter: TrackListAdapter
    val args: TrackListScreenFragmentArgs by navArgs()
    private var currentPlayer: SimpleExoPlayer?=null
    private var currentPlayingPosition: Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentTrackListScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllTracks()
        initDetail()
    }


    private fun getAllTracks() {
        viewModel.fetchAllTracks(args.albumId)
        binding.tvAlbumName.text=args.albumName
    }

    private fun getAllLikedTrackIds(trackList: ArrayList<ListTrackModel>){
        viewModel.getAllLikedTracksIds()
        viewModel.mutableLongListData.observe(viewLifecycleOwner,
        Observer {
            trackList.forEach { track: ListTrackModel->
                track.isLiked=track.id in it
            }
            adapter.notifyDataSetChanged()
        })
    }

    fun initDetail() {
        viewModel.mutableTrackData.observe(viewLifecycleOwner, Observer {
            trackList->lifecycleScope.launch{

            if (trackList != null) {
                getAllLikedTrackIds(trackList)
            }

            adapter= TrackListAdapter(trackList,object :TrackOnItemClickListener{
                override fun onItemClicked(previewUrl: String) {
                    val position= trackList?.indexOfFirst { it.preview==previewUrl }
                    if(position==currentPlayingPosition){
                        stopMusic()
                    }else{
                        if (position != null) {
                            playMusic(previewUrl,position)
                        }
                    }
                }
                override fun onFavoriteButtonClicked(track: ListTrackModel) {
                    toggleTrackLikedStatus(track)
                }
            })
            binding.rvTrackList.adapter=adapter

        }

        })
    }

    private fun playMusic(previewUrl: String, position: Int) {
        stopMusic()
        val mediaSource = ProgressiveMediaSource.Factory(DefaultDataSourceFactory(requireContext()))
            .createMediaSource(Uri.parse(previewUrl))

        currentPlayer = SimpleExoPlayer.Builder(requireContext()).build()
        currentPlayer?.prepare(mediaSource)
        currentPlayer?.play()

        currentPlayingPosition = position

        Handler(Looper.getMainLooper()).postDelayed({
            stopMusic()
        }, 30000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopMusic()
    }

    private fun stopMusic() {
        currentPlayer?.stop()
        currentPlayer?.release()
        currentPlayer=null
        currentPlayingPosition=-1
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun toggleTrackLikedStatus(track: ListTrackModel) {
        viewModel.isLikedTrack(track.id!!)
        viewModel.mutableStateTrackData.observe(viewLifecycleOwner,
        Observer {
            if(it){
                deleteTrack(getTrackModel(track))
            }else{
                insertTrack(getTrackModel(track))

            }
        })
        track.isLiked=!track.isLiked
        adapter.notifyDataSetChanged()
    }

    private fun deleteTrack(trackModel: TrackModel) {
        viewModel.deleteTrack(trackModel)
        Log.d("TrackListScreenFragment", "Track ${trackModel.title} deleted from liked tracks.")

    }

    private fun insertTrack(trackModel: TrackModel) {
        viewModel.insertTrack(trackModel)
        Log.d("TrackListScreenFragment", "Track ${trackModel.title} added to liked tracks.")
    }

    private fun getTrackModel(track: ListTrackModel): TrackModel {
        return TrackModel(
            track.id,
            track.title,
            track.duration,
            track.preview,
            track.md5_image,
            is_liked = true
        )

    }



}