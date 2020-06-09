package com.example.transactionalsmstracker.ui.base


import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
        setContentView(provideLayoutId())
        setupObserver()
        setupView(savedInstanceState)
        viewModel.onCreate()
    }

    //ViewModel observes the live data
    protected open fun setupObserver() {
        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })
    }


    fun showMessage(message: String) =
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

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