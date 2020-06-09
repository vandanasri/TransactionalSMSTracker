package com.example.transactionalsmstracker.ui

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.transactionalsmstracker.R
import com.example.transactionalsmstracker.utils.AlertDialogUtil
import com.example.transactionalsmstracker.utils.PermissionUtil
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (PermissionUtil.requestReadSmsPermission(this,
                "This App will help you to filter finance related SMS from your phone. So, please allow this app to read your SMS",
                DialogInterface.OnClickListener { dialog, which ->
                    // YOUR CANCEL CODE
                })
        ) {

            readMessages()
            // YOUR BASE METHOD
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v("msg", "permission granted");
            readMessages()
        }else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
                // user denied flagging NEVER ASK AGAIN
                AlertDialogUtil.showAlertDialog(this)
            }
            Log.v("msg", "permission not granted");

        }
    }


    //Method to read transactional Sms
    private fun readMessages() {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val cursor: Cursor? = this.contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) { // must check the result to prevent exception
                do {
                    val msgData: String = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString()
                    val date: String = cursor.getString(cursor.getColumnIndexOrThrow("date")).toString()
                    val add: String = cursor.getString(2)
                   val dateVal = formatter.format(Date(date.toLong()))
                    if (msgData.contains("credited") || msgData.contains("debited") || msgData.contains(
                            "withdrawn"
                        )
                    ) {
                        Log.v("Date Value",dateVal + " Msg:" + msgData + "Msg from: " + add)

                    }
                } while (cursor.moveToNext())
            } else {
               Toast.makeText(this,"Inbox is empty, no SMS", Toast.LENGTH_SHORT).show()
            }
        }
    }


}