package com.melodia.music.domain.model

@JvmInline
value class SongId(
    val value: String,
)

@JvmInline
value class AlbumId(
    val value: String,
)

@JvmInline
value class ArtistId(
    val value: String,
)

@JvmInline
value class PlaylistId(
    val value: String,
)

@JvmInline
value class SourceId(
    val value: String,
) {
    companion object {
        val LOCAL = SourceId("local")
    }
}

/** A null tag means the file does not have it. */
data class Song(
    val id: SongId,
    val sourceId: SourceId,
    val uri: String,
    val title: String,
    val artistId: ArtistId?,
    val artistName: String?,
    val albumId: AlbumId?,
    val albumTitle: String?,
    val durationMs: Long,
    val trackNumber: Int?,
    val discNumber: Int?,
    val year: Int?,
    val genre: String?,
    val dateAddedMs: Long,
    val mimeType: String?,
)

data class Album(
    val id: AlbumId,
    val title: String?,
    val artistName: String?,
    val songCount: Int,
    val year: Int?,
)

data class Artist(
    val id: ArtistId,
    val name: String?,
    val songCount: Int,
    val albumCount: Int,
)

data class Genre(
    val name: String,
    val songCount: Int,
)

data class Playlist(
    val id: PlaylistId,
    val name: String,
    val songCount: Int,
    val createdAtMs: Long,
    val updatedAtMs: Long,
)
