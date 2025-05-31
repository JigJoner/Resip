package com.example.resip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resip.data.repository.IngredientRepository
import com.example.resip.model.Ingredient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(1)
}

class HomePageViewModel : ViewModel(){

}

class RecipesViewModel : ViewModel(){

}

class IngredientsViewModel(private val repo: IngredientRepository) : ViewModel(){
    // private stateflow
    private var _uiState = MutableStateFlow(IngredientsUiState())
    // public stateflow: read only
    val uiState: StateFlow<IngredientsUiState> = _uiState.asStateFlow()

//    fun getIngredients(): List<Ingredient>{
//        return _uiState.value.ingredients
//    }
    init {
        viewModelScope.launch{
            repo.getAllIngredientsStream().collect(){
                it ->
                _uiState.value = _uiState.value.copy(ingredients = it.filter {
                    it.isOwned
                })
            }
        }
    }

}

class BrowseViewModel : ViewModel(){

}