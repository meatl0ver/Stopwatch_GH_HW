package raul.imashev.stopwatch.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import raul.imashev.stopwatch.domain.StopwatchStateHolder

class StopWatchViewModelFactory(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val scope: CoroutineScope
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        StopWatchViewModel(stopwatchStateHolder, scope) as T
}