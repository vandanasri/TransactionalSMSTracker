package com.example.transactionalsmstracker.ui

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.transactionalsmstracker.model.SMSData
import com.example.transactionalsmstracker.remote.PostSMSDataRepo
import com.example.transactionalsmstracker.ui.base.BaseViewModel
import com.example.transactionalsmstracker.utils.Resource
import com.example.transactionalsmstracker.utils.Status
import com.example.transactionalsmstracker.utils.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivityViewModel(compositeDisposable: CompositeDisposable,
                            networkHelper: NetworkHelper,
    private val postSMSDataRepo: PostSMSDataRepo)
    :BaseViewModel(compositeDisposable, networkHelper) {

    val loadingProgressBar = MutableLiveData<Resource<Status>>()
    val smsData = MutableLiveData<ArrayList<SMSData>>()



    //Method to read transactional Sms
    fun readMessages(contentResolver: ContentResolver) {
        val listOfSMS = ArrayList<SMSData>()
        loadingProgressBar.postValue(Resource.loading())
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val cursor: Cursor? = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) { // must check the result to prevent exception
                do {
                    val msgData: String = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString()
                    val date: String = cursor.getString(cursor.getColumnIndexOrThrow("date")).toString()
                    val add: String = cursor.getString(2)
                    val dateVal = formatter.format(Date(date.toLong()))
                    if (msgData.contains("credited") || msgData.contains("debited") || msgData.contains("withdrawn"))
                    {
                        Log.v("Date Value",dateVal + " Msg:" + msgData + "Msg from: " + add)
                        listOfSMS.add(SMSData(dateVal, add, msgData))
                    }
                } while (cursor.moveToNext())
            } else {
                loadingProgressBar.postValue(Resource.error())
            }
            loadingProgressBar.postValue(Resource.success())
            smsData.postValue(listOfSMS)
            cursor.close()
        }
        else{
            loadingProgressBar.postValue(Resource.error())
        }
    }


    //method to post sms data to server
    fun postSMSData(apiKey : String, listOfSMS : ArrayList<SMSData>)
    {
        if (checkInternetConnection()) {
            loadingProgressBar.postValue(Resource.loading())
            compositeDisposable.add(
                postSMSDataRepo.postSMSData(apiKey, listOfSMS)
                    .subscribeOn(Schedulers.io())
                    .doOnSuccess {

                    }
                    .subscribe(
                        {
                            loadingProgressBar.postValue(Resource.success())
                        },
                        {
                            loadingProgressBar.postValue(Resource.error())
                            handleNetworkError(it)
                        })

            )
        }
    }


    override fun onCreate() {}


}