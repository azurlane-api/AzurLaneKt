package com.github.kurozeropb.internal.responses

interface IBaseResponse {
    val statusCode: Int
    val statusMessage: String
    val message: String
}