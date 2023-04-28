package com.harrycampaz.searchproducts.common

data class NetworkException(override val message: String) : Throwable()
data class ServerException(val code: Int, override val message: String) : Throwable()
data class UnknownException(override val message: String) : Throwable()