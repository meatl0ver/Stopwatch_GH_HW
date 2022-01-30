package raul.imashev.stopwatch.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import raul.imashev.stopwatch.R
import raul.imashev.stopwatch.databinding.ActivityMainBinding
import raul.imashev.stopwatch.domain.*
import raul.imashev.stopwatch.domain.model.TimestampMillisecondsFormatter
import raul.imashev.stopwatch.viewModel.StopWatchViewModel
import raul.imashev.stopwatch.viewModel.StopWatchViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val timestampProvider: ITimestampProvider = TimestampProviderImpl()

    private lateinit var modelFirst: StopWatchViewModel
    private lateinit var modelSecond: StopWatchViewModel

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        init()

        scope.launch {
            modelFirst.ticker.collect {
                binding.textTime.text = it
            }
        }

        scope.launch {
            modelSecond.ticker.collect {
                binding.textTimeTwo.text = it
            }
        }
    }

    private fun init() {
        modelFirst = StopWatchViewModelFactory(
            stopwatchStateHolder = StopwatchStateHolder(
                StopwatchStateCalculator(
                    timestampProvider,
                    ElapsedTimeCalculator(timestampProvider),
                ),
                ElapsedTimeCalculator(timestampProvider),
                TimestampMillisecondsFormatter()
            ),
            CoroutineScope(
                Dispatchers.Default
                        + SupervisorJob()
            )
        ).create(StopWatchViewModel::class.java)

        modelSecond = StopWatchViewModelFactory(
            stopwatchStateHolder = StopwatchStateHolder(
                StopwatchStateCalculator(
                    timestampProvider,
                    ElapsedTimeCalculator(timestampProvider),
                ),
                ElapsedTimeCalculator(timestampProvider),
                TimestampMillisecondsFormatter()
            ),
            CoroutineScope(
                Dispatchers.Default
                        + SupervisorJob()
            )
        ).create(StopWatchViewModel::class.java)

        binding.buttonStart.setOnClickListener {
            modelFirst.start()
        }
        binding.buttonPause.setOnClickListener {
            modelFirst.pause()
        }
        binding.buttonStop.setOnClickListener {
            modelFirst.stop()
        }
        binding.buttonStartTwo.setOnClickListener {
            modelSecond.start()
        }
        binding.buttonPauseTwo.setOnClickListener {
            modelSecond.pause()
        }
        binding.buttonStopTwo.setOnClickListener {
            modelSecond.stop()
        }
    }
}