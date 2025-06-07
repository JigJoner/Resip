package com.example.resip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resip.data.database.preIngredient.PreIngredient
import com.example.resip.data.repository.IngredientRepository
import com.example.resip.model.Ingredient
import com.example.resip.model.MeasurementUnit
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.random.RandomGenerator
import kotlin.random.Random

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(1)
}

class HomePageViewModel : ViewModel() {

}

class RecipesViewModel : ViewModel() {

}

class IngredientsViewModel(private val repo: IngredientRepository) : ViewModel() {
    // private stateflow
    private var _uiState = MutableStateFlow(IngredientsUiState())

    // public stateflow: read only
    val uiState: StateFlow<IngredientsUiState> = _uiState.asStateFlow()

    //By calling this, we recompose automatically
    suspend fun updateQuantity(quantity: Int, item: Ingredient){
        repo.updateOwnedIngredient(item.copy(quantity = quantity))
    }

    fun getOwnedIngredients():List<Ingredient>{
        return _uiState.value.ownedIngredients
    }
    fun getPreIngredients():List<Ingredient>{
        return _uiState.value.preIngredients
    }

    fun loadPopup(name: String){
        _uiState.value = _uiState.value.copy(popupId = name)
    }

    fun removePopup(){
        _uiState.value = _uiState.value.copy(popupId = null)
    }

    init {
        viewModelScope.launch {
//            for (i in 1..100){
//                repo.addPre(Ingredient("PreIngredient${i}",
//                    MeasurementUnit.G,
//                    Random.nextInt(from = 1, until = 500))
//                )
//            }
//            for (i in 101..200){
//                repo.addOwnedIngredient(Ingredient("OwnedIngredient${i}",
//                    MeasurementUnit.G,
//                    Random.nextInt(from = 1, until = 500))
//                )
//            }
            val ownedJob = async {
                val result = repo.getAllOwnedIngredientsStream().first()
                _uiState.value = _uiState.value.copy(ownedIngredients = result)
            }
            val preJob = async {
                // We DON'T use collect because it is hot and infinite, meaning an infinite
                // waiting of flow
                val result = repo.getAllPreIngredientsStream().first()
                _uiState.value = _uiState.value.copy(preIngredients = result)
            }
            ownedJob.await()
            preJob.await()
            _uiState.value = _uiState.value.copy(isOwnedLoading = false)
            _uiState.value = _uiState.value.copy(isPreLoading = false)

            // Keep listening for changes in the database to update viewModel state automatically
            repo.getAllPreIngredientsStream().collect {
                _uiState.value = _uiState.value.copy(preIngredients = it)
            }
            repo.getAllOwnedIngredientsStream().collect {
                _uiState.value = _uiState.value.copy(ownedIngredients = it)
            }
        }
    }
}

class BrowseViewModel : ViewModel() {

}