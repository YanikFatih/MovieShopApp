package com.example.movieshopapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieshopapp.data.entity.Favorites
import com.example.movieshopapp.data.repo.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesPageViewModel @Inject constructor(var favoritesRepository: FavoritesRepository) : ViewModel() {
    var favoritesList = MutableLiveData<List<Favorites>>()

    init {
        getFavorites()
    }

    fun getFavorites() {
        CoroutineScope(Dispatchers.Main).launch {
            favoritesList.value = favoritesRepository.getFavorites()
        }
    }

    fun deleteFromFavorites(id:Int) {
        CoroutineScope(Dispatchers.Main).launch {
            favoritesRepository.deleteFromFavorites(id)
            getFavorites()
        }
    }

    fun search(searchString: String) {
        CoroutineScope(Dispatchers.Main).launch {
            favoritesList.value = favoritesRepository.search(searchString)
        }
    }
}