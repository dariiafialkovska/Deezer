package com.example.deezer.presentation.likedlist

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
import com.example.deezer.data.model.response.TrackModel
import com.example.deezer.databinding.FragmentLikedTrackListScreenBinding
import com.example.deezer.domain.model.ListTrackModel
import com.example.deezer.presentation.MainActivity
import com.example.deezer.presentation.tracklist.TrackListViewModel
import com.example.deezer.presentation.tracklist.TrackOnItemClickListener
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikedTrackListScreenFragment : Fragment() {
    lateinit var binding: FragmentLikedTrackListScreenBinding
    val viewModel: TrackListViewModel by viewModels()
    private lateinit var adapter: LikedTrackListAdapter
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
        binding= FragmentLikedTrackListScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        initDetail()
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

    private fun initDetail() {
        viewModel.getAllLikedTracks()
    }


    private fun toggleTrackLikedStatus(track: ListTrackModel){
        viewModel.isLikedTrack(track.id?:0)
        viewModel.mutableStateTrackData.observe(viewLifecycleOwner, Observer {
            if(it){
                deleteTrack(getTrackModel(track))
            }
            else{
                Log.d("LikedListScreenFragment","Tack ${track.title} is not added ")
            }
            track.isLiked=!track.isLiked
            viewModel.getAllLikedTracks()
        })
    }

    private fun deleteTrack(track: TrackModel){
        viewModel.deleteTrack(track)
        Log.d("TrackListScreenFragment", "Track ${track.title} added to liked tracks.")

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

    private fun observeLiveData() {
        viewModel.mutableListData.observe(viewLifecycleOwner, Observer {

            adapter= LikedTrackListAdapter(it,object: TrackOnItemClickListener{
                override fun onItemClicked(previewUrl: String) {
                    val position=it?.indexOfFirst { it.preview==previewUrl }
                    if(position==currentPlayingPosition){
                        stopMusic()
                    }else{
                        playMusic(previewUrl,position)
                    }
                }

                override fun onFavoriteButtonClicked(track: ListTrackModel) {
                    toggleTrackLikedStatus(track)
                }
            },object :OnDeleteItemClickListener{
                override fun onDeleteClicked(track: ListTrackModel) {
                    toggleTrackLikedStatus(track)
                }
            }
            )
            binding.rvLikedTrackList.adapter=adapter
        })
    }

    private fun playMusic(previewUrl: String, position: Int?) {
        stopMusic()
        val mediaSource = ProgressiveMediaSource.Factory(DefaultDataSourceFactory(requireContext()))
            .createMediaSource(Uri.parse(previewUrl))

        currentPlayer = SimpleExoPlayer.Builder(requireContext()).build()
        currentPlayer?.prepare(mediaSource)
        currentPlayer?.play()

        currentPlayingPosition = position?:0

        Handler(Looper.getMainLooper()).postDelayed({
            stopMusic()
        }, 30000)

    }
}