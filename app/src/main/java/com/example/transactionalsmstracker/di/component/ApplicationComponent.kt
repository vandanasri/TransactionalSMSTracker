package com.example.transactionalsmstracker.di.component

import android.app.Application
import android.content.Context
import com.example.transactionalsmstracker.TransactionalSMSTrackerApplication
import com.example.transactionalsmstracker.di.ApplicationContext
import com.example.transactionalsmstracker.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: TransactionalSMSTrackerApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getApplication(): Application
}