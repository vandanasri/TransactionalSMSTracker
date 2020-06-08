package com.example.transactionalsmstracker.utils

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionUtil {

    companion object {
        val SMS_READ_PERMISSION_REQUEST = 0


        fun requestReadSmsPermission(
            activity: Activity,
            messagePermission: String,
            onCancelListener: DialogInterface.OnClickListener
        ): Boolean {

            var result: Boolean = false;
            //Check if we have SMS permission
            if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.READ_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                //Request runtime SMS permission
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.READ_SMS
                    )
                ) {
                    // This thread waiting for the user's response! After the user
                    // Try again to request the permission.
                    AlertDialogUtil.showAlertDialog(activity)
                } else {
                    //Request runtime SMS permission
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.READ_SMS),
                        SMS_READ_PERMISSION_REQUEST
                    )
                }
            } else {
                result = true;
            }
            return result;
        }
    }

}