package com.example.lib_data.di

import android.content.Context
import com.example.lib_data.data.remote.ApiService
import com.example.lib_data.data.repository.RepositoryImpl
import com.example.lib_data.domain.repository.DataStorePrefImpl
import com.example.lib_data.domain.repository.Repository
import com.example.lib_data.util.Constants
import com.example.lib_data.util.DataStorePrefSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.header("Content-Type", "application/json")
                return chain.proceed(
                    requestBuilder
                        .build()
                )
            }
        })
        .build()

    @Singleton
    @Provides
    fun providesApisService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideRepositoryImpl(
        apiService: ApiService,
//        dataStore: DataStorePrefSource
    ): Repository = RepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStorePrefSource =
        DataStorePrefImpl(context)
}
