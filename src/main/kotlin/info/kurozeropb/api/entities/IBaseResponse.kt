package info.kurozeropb.api.entities

interface IBaseResponse {
    val statusCode: Int
    val statusMessage: String
    val message: String
}