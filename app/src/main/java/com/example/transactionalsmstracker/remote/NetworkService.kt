package com.example.transactionalsmstracker.remote

import com.example.transactionalsmstracker.model.SMSData
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkService {

    //Post query to send data to the server
    @POST(Endpoints.DATA_ENDPOINT)
    fun postSMSData(@Query("api_key") apiKey: String,
                         @Body listOfSMS: ArrayList<SMSData>): Single<String>
}