package com.harrycampaz.searchproducts.data.network

import com.harrycampaz.searchproducts.data.models.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ProductApi {

    @GET("sites/MLA/search{q}")
    suspend fun searchProducts(
        @Path("query") queryString: String
    ): ProductResponse
}