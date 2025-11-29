package com.example.vinyls.viewmodel

import androidx.lifecycle.ViewModel
import com.example.vinyls.model.Album
import com.example.vinyls.model.Track
import com.example.vinyls.model.TrackRequest
import com.example.vinyls.repositories.RemoteTrackManagementRepository
import com.example.vinyls.repositories.TrackManagementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

private data class TrackInput(
    val name: String,
    val duration: String,
    val trackNumber: Int
)

data class TrackFormState(
    val id: Long = System.nanoTime(),
    val title: String = "",
    val duration: String = "",
    val trackNumber: String = ""
) {
    fun isBlank() = title.isBlank() && duration.isBlank() && trackNumber.isBlank()
    fun isComplete() = title.isNotBlank() && duration.isNotBlank() && trackNumber.isNotBlank()
}

data class AddTracksUiState(
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val album: Album? = null,
    val forms: List<TrackFormState> = listOf(TrackFormState()),
    val canSubmit: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class AddTracksViewModel(
    private val repository: TrackManagementRepository = RemoteTrackManagementRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTracksUiState())
    val uiState: StateFlow<AddTracksUiState> = _uiState

    fun loadAlbum(albumId: Int) {
        val albumLoaded = _uiState.value.album
        if (albumLoaded?.id == albumId && !_uiState.value.isLoading) return

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null, successMessage = null)
        repository.fetchAlbum(albumId) { result ->
            result.onSuccess { album ->
                _uiState.value = AddTracksUiState(
                    isLoading = false,
                    album = album,
                    forms = listOf(TrackFormState()),
                    canSubmit = false
                )
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = throwable.localizedMessage ?: "Unable to load album"
                )
            }
        }
    }

    fun addForm() {
        _uiState.update { current ->
            val updatedForms = current.forms + TrackFormState()
            current.copy(forms = updatedForms, canSubmit = canSubmit(updatedForms))
        }
    }

    fun removeForm(formId: Long) {
        _uiState.update { current ->
            if (current.forms.size == 1) return@update current
            val updatedForms = current.forms.filterNot { it.id == formId }
            current.copy(forms = if (updatedForms.isEmpty()) listOf(TrackFormState()) else updatedForms, canSubmit = canSubmit(updatedForms))
        }
    }

    fun updateTitle(formId: Long, value: String) = updateForm(formId) { copy(title = value) }
    fun updateDuration(formId: Long, value: String) = updateForm(formId) { copy(duration = value) }
    fun updateNumber(formId: Long, value: String) = updateForm(formId) { copy(trackNumber = value.filter { it.isDigit() }) }

    private fun updateForm(formId: Long, updater: TrackFormState.() -> TrackFormState) {
        _uiState.update { current ->
            val updatedForms = current.forms.map { form -> if (form.id == formId) form.updater() else form }
            current.copy(forms = updatedForms, canSubmit = canSubmit(updatedForms))
        }
    }

    fun submitTracks(albumId: Int) {
        val album = _uiState.value.album ?: return
        val validation = validateForms(album)
        validation.fold(
            onSuccess = { inputs ->
                if (inputs.isEmpty()) {
                    _uiState.update { it.copy(errorMessage = "Complete at least one track") }
                    return
                }
                _uiState.update { it.copy(isSaving = true, errorMessage = null) }
                submitSequentially(albumId, inputs, 0)
            },
            onFailure = { error ->
                _uiState.update { it.copy(errorMessage = error.message ?: "Invalid track information") }
            }
        )
    }

    fun clearErrorMessage() {
        if (_uiState.value.errorMessage != null) {
            _uiState.update { it.copy(errorMessage = null) }
        }
    }

    fun clearSuccessMessage() {
        if (_uiState.value.successMessage != null) {
            _uiState.update { it.copy(successMessage = null) }
        }
    }

    private fun validateForms(album: Album): Result<List<TrackInput>> {
        val cleanedForms = _uiState.value.forms
        val completeForms = cleanedForms.filter { it.isComplete() }
        val partiallyFilled = cleanedForms.any { !it.isBlank() && !it.isComplete() }
        if (partiallyFilled) {
            return Result.failure(IllegalArgumentException("Complete or clear all fields"))
        }
        if (completeForms.isEmpty()) {
            return Result.failure(IllegalArgumentException("Add at least one track"))
        }

        val parsed = mutableListOf<TrackInput>()
        completeForms.forEach { form ->
            val number = form.trackNumber.toIntOrNull()
                ?: return Result.failure(IllegalArgumentException("Track number must be numeric"))
            parsed.add(
                TrackInput(
                    name = form.title.trim(),
                    duration = form.duration.trim(),
                    trackNumber = number
                )
            )
        }

        val duplicateNames = parsed.groupBy { it.name.lowercase() }.any { it.value.size > 1 }
        if (duplicateNames) {
            return Result.failure(IllegalArgumentException("Duplicate track titles are not allowed"))
        }

        val existingNames = album.tracks.map { it.name.lowercase() }.toSet()
        if (parsed.any { it.name.lowercase() in existingNames }) {
            return Result.failure(IllegalArgumentException("Track already exists in this album"))
        }

        val existingNumbers = album.tracks.mapNotNull { it.trackNumber }.toSet()
        val duplicateNumbers = parsed.groupBy { it.trackNumber }.any { it.value.size > 1 }
        if (duplicateNumbers || parsed.any { it.trackNumber in existingNumbers }) {
            return Result.failure(IllegalArgumentException("Repeated track numbers are not allowed"))
        }

        return Result.success(parsed)
    }

    private fun submitSequentially(albumId: Int, inputs: List<TrackInput>, index: Int) {
        if (index >= inputs.size) {
            _uiState.update { it.copy(isSaving = false, successMessage = "Tracks added successfully") }
            return
        }
        val request = TrackRequest(
            name = inputs[index].name,
            duration = inputs[index].duration
        )
        repository.addTrack(albumId, request) { result ->
            result.onSuccess { track ->
                appendTrack(track, inputs[index])
                submitSequentially(albumId, inputs, index + 1)
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = error.localizedMessage ?: "Unable to add track"
                    )
                }
            }
        }
    }

    private fun appendTrack(track: Track, fallback: TrackInput) {
        _uiState.update { current ->
            val album = current.album ?: return@update current
            val normalizedTrack = track.copy(trackNumber = track.trackNumber ?: fallback.trackNumber)
            val updatedAlbum = album.copy(tracks = album.tracks + normalizedTrack)
            current.copy(album = updatedAlbum)
        }
    }

    private fun canSubmit(forms: List<TrackFormState>): Boolean {
        return !forms.none { it.isComplete() }
    }
}
