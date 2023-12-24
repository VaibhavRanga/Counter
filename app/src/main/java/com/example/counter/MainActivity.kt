package com.example.counter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val counterUiState by counterViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CountBlock(count = counterUiState.count)
        TargetBlock(target = counterUiState.target)
        Button(onClick = { counterViewModel.addCount() }) {
            Text(text = "Add")
        }
        Button(onClick = { counterViewModel.minusCount() }) {
            Text(text = "Minus")
        }
        TextField(
            value = counterUiState.target.toString(),
            onValueChange = { counterViewModel.updateTargetValue(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
        if (counterUiState.targetReached)
            ToastMessage(message = stringResource(R.string.target_reached_toast))
    }
}

@Composable
fun CountBlock(
    count: Int
) {
    Text(text = stringResource(R.string.count, count))
}

@Composable
fun TargetBlock(
    target: Int
) {
    Text(text = stringResource(R.string.target, target))
}

@Composable
fun ToastMessage(message: String) {
    val context = LocalContext.current
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    CounterTheme {
        MyApp()
    }
}
