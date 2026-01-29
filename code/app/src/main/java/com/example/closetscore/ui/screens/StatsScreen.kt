package com.example.closetscore.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.EuroSymbol
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.closetscore.R
import com.example.closetscore.ui.AppViewModelProvider
import com.example.closetscore.ui.components.CategoriesChart
import com.example.closetscore.ui.components.HeaderText
import com.example.closetscore.ui.components.StatsTag
import com.example.closetscore.ui.components.TotalValueChart
import com.example.closetscore.ui.components.WearFrequencyChartFinal
import com.example.closetscore.ui.theme.LightGreen
import com.example.closetscore.ui.viewmodel.ScoreViewModel
import ir.ehsannarmani.compose_charts.extensions.format

@Composable
fun StatsScreen(
    scoreViewModel: ScoreViewModel = viewModel(factory = AppViewModelProvider.Factory),
    paddingValues: PaddingValues
) {
    val currentScore by scoreViewModel.score.collectAsState()
    val dataState by scoreViewModel.dataState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(
            top = paddingValues.calculateTopPadding() + 16.dp,
            bottom = paddingValues.calculateBottomPadding() + 16.dp,
            start = 16.dp,
            end = 16.dp
        )
    ) {
        item {
            HeaderText("Dashboard")
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                MainScore(currentScore)
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatsTag(
                        count = dataState.itemSize.toString(),
                        label = "Items",
                        icon = Icons.Default.Checkroom
                    )
                    StatsTag(
                        count = dataState.totalWears.toString(),
                        label = "Total Wears",
                        icon = Icons.Default.Repeat
                    )
                    StatsTag(
                        count = dataState.thriftAverage.format(2) + "%",
                        label = "Thrifted",
                        icon = Icons.Default.Eco
                    )
                    StatsTag(
                        count = dataState.priceAverage.format(2) + "€",
                        label = "Average Price",
                        icon = Icons.Default.EuroSymbol
                    )
                }
            }
        }

        item {
            TotalValueChart(scoreViewModel)
        }
        item {
            WearFrequencyChartFinal(scoreViewModel)
        }
        item {
            CategoriesChart(scoreViewModel)
        }
    }
}

@Composable
fun MainScore(score: Int) {
    val isHappy = score >= 60
    val mainColor = if (isHappy) LightGreen else Color.Red
    val dogIcon = if (isHappy) R.drawable.dogicon else R.drawable.maddog
    val trackColor = Color.LightGray

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(200.dp)
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 15.dp.toPx())
            )

            val sweepAngle = 360f * (score / 100f)
            drawArc(
                color = mainColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 13.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                painter = painterResource(id = dogIcon),
                contentDescription = null,
                tint = mainColor,
                modifier = Modifier.size(32.dp)
            )

            Text(
                text = score.toString(),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 56.sp,
                    color = Color.Black
                ),
                modifier = Modifier.offset(y = (-4).dp)
            )

            Text(
                text = "ECO SCORE",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 12.sp,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            )
        }
    }
}