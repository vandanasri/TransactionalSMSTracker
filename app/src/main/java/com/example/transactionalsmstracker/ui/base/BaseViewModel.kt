package com.example.transactionalsmstracker.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.transactionalsmstracker.R
import com.example.transactionalsmstracker.utils.Resource
import com.example.transactionalsmstracker.utils.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable
import javax.net.ssl.HttpsURLConnection


//This class extends the android view model
abstract class BaseViewModel(protected val compositeDisposable: CompositeDisposable,
                             protected val networkHelper: NetworkHelper
) : ViewModel(){


    val messageStringId = MutableLiveData<Resource<Int>>()
    val messageString = MutableLiveData<Resource<String>>()


    //Method to check the internet connection and show message if not connected
    protected fun checkInternetConnectionWithMessage(): Boolean =
        if (networkHelper.isNetworkConnected()) {
            true
        } else {
            messageStringId.postValue(Resource.error(R.string.network_connection_error))
            false
        }

    //method to call networkHelper network connection which return boolean according to the network status
    protected fun checkInternetConnection() : Boolean = networkHelper.isNetworkConnected()


    //to handle the all the state of network error
    protected fun handleNetworkError(err : Throwable?){
        err?.let {
            networkHelper.castToNetworkError(it).run {
                println("statusCode ${it.localizedMessage}")
                when (status) {
                    -1 -> messageStringId.postValue(Resource.error(R.string.network_default_error))
                    0 -> messageStringId.postValue(Resource.error(R.string.server_connection_error))
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                        messageStringId.postValue(Resource.error(R.string.server_connection_error))
                    }
                    HttpsURLConnection.HTTP_INTERNAL_ERROR ->
                        messageStringId.postValue(Resource.error(R.string.network_internal_error))
                    HttpsURLConnection.HTTP_UNAVAILABLE ->
                        messageStringId.postValue(Resource.error(R.string.network_server_not_available))
                    else -> messageString.postValue(Resource.error(message))
                }
            }
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    abstract fun onCreate()

}