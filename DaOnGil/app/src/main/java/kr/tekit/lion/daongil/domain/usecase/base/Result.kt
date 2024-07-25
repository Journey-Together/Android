package kr.tekit.lion.daongil.domain.usecase.base

sealed class Result<out T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Error<T>(val error: Throwable) : Result<T>()
}

fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    return when (this) {
        is Result.Success<T> ->{
            action(value)
            this
        }
        is Result.Error<T> -> {
            this
        }
    }
}

fun <T> Result<T>.onError(action: (Throwable) -> Unit): Result<T> {
    return when (this) {
        is Result.Success<T> ->{
            this
        }
        is Result.Error<T> -> {
            action(error)
            this
        }
    }
}

fun <A, B, R> combineResults(
    resultA: Result<A>,
    resultB: Result<B>,
    combine: (A, B) -> R
): Result<R> {
    return when (resultA) {
        is Result.Success -> {
            when (resultB) {
                is Result.Success -> Result.Success(combine(resultA.value, resultB.value))
                is Result.Error -> Result.Error(resultB.error)
            }
        }
        is Result.Error -> Result.Error(resultA.error)
    }
}