package com.harrycampaz.searchproducts.data.network

import com.harrycampaz.searchproducts.data.models.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ProductApi {

    @GET("sites/MLA/search")
    suspend fun searchProducts(
        @Query("q") query: String
    ): ProductResponse

}