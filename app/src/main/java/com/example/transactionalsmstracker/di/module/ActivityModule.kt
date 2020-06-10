package com.example.transactionalsmstracker.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.example.transactionalsmstracker.di.ActivityContext
import com.example.transactionalsmstracker.remote.PostSMSDataRepo
import com.example.transactionalsmstracker.ui.MainActivityViewModel
import com.example.transactionalsmstracker.ui.base.BaseActivity
import com.example.transactionalsmstracker.utils.ViewModelProviderFactory
import com.example.transactionalsmstracker.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>){

    @ActivityContext
    @Provides
    fun provideContext(): Context = activity

    @Provides
    fun provideMainViewModel(
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        postSMSDataRepo: PostSMSDataRepo
    ): MainActivityViewModel =
        ViewModelProviders.of(activity, ViewModelProviderFactory(MainActivityViewModel::class) {
            MainActivityViewModel(compositeDisposable, networkHelper, postSMSDataRepo)
        }).get(MainActivityViewModel::class.java)

}