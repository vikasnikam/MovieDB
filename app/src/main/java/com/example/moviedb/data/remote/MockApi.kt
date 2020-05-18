package com.example.moviedb.data.remote

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetCastAndCrewResponse
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetMovieVideos
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MockApi(
) : ApiService {

    override suspend fun getLatestMovieAsync(
        hashMap: HashMap<String, String>
    ): GetMovieListResponse =
        when (HttpURLConnection.HTTP_UNAUTHORIZED) {
            1 -> {
                throw BaseException.toNetworkError(
                    cause = UnknownHostException()
                )
            }

            2 -> {
                throw BaseException.toNetworkError(
                    cause = SocketTimeoutException()
                )
            }

            HttpURLConnection.HTTP_OK -> {
                GetMovieListResponse()
            }

            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                throw BaseException.toServerError(
                    serverErrorResponse = ServerErrorResponse(
                        message = "Test code 401"
                    ),
                    response = null,
                    httpCode = HttpURLConnection.HTTP_UNAUTHORIZED
                )
            }

            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                throw BaseException.toServerError(
                    serverErrorResponse = ServerErrorResponse(
                        message = "Test code 500"
                    ),
                    response = null,
                    httpCode = HttpURLConnection.HTTP_INTERNAL_ERROR
                )
            }

            else -> GetMovieListResponse()
        }

    override suspend fun getMovieAsync(
        hashMap: HashMap<String, String>
    ): Movie = Movie("1")

    override suspend fun getMovieCreditsAsync(movieId: String): GetCastAndCrewResponse {
        return GetCastAndCrewResponse()
    }

    override suspend fun getSimilarMovieAsync(movieId: String): GetMovieListResponse {
        return GetMovieListResponse()
    }

    override suspend fun getVideoAsync(movieId: String): GetMovieVideos {
        return GetMovieVideos()
    }

}