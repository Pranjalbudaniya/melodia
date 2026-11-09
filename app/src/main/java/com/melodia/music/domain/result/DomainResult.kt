package com.melodia.music.domain.result

sealed interface DomainError {
    data object NotFound : DomainError

    data object PermissionDenied : DomainError

    data object Unavailable : DomainError

    data object InvalidInput : DomainError

    data class Unknown(
        val detail: String?,
    ) : DomainError
}

sealed interface DomainResult<out T> {
    data class Success<out T>(
        val value: T,
    ) : DomainResult<T>

    data class Failure(
        val error: DomainError,
    ) : DomainResult<Nothing>
}

inline fun <T, R> DomainResult<T>.map(transform: (T) -> R): DomainResult<R> =
    when (this) {
        is DomainResult.Success -> DomainResult.Success(transform(value))
        is DomainResult.Failure -> this
    }

fun <T> DomainResult<T>.getOrNull(): T? =
    when (this) {
        is DomainResult.Success -> value
        is DomainResult.Failure -> null
    }

inline fun <T, R> DomainResult<T>.fold(
    onSuccess: (T) -> R,
    onFailure: (DomainError) -> R,
): R =
    when (this) {
        is DomainResult.Success -> onSuccess(value)
        is DomainResult.Failure -> onFailure(error)
    }
