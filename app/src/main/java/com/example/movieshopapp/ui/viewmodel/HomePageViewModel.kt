package com.example.movieshopapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieshopapp.data.entity.Movies
import com.example.movieshopapp.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(var moviesRepository: MoviesRepository) : ViewModel() {
    var moviesList = MutableLiveData<List<Movies>>() //to send movies list to user interface

    init {
        loadAllMovies()
    }

    fun loadAllMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            moviesList.value = moviesRepository.loadAllMovies() //can be accessed from user interface
        }
    }
}