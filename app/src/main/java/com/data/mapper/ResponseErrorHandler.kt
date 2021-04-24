package com.data.mapper

import com.google.gson.Gson
import com.data.entity.response.ErrorResponse
import org.json.JSONObject
import retrofit2.Response

class ResponseErrorHandler(private val gson: Gson) {
    fun map(response: Any): ErrorResponse {
        when (response) {
            is Response<*> ->
                try {
                    var errorResponse = gson.fromJson(response.errorBody()!!.byteStream().toString(), ErrorResponse::class.java)
                    errorResponse = errorResponse?.let {
                        ErrorResponse(it.error, it.errorDescription, it.message)
                    } ?: ErrorResponse(
                            error = response.raw().message(),
                        )

                    response.body()?.let {
                        val jsonObject = JSONObject(it.toString())
                        if(jsonObject.has("errorDisplay")) {
                            errorResponse.errorDescription = jsonObject.getString("errorDescription")
                        } else {
                            errorResponse.errorDescription = null
                        }
                    }

                    return errorResponse
                } catch (t: Throwable) {
                    return ErrorResponse(
                         exception = t,
                        responseCode = response.code()
                    )
                }

            else -> return ErrorResponse(
                exception = Throwable(),
                responseCode = 0
            )
        }
    }
}