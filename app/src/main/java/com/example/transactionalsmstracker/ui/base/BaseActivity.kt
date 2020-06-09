package com.example.transactionalsmstracker.ui.base


import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.transactionalsmstracker.TransactionalSMSTrackerApplication
import com.example.transactionalsmstracker.di.component.ActivityComponent
import com.example.transactionalsmstracker.di.component.DaggerActivityComponent
import com.example.transactionalsmstracker.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<VM: BaseViewModel> : AppCompatActivity() {


    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
    }


    //function to build Activity Component
    private fun buildActivityComponent() =
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as TransactionalSMSTrackerApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

    //to inject dagger dependencies
    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    //to setup the UI
    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    //to setup Views in the UI
    protected abstract fun setupView(savedInstanceState: Bundle?)

}