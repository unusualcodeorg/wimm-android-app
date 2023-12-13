package com.whereismymotivation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whereismymotivation.ui.theme.AppTheme
import com.whereismymotivation.utils.log.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.d(MainViewModel.TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    NetworkMessage(
                        viewModel = mainViewModel,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                    Message(viewModel = mainViewModel, modifier = Modifier.padding(top = 20.dp))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun NetworkMessage(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    viewModel.networkMessage.collectAsState().value.data?.let {
        if (it > 0) Text(
            text = stringResource(it),
            modifier = modifier
        )
    }

}

@Composable
fun Message(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    viewModel.message.collectAsState().value.data?.let {
        if (it.isNotEmpty()) Text(
            text = it,
            modifier = modifier
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}
