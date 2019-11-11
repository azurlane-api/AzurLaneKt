package info.kurozeropb.api.entities

data class ErrorResponse(
    override val statusCode: Int,
    override val statusMessage: String,
    override val message: String,
    val error: String? = null
) :IBaseResponse