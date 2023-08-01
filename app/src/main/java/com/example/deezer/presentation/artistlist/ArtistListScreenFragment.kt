package com.example.deezer.presentation.artistlist

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
import com.example.deezer.databinding.FragmentArtistListScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.deezer.presentation.MainActivity


@AndroidEntryPoint
class ArtistListScreenFragment: Fragment() {
    lateinit var binding: FragmentArtistListScreenBinding
    val viewModel: ArtistListViewModel by viewModels()
    private lateinit var adapter: ArtistListAdapter
    val args: ArtistListScreenFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?){
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
        binding= FragmentArtistListScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()
        initDetail()

    }

    fun initDetail(){
        viewModel.mutableArtistData.observe(viewLifecycleOwner, Observer {
            adapter= ArtistListAdapter(it ,object: ArtistOnItemClickListener{
                override fun onItemClicked(artistId: Int,artistName:String,artistImage: String) {
                   val action= ArtistListScreenFragmentDirections.actionFragmentArtistListScreenToFragmentAlbumListScreen(artistId,artistName,artistImage)
                    view?.let{
                        view-> Navigation.findNavController(view).navigate(action)
                    }
                }
            } )
           
            binding.rvArtistList.adapter=adapter

        })
    }

    fun observeLiveData(){
        viewModel.fetchGenreArtists(args.genreId)

        binding.tvCategoryName.text=args.genreTitle
    }


}