package com.semba.androidsamples.ArchPagination.Dagger

import com.semba.androidsamples.ArchPagination.Helper.PagedApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: PagedApplication)
}