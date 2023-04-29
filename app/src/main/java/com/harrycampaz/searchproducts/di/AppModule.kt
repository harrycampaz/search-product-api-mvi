package com.harrycampaz.searchproducts.di

import com.harrycampaz.searchproducts.common.Constants
import com.harrycampaz.searchproducts.data.network.ProductApi
import com.harrycampaz.searchproducts.data.repository.ProductRepository
import com.harrycampaz.searchproducts.domain.repository.IProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providerProductRepository(
        productApi: ProductApi
    ): IProductRepository = ProductRepository(productApi)

    @Singleton
    @Provides
    fun provideRetrofit(): ProductApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApi::class.java)
    }

}
