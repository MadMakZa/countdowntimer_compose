package makza.afonsky.countdowntimer_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import makza.afonsky.countdowntimer_compose.ui.theme.Countdowntimer_composeTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Countdowntimer_composeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CountDownTimerScreen()
                }
            }
        }
    }
}


@Composable
fun CountDownTimerScreen(viewModel: CountDownTimerViewModel = viewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewModel.apply {
            Text(text = timerText.value, fontSize = 28.sp)
            Button(onClick = {
                if (isPlaying.value) stopCountDownTimer() else startCountDownTimer()
            }) {
                Text(text = if (isPlaying.value) "Stop CountDown" else "Start CountDown")
            }
            Button(onClick = { 
                resetCountDownTimer() 
            }) {
                Text(text = "Reset Timer")
            }
        }
    }
}