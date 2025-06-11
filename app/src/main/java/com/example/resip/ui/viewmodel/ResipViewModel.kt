package com.example.resip.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resip.data.repository.IngredientRepository
import com.example.resip.model.Ingredient
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class ResipViewModels(
    val ingredientsViewModel: IngredientsViewModel,
    val recipesViewModel: RecipesViewModel
)

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

    fun addIngredientPopup(switch: Boolean){
        _uiState.value = _uiState.value.copy(addIngredientPopup = switch)
    }

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

    fun loadAddIngredientPopup(){
        _uiState.value = _uiState.value.copy(addIngredientPopup = true)
    }

    fun removeAddIngredientPopup(){
        _uiState.value = _uiState.value.copy(addIngredientPopup = false)
    }

    fun deleteOwnedIngredient(name: String){
        var test: Ingredient
        viewModelScope.launch {
            try{
                test = _uiState.value.ownedIngredients.filter{ it -> it.name.equals(name)}[0]
                repo.deleteOwnedIngredient(
                    test
                )
            }catch(e: Exception){
                Log.e("IngredientViewModel", e.toString())
            }
        }

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