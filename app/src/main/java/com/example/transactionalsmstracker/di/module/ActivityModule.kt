package com.example.transactionalsmstracker.di.module

import android.content.Context
import com.example.transactionalsmstracker.di.ActivityContext
import com.example.transactionalsmstracker.ui.base.BaseActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity<*>){

    @ActivityContext
    @Provides
    fun provideContext(): Context = activity

}