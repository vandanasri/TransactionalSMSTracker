package com.example.transactionalsmstracker.ui

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transactionalsmstracker.R
import com.example.transactionalsmstracker.di.component.ActivityComponent
import com.example.transactionalsmstracker.ui.base.BaseActivity
import com.example.transactionalsmstracker.utils.AlertDialogUtil
import com.example.transactionalsmstracker.utils.PermissionUtil
import com.example.transactionalsmstracker.utils.Status
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainActivityViewModel>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Checking for SMS Read permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            if (PermissionUtil.requestReadSmsPermission(this,
                    "This App will help you to filter finance related SMS from your phone. So, please allow this app to read your SMS",
                    DialogInterface.OnClickListener { dialog, which ->
                        // YOUR CANCEL CODE
                    })
            ) {

                // Permission already granted, read sms and display
                viewModel.readMessages(this.contentResolver)
            }
        }else{
            viewModel.readMessages(this.contentResolver)
        }
    }

    override fun injectDependencies(activityComponent: ActivityComponent) = activityComponent.inject(this)

    override fun provideLayoutId(): Int = R.layout.activity_main

    //initialize layout manager and recyclerview
    override fun setupView(savedInstanceState: Bundle?) {
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_repository.layoutManager = linearLayoutManager
        val adapter = SMSRVAdapter(ArrayList())
        rv_repository.adapter = adapter
    }

    override fun setupObserver() {
        super.setupObserver()

        viewModel.loadingProgressBar.observe(this, androidx.lifecycle.Observer {
            when(it.status){
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.SUCCESS -> progressBar.visibility = View.GONE
                Status.ERROR -> progressBar.visibility = View.GONE
                else -> progressBar.visibility = View.GONE
            }

        })

        //Observe live data which is coming from view model and then passing it to recyclerview adapter
        viewModel.smsData.observe(this, androidx.lifecycle.Observer {
            rv_repository.adapter = SMSRVAdapter(it)
        })
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v("msg", "permission granted");
            viewModel.readMessages(this.contentResolver)
        }else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
                // user denied flagging NEVER ASK AGAIN
                AlertDialogUtil.showAlertDialog(this)
            }
            Log.v("msg", "permission not granted");

        }
    }

}