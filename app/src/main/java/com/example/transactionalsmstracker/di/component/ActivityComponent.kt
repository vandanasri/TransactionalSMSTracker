package com.example.transactionalsmstracker.di.component

import com.example.transactionalsmstracker.di.ActivityScope
import com.example.transactionalsmstracker.di.module.ActivityModule
import com.example.transactionalsmstracker.ui.MainActivity
import dagger.Component


@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

}