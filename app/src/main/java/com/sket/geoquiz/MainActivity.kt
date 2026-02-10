package com.sket.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.sket.geoquiz.ui.theme.GeoQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuestionScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionScreen(modifier: Modifier = Modifier) {
    val (isAnswered, setIsAnswered) = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        TrueFalseButtonsRow(
            onTrueClick = { setIsAnswered(true) },
            onFalseClick = { setIsAnswered(true) },
            isAnswered = isAnswered,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TrueFalseButtonsRow(
    onTrueClick: () -> Unit,
    onFalseClick: () -> Unit,
    isAnswered: Boolean,
    modifier: Modifier = Modifier
) {
    val rowAlpha = if (isAnswered) 0.0f else 1.0f

    Row(
        modifier = modifier
            .fillMaxWidth()
            .alpha(rowAlpha),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = onTrueClick, enabled = !isAnswered) {
            Text(
                text = "True",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Button(onClick = onFalseClick, enabled = !isAnswered) {
            Text(
                text = "False",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
