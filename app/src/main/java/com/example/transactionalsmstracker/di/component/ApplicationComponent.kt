package com.example.transactionalsmstracker.di.component

import android.app.Application
import android.content.Context
import com.example.transactionalsmstracker.TransactionalSMSTrackerApplication
import com.example.transactionalsmstracker.di.ApplicationContext
import com.example.transactionalsmstracker.di.module.ApplicationModule
import com.example.transactionalsmstracker.utils.network.NetworkHelper
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: TransactionalSMSTrackerApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getApplication(): Application

    fun getCompositeDisposable() : CompositeDisposable

    fun getNetworkHelper(): NetworkHelper
}