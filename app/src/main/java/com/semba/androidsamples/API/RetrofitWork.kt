package com.semba.androidsamples.API

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitWork {

    fun provideGson() = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setLenient().create()

    fun getRequestHeader(): OkHttpClient
    {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor {
            val original = it.request()
            val request = original.newBuilder().build()
            it.proceed(request)
        }
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    fun provideRetrofit(): Retrofit
    {
        val retrofit = Retrofit.Builder()
            .baseUrl(URLs.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getRequestHeader())
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .build()

        return retrofit
    }

    fun getApiCallInterface(): ApiCallInterface
    {
        return provideRetrofit().create(ApiCallInterface::class.java)
    }
}