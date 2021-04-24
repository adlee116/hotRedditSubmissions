package com.data.entity.response

data class ErrorResponse(
    @Transient
    var error: String? = null,
    var errorDescription: String? = null,
    var message: String? = null,
    var errors: HashMap<String, List<String>>? = null,
    var responseCode: Int? = null,
    var exception: Throwable? = null
) {

    override fun toString(): String {
        return "$error $errorDescription $message"
    }
}