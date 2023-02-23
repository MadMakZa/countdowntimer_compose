package makza.afonsky.countdowntimer_compose

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import makza.afonsky.countdowntimer_compose.TimeFormatExt.timeFormat
import java.util.concurrent.TimeUnit

class CountDownTimerViewModel : ViewModel() {

    private var countDownTimer: CountDownTimer? = null

    private val userInputHour = TimeUnit.HOURS.toMillis(1)
    private val userInputMinute = TimeUnit.MINUTES.toMillis(10)
    private val userInputSecond = TimeUnit.SECONDS.toMillis(30)

    val initialTotalTimeInMillis = userInputHour + userInputMinute + userInputSecond
    var timeLeft = mutableStateOf(initialTotalTimeInMillis)
    val countDownInterval = 1000L //one seconds is the lowest

    val timerText = mutableStateOf(timeLeft.value.timeFormat())

    val isPlaying = mutableStateOf(false)

    fun startCountDownTimer() = viewModelScope.launch {
        isPlaying.value = true
        countDownTimer = object : CountDownTimer(timeLeft.value, countDownInterval) {
            override fun onTick(currentTimeLeft: Long) {
                timerText.value = currentTimeLeft.timeFormat()
                timeLeft.value = currentTimeLeft
            }

            override fun onFinish() {
                timerText.value = initialTotalTimeInMillis.timeFormat()
                isPlaying.value = false
            }
        }.start()
    }

    fun stopCountDownTimer() = viewModelScope.launch{
        isPlaying.value = false
        countDownTimer?.cancel()
    }

    fun resetCountDownTimer() = viewModelScope.launch {
        isPlaying.value = false
        countDownTimer?.cancel()
        timerText.value = initialTotalTimeInMillis.timeFormat()
        timeLeft.value = initialTotalTimeInMillis
    }
}

object TimeFormatExt {
    private const val FORMAT = "%02d:%02d:%02d"

    fun Long.timeFormat(): String = String.format(
        FORMAT,
        TimeUnit.MILLISECONDS.toHours(this),
        TimeUnit.MILLISECONDS.toMinutes(this) % 60,
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )
}