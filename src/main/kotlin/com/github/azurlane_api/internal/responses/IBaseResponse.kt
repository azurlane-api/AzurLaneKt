package com.github.azurlane_api.internal.responses

interface IBaseResponse {
    val statusCode: Int
    val statusMessage: String
    val message: String
}