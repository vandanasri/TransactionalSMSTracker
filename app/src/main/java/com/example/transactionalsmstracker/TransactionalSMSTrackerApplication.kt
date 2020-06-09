package com.example.transactionalsmstracker

import android.app.Application
import com.example.transactionalsmstracker.di.component.ApplicationComponent
import com.example.transactionalsmstracker.di.component.DaggerApplicationComponent
import com.example.transactionalsmstracker.di.module.ApplicationModule

class TransactionalSMSTrackerApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}