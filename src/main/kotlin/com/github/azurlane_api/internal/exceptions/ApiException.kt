package com.github.azurlane_api.internal.exceptions

import java.lang.Exception

class ApiException(override val message: String) : Exception(message)