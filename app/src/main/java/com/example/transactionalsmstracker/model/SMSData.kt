package com.example.transactionalsmstracker.model


//Data class to set and get SMS data
data class SMSData(

    var date : String,
    var sender : String,
    var messageText : String
)