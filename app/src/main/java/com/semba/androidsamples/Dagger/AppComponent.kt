package com.semba.androidsamples.Dagger

import com.semba.androidsamples.Helper.PagedApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: PagedApplication)
}