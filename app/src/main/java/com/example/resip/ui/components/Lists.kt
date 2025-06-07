package com.example.resip.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.resip.R
import com.example.resip.model.Ingredient
import com.example.resip.ui.viewmodel.IngredientsViewModel

enum class ListTypes() {
    Card(),
    Line(),
    Detailed()
}


@Composable
fun CardList(
    modifier: Modifier = Modifier,
    isRecipe: Boolean,
    rowQuantity: Int = 1, //The number of cards per row
    listType: ListTypes,
) {
    val trueRowQuantity = when (listType) {
        ListTypes.Card -> rowQuantity
        else -> 1
    }
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(trueRowQuantity)
    ) {
        when (listType) {
            ListTypes.Card -> {
                if (isRecipe) {
                    //Test
                    items(50) { index ->
                        RecipeCard() {
                            Text(text = "Item: $index")
                        }
                    }
                } else {
                }
            }

            ListTypes.Line -> {
                if (isRecipe) {

                } else {

                }
            }

            ListTypes.Detailed -> {
                if (isRecipe) {

                } else {

                }
            }
        }
    }
}

@Composable
fun RecipeCard(
    modifier: Modifier = Modifier,
//    item: Any
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.spacing_medium))
            .aspectRatio(1f)
    ) {
        content()
    }
}


@Composable
fun RecipeLineItem(
    modifier: Modifier = Modifier,
//    item: Any
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.spacing_medium))
            .aspectRatio(1f)
    ) {
        content()
    }
}

@Composable
fun IngredientLineItem(
    modifier: Modifier = Modifier,
//    item: Any
    content: @Composable () -> Unit,
) {
}

@Composable
fun RecipeDetailedItem(
    modifier: Modifier = Modifier,
//    item: Any
    content: @Composable () -> Unit,
) {
}

//@Composable
//fun IngredientDetailedItem(
//    modifier: Modifier = Modifier,
////    item: Any
//    content: @Composable () -> Unit,
//) {
//}