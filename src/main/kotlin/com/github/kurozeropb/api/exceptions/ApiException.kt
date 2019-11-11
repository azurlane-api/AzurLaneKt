package com.github.kurozeropb.api.exceptions

import java.lang.Exception

class ApiException(override val message: String) : Exception(message)