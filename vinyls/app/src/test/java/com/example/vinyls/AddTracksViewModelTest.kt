package com.example.vinyls

import com.example.vinyls.model.Album
import com.example.vinyls.model.Comment
import com.example.vinyls.model.Performer
import com.example.vinyls.model.Track
import com.example.vinyls.model.TrackRequest
import com.example.vinyls.repositories.TrackManagementRepository
import com.example.vinyls.viewmodel.AddTracksViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class AddTracksViewModelTest {

    private lateinit var repository: FakeTrackRepository
    private lateinit var viewModel: AddTracksViewModel

    @Before
    fun setup() {
        repository = FakeTrackRepository()
        viewModel = AddTracksViewModel(repository)
        viewModel.loadAlbum(1)
    }

    @Test
    fun submitTracks_rejectsDuplicateNames() = runBlocking {
        repository.album = repository.album.copy(
            tracks = listOf(Track(id = 10, name = "Existing", duration = "3:00", trackNumber = 1))
        )
        viewModel = AddTracksViewModel(repository).also { it.loadAlbum(1) }
        val formId = viewModel.uiState.first().forms.first().id
        viewModel.updateTitle(formId, "Existing")
        viewModel.updateDuration(formId, "3:45")
        viewModel.updateNumber(formId, "2")

        viewModel.submitTracks(1)

        assertEquals("Track already exists in this album", viewModel.uiState.first().errorMessage)
    }

    @Test
    fun submitTracks_addsTrackAndReturnsSuccess() = runBlocking {
        val formId = viewModel.uiState.first().forms.first().id
        viewModel.updateTitle(formId, "New Track")
        viewModel.updateDuration(formId, "4:01")
        viewModel.updateNumber(formId, "5")

        viewModel.submitTracks(1)

        val state = viewModel.uiState.first()
        assertEquals("Tracks added successfully", state.successMessage)
        assertEquals(3, state.album?.tracks?.size)
        assertFalse(state.isSaving)
    }

    private class FakeTrackRepository : TrackManagementRepository {
        var album: Album = Album(
            id = 1,
            name = "Sample",
            cover = null,
            releaseDate = "1984-01-01",
            description = "",
            genre = "Folk",
            recordLabel = "Label",
            tracks = listOf(
                Track(id = 1, name = "Intro", duration = "1:00", trackNumber = 1),
                Track(id = 2, name = "Second", duration = "2:00", trackNumber = 2)
            ),
            performers = listOf(Performer(id = 3, name = "Duo", image = null, description = null)),
            comments = listOf(Comment(id = 1, rating = 5, description = "Great"))
        )
        private var nextId = 100

        override fun fetchAlbum(albumId: Int, onResult: (Result<Album>) -> Unit) {
            onResult(Result.success(album))
        }

        override fun addTrack(albumId: Int, request: TrackRequest, onResult: (Result<Track>) -> Unit) {
            val track = Track(id = nextId++, name = request.name, duration = request.duration, trackNumber = null)
            album = album.copy(tracks = album.tracks + track)
            onResult(Result.success(track))
        }
    }
}
