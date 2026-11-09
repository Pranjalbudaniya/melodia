package com.melodia.music.domain.result

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DomainResultTest {
    @Test
    fun mapTransformsSuccessValue() {
        val result: DomainResult<Int> = DomainResult.Success(2)
        val transformed = result.map { it * 3 }
        assertEquals(DomainResult.Success(6), transformed)
    }

    @Test
    fun mapKeepsFailureUntouched() {
        var callCounter = 0
        val failure: DomainResult<Int> = DomainResult.Failure(DomainError.NotFound)
        val transformed =
            failure.map {
                callCounter++
                it * 3
            }
        assertEquals(DomainResult.Failure(DomainError.NotFound), transformed)
        assertEquals(0, callCounter)
    }

    @Test
    fun getOrNullReturnsValueOnlyForSuccess() {
        val success: DomainResult<String> = DomainResult.Success("a")
        val failure: DomainResult<String> = DomainResult.Failure(DomainError.Unavailable)
        assertEquals("a", success.getOrNull())
        assertNull(failure.getOrNull())
    }

    @Test
    fun foldPicksTheMatchingBranch() {
        val success: DomainResult<Int> = DomainResult.Success(1)
        val successOutcome =
            success.fold(
                onSuccess = { "ok" },
                onFailure = { "failed" },
            )
        assertEquals("ok", successOutcome)

        val failure: DomainResult<Int> = DomainResult.Failure(DomainError.PermissionDenied)
        val failureOutcome =
            failure.fold(
                onSuccess = { null },
                onFailure = { it },
            )
        assertEquals(DomainError.PermissionDenied, failureOutcome)
    }
}
