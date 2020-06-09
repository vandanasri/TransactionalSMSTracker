package com.example.transactionalsmstracker.di

import javax.inject.Qualifier


    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class ApplicationContext

    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class ActivityContext
