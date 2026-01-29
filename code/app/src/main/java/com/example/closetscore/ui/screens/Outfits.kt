package com.example.closetscore.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.closetscore.ui.AppViewModelProvider
import com.example.closetscore.ui.components.HeaderText
import com.example.closetscore.ui.components.TemplateCard
import com.example.closetscore.ui.navigation.Screen
import com.example.closetscore.ui.theme.LightGreen
import com.example.closetscore.ui.theme.White
import com.example.closetscore.ui.viewmodels.TemplateViewModel

@Composable
fun OutfitsScreen(
    navController: NavController,
    templateViewModel: TemplateViewModel = viewModel(factory = AppViewModelProvider.Factory),
    paddingValues: PaddingValues
) {
    val templatesList by templateViewModel.templatesWithItems.collectAsState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(minSize = 128.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),

        contentPadding = PaddingValues(
            top = paddingValues.calculateTopPadding() + 16.dp,
            bottom = paddingValues.calculateBottomPadding() + 16.dp,
            start = 16.dp,
            end = 16.dp
        )
    ) {

        item(span = { GridItemSpan(maxLineSpan) }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-5).dp)
            ) {
                Box(modifier = Modifier.align(Alignment.CenterStart)) {
                    HeaderText("Your Outfits")
                }

                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.Template.route)
                    },
                    containerColor = LightGreen,
                    contentColor = White,
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Create outfit")
                }
            }
        }

        if (templatesList.isEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No outfits yet. Press + to create one!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }


        items(templatesList, span = { GridItemSpan(maxLineSpan) }) { templateWithItems ->
            TemplateCard(
                templateWithItems = templateWithItems,
                onClick = {
                    navController.navigate("${Screen.TemplateDetail.route}/${templateWithItems.template.id}")
                }
            )
        }
    }
}