package com.example.transactionalsmstracker.di.module

import android.app.Application
import android.content.Context
import com.example.transactionalsmstracker.BuildConfig
import com.example.transactionalsmstracker.TransactionalSMSTrackerApplication
import com.example.transactionalsmstracker.di.ApplicationContext
import com.example.transactionalsmstracker.remote.NetworkService
import com.example.transactionalsmstracker.remote.Networking
import com.example.transactionalsmstracker.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton


@Module
class ApplicationModule(private val application : TransactionalSMSTrackerApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    @Singleton
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

    @Provides
    @Singleton
    fun providesNetworkService() : NetworkService =
        Networking.create(
            BuildConfig.BASE_URL,
            application.cacheDir,
            10 * 1024 * 1024 // 10MB
        )



}