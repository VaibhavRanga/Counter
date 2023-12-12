package com.example.counter

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.counter.ui.theme.CounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp(
    counterViewModel: CounterViewModel = viewModel()
) {
    val counterState by counterViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
    ) {
        TextOutputs(currentCount = counterState.currentCount, target = counterState.target)
        AddSubtractCount(
            addCount = {
                counterViewModel.addCount()
                counterViewModel.targetReached()
            },
            minusCount = {
                if (counterState.currentCount != 0)
                    counterViewModel.minusCount()
                counterViewModel.targetReached()
            }
        )
        TextInputField(
            targetField = counterViewModel.targetInputValue.toString(),
            onTargetValueChange = { counterViewModel.enteredTargetValue(it) })
        Button(onClick = { counterViewModel.setTarget() }) {
            Text(text = "SET TARGET")
        }
        if (counterState.targetReached) {
            DisplayToast(message = "Target reached!")
        }
    }
}

@Composable
fun TextOutputs(
    currentCount: Int,
    target: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "$currentCount")
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "$target")
    }
}

@Composable
fun AddSubtractCount(
    addCount: () -> Unit,
    minusCount: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(top = 20.dp)
    ) {
        Button(onClick = addCount) {
            Text(text = "ADD COUNT")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = minusCount) {
            Text(text = "MINUS COUNT")
        }
    }
}

@Composable
fun TextInputField(
    targetField: String,
    onTargetValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = targetField,
        onValueChange = onTargetValueChange,
        label = { Text(text = "Enter target...") },
        maxLines = 1,
        modifier = modifier.padding(vertical = 20.dp)
    )
}

@Composable
fun DisplayToast(
    message: String
) {
    val context = (LocalContext.current as Activity)

    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showSystemUi = true)
@Composable
fun CounterAppPreview() {
    CounterTheme {
        MyApp()
    }
}
