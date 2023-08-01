package com.example.deezer.presentation.genreList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import com.example.deezer.databinding.FragmentGenreListScreenBinding

@AndroidEntryPoint
class GenreListScreenFragment : Fragment(){
    private val viewModel: GenreListViewModel by viewModels()
    private lateinit var adapter: GenreListAdapter
    lateinit var binding: FragmentGenreListScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentGenreListScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeGenreList()
    }

    fun observeGenreList(){
        viewModel.fetchAllGenres()

        viewModel.mutableGenreData.observe(viewLifecycleOwner, Observer{
            adapter= GenreListAdapter(it!!, object :GenreOnItemClickListener{
                override fun onItemClicked(genreId: Int,genreTitle: String) {
                    val action=GenreListScreenFragmentDirections.actionFragmentGenreListScreenToFragmentArtistListScreen(genreId,genreTitle)
                    view?.let {
                        view-> Navigation.findNavController(view).navigate(action)
                    }
                }
            })
            binding.rvGenreList.adapter=adapter
        })
    }
}