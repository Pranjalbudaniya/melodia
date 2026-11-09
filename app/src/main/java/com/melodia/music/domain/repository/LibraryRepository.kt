package com.melodia.music.domain.repository

import com.melodia.music.domain.model.Album
import com.melodia.music.domain.model.AlbumId
import com.melodia.music.domain.model.Artist
import com.melodia.music.domain.model.ArtistId
import com.melodia.music.domain.model.Genre
import com.melodia.music.domain.model.Song
import com.melodia.music.domain.model.SongId
import com.melodia.music.domain.result.DomainResult
import kotlinx.coroutines.flow.Flow

/**
 * Each flow emits the current list at once (an empty list is a valid value),
 * emits again on every change, and never completes or throws; default order is
 * title ascending, case-insensitive (album songs: disc, then track, then title);
 * sort options come with P43.
 */
interface LibraryRepository {
    fun observeSongs(): Flow<List<Song>>

    fun observeAlbums(): Flow<List<Album>>

    fun observeArtists(): Flow<List<Artist>>

    fun observeGenres(): Flow<List<Genre>>

    fun observeAlbumSongs(albumId: AlbumId): Flow<List<Song>>

    fun observeArtistSongs(artistId: ArtistId): Flow<List<Song>>

    fun observeGenreSongs(genre: String): Flow<List<Song>>

    suspend fun getSong(id: SongId): DomainResult<Song>
}
