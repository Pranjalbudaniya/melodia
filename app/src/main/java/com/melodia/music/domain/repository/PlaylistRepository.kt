package com.melodia.music.domain.repository

import com.melodia.music.domain.model.Playlist
import com.melodia.music.domain.model.PlaylistId
import com.melodia.music.domain.model.Song
import com.melodia.music.domain.model.SongId
import com.melodia.music.domain.result.DomainResult
import kotlinx.coroutines.flow.Flow

/**
 * A blank name gives Failure(InvalidInput) (create, rename); an unknown playlist gives Failure(NotFound);
 * addSongs appends in the given order, duplicates allowed, an empty list is a success;
 * removeSongs and moveSong use positions in the current order, and a position out of range
 * gives Failure(InvalidInput) with nothing changed.
 */
interface PlaylistRepository {
    fun observePlaylists(): Flow<List<Playlist>>

    fun observePlaylistSongs(id: PlaylistId): Flow<List<Song>>

    suspend fun create(name: String): DomainResult<PlaylistId>

    suspend fun rename(
        id: PlaylistId,
        name: String,
    ): DomainResult<Unit>

    suspend fun delete(id: PlaylistId): DomainResult<Unit>

    suspend fun addSongs(
        id: PlaylistId,
        songs: List<SongId>,
    ): DomainResult<Unit>

    suspend fun removeSongs(
        id: PlaylistId,
        positions: List<Int>,
    ): DomainResult<Unit>

    suspend fun moveSong(
        id: PlaylistId,
        from: Int,
        to: Int,
    ): DomainResult<Unit>
}
