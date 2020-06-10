package com.example.transactionalsmstracker.remote

import com.example.transactionalsmstracker.model.SMSData
import io.reactivex.Single
import javax.inject.Inject


//Data repository to communicate with API call or local DB
class PostSMSDataRepo @Inject constructor(private val networkService: NetworkService
) {

    fun postSMSData(apiKey: String, listOfSMS: ArrayList<SMSData>) : Single<String> =
        networkService.postSMSData(apiKey, listOfSMS)
}