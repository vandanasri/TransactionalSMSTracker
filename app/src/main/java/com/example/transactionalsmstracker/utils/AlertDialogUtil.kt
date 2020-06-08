package com.example.transactionalsmstracker.utils

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.transactionalsmstracker.R

class AlertDialogUtil {

    companion object {
        fun showAlertDialog(activity: Activity) {
            val builder: android.app.AlertDialog.Builder =
                android.app.AlertDialog.Builder(activity)
            builder.setMessage(R.string.permission_request_msg).setPositiveButton(
                activity.resources.getString(android.R.string.ok)
            ) { dialog, which ->
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.READ_SMS),
                    PermissionUtil.SMS_READ_PERMISSION_REQUEST
                )
            }.setNegativeButton(
                activity.resources.getString(android.R.string.cancel)
            ) { dialog, which ->
                Log.v("msg", "AlertDialog permission not granted")
                activity.finish()
            }.show()
        }
    }

}