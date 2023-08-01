package com.example.deezer.presentation.albumlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.deezer.databinding.FragmentAlbumListScreenBinding
import com.example.deezer.presentation.MainActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AlbumListScreenFragment : Fragment(){
    lateinit var binding: FragmentAlbumListScreenBinding
    val viewModel: AlbumListViewModel by viewModels()
    private lateinit var adapter : AlbumListAdapter
    val args: AlbumListScreenFragmentArgs by navArgs()

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
        binding= FragmentAlbumListScreenBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        initDetail()
    }

    fun initDetail(){
        viewModel.mutableAlbumData.observe(viewLifecycleOwner, Observer {
            adapter= AlbumListAdapter(it,object : AlbumOnItemClickListener{
                override fun onItemClicked(albumId: Long,albumName:String) {
                    val action= AlbumListScreenFragmentDirections.actionFragmentAlbumListScreenToFragmentTrackListScreen(albumId, albumName)
                    view?.let{
                        view->Navigation.findNavController(view).navigate(action)
                    }

                }
            })

            binding.rvAlbumList.adapter=adapter
        })
    }

    fun observeLiveData(){
        viewModel.fetchAllAlbums(args.artistId)
        binding.tvArtistName.text=args.artistName

        val imageView= args.artistImage
        Picasso.get().load(imageView).into(binding.ivArtistImage)
    }
}