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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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

data class Question(val text: String, val isTrue: Boolean)

val questions = listOf(
    Question("Canberra is the capital of Australia.", true),
    Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
    Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
    Question("The source of the Nile River is in Egypt.", false),
    Question("The Amazon River is the longest river in the Americas.", true),
    Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true)
)

@Composable
fun QuestionScreen(modifier: Modifier = Modifier) {
    val (currentIndex, setCurrentIndex) = remember { mutableIntStateOf(0) }
    val (isAnswered, setIsAnswered) = remember { mutableStateOf(false) }
    val (countTrue, setCountTrue) = remember { mutableIntStateOf(0) }

    val isFinal = questions.size <= currentIndex + 1

    val onCheck: (Boolean) -> Unit = { userAnswer ->
        questions.getOrNull(currentIndex).let { question ->
            if (question == null) return@let
            val isRight = question.isTrue == userAnswer
            setIsAnswered(true)
            if (isRight) {
                setCountTrue(countTrue + 1)
            }
        }
    }

    val onNextClick: () -> Unit = {
        setCurrentIndex(currentIndex + 1)
        setIsAnswered(false)
    }

    val onRestart: () -> Unit = {
        setCurrentIndex(0)
        setIsAnswered(false)
        setCountTrue(0)
    }

    if (isFinal) {
        Dialog(onDismissRequest = onRestart) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Тест завершён!",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "Ваше количество правильных ответов: $countTrue из ${questions.size}",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = onRestart,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Пройти заново")
                    }
                }
            }
        }
    } else {
        val currentQuestion = questions[currentIndex]
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = currentQuestion.text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            HorizontalDivider(thickness = 2.dp)
            TrueFalseButtonsRow(
                onTrueClick = { onCheck(true) },
                onFalseClick = { onCheck(false) },
                enabled = !isAnswered,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (isAnswered) 0.0f else 1.0f)
            )
            NextButtonRow(
                onNextClick = onNextClick,
                enabled = isAnswered,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (isAnswered) 1.0f else 0.0f),
            )
        }
    }

}

@Composable
fun TrueFalseButtonsRow(
    onTrueClick: () -> Unit,
    onFalseClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = onTrueClick, enabled = enabled) {
            Text(
                text = "True",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Button(onClick = onFalseClick, enabled = enabled) {
            Text(
                text = "False",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun NextButtonRow(
    onNextClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        Button(onClick = onNextClick, enabled = enabled) {
            Text(
                text = "Next",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}