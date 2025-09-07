package org.unizd.rma.curko.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.unizd.rma.curko.model.ApodItem
import org.unizd.rma.curko.repository.NasaRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class UiState(
    val loading: Boolean = false,
    val error: String? = null,
    val items: List<ApodItem> = emptyList()
)

class NasaViewModel : ViewModel() {
    private val repo = NasaRepository()
    private val _state = MutableStateFlow(UiState(loading = true))
    val state: StateFlow<UiState> = _state

    private val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    /** Umjesto init { refresh() } – čekamo da Activity pošalje ključ */
    fun refresh(apiKey: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                val (start, end) = lastNDays(10)
                val data = repo.apodRange(
                    apiKey = apiKey,
                    start = start,
                    end = end
                ).sortedByDescending { it.date }
                _state.value = UiState(loading = false, items = data)
            } catch (e: Exception) {
                _state.value = UiState(loading = false, error = e.message ?: "Unexpected error")
            }
        }
    }

    private fun lastNDays(n: Int): Pair<String, String> {
        val cal = Calendar.getInstance()
        val end = df.format(cal.time)
        cal.add(Calendar.DAY_OF_YEAR, -(n - 1))
        val start = df.format(cal.time)
        return start to end
    }
}
