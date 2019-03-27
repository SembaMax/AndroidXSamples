package com.semba.androidsamples.Dagger

import androidx.lifecycle.ViewModelProvider
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.semba.androidsamples.API.ApiCallInterface
import com.semba.androidsamples.API.URLs
import com.semba.androidsamples.ArchPagination.Data.Repository
import com.semba.androidsamples.ArchPagination.MainScreen.ViewModelFactory
import com.semba.androidsamples.Shared.NotificationManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setLenient().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit
    {
        val retrofit = Retrofit.Builder()
            .baseUrl(URLs.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit
    }

    @Provides
    @Singleton
    fun getApiCallInterface(retrofit: Retrofit): ApiCallInterface
    {
        return retrofit.create(ApiCallInterface::class.java)
    }

    @Provides
    @Singleton
    fun getRequestHeader(): OkHttpClient
    {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor {
            val original = it.request()
            val request = original.newBuilder().build()
            it.proceed(request)
        }
            .connectTimeout(100,TimeUnit.SECONDS)
            .writeTimeout(100,TimeUnit.SECONDS)
            .readTimeout(100,TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun getRepository(apiCallInterface: ApiCallInterface) = Repository(apiCallInterface)

    @Provides
    @Singleton
    fun getViewModelFactory(repository: Repository): ViewModelProvider.Factory = ViewModelFactory(repository)

    @Provides
    @Singleton
    fun getNotificationManager(): NotificationManager = NotificationManager()

}