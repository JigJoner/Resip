package com.example.resip.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resip.R
import com.example.resip.model.Ingredient
import com.example.resip.model.MeasurementUnit
import com.example.resip.ui.viewmodel.IngredientsViewModel
import kotlinx.coroutines.flow.StateFlow

enum class ListTypes() {
    Card(),
    Line(),
    Detailed()
}

@Composable
fun IngredientsList(
    modifier: Modifier = Modifier,
    listType: ListTypes,
    list: List<Ingredient>,
) {
    val list = listOf<Ingredient>(
        Ingredient(
            "Name1",
            MeasurementUnit.G,
            5f,
            false,
            true
        )
    )
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(1)
    ) {
        when (listType) {
            ListTypes.Card -> {
                items(list) { item ->
                    IngredientCard(
                        item = item
                    )
                }
            }

            ListTypes.Line -> TODO()
            ListTypes.Detailed -> TODO()
        }
    }
}

@Composable
fun IngredientCard(
    modifier: Modifier = Modifier,
    item: Ingredient
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.spacing_medium))
            .height(dimensionResource(R.dimen.button_height) + dimensionResource(R.dimen.spacing_small) * 2)
    ) {
        Row() {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    imageVector = Icons.Filled.Image, //Temp
                    contentDescription = null
                )
            }
            Row(
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {},
                    enabled = true,
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowUpward,
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = {},
                    enabled = true,
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDownward,
                        contentDescription = null
                    )
                }
                NumberTextField(
                    label = item.quantity.toString()
                )
            }
        }
        Button(
            modifier = Modifier
//                .padding(dimensionResource(R.dimen.spacing_small))
                .aspectRatio(1f),

            onClick = {},
        ) {

        }
    }
}

@Composable
fun CardList(
    modifier: Modifier = Modifier,
    isRecipe: Boolean,
    rowQuantity: Int = 1, //The number of cards per row
    listType: ListTypes,
) {
    val trueRowQuantity = when (listType){
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
                    //        items(list) { item ->
                    //            RecipeCard(item)
                    //        }
                } else {
//                    val uiState = (viewModel as IngredientsViewModel)

                }
            }
            ListTypes.Line -> {
                if (isRecipe){

                }else{

                }
            }
            ListTypes.Detailed -> {
                if (isRecipe){

                }else{

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