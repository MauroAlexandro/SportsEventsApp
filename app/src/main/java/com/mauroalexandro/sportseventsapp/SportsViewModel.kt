package com.mauroalexandro.sportseventsapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauroalexandro.sportseventsapp.models.Sports
import com.mauroalexandro.sportseventsapp.network.ApiClient
import com.mauroalexandro.sportseventsapp.network.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Mauro_Chegancas
 */
class SportsViewModel : ViewModel() {
    private val getSports = MutableLiveData<Resource<Sports>>()
    private var apiClient: ApiClient = ApiClient.getInstance()
    private var job: Job? = null

    /**
     * GET Collections
     */
    fun getCollectionsFromService() {
        job = CoroutineScope(Dispatchers.IO).launch {
            getSports.postValue(Resource.loading(null))

            apiClient.getClient()?.getSports()?.enqueue(object :
                Callback<Sports?> {
                override fun onResponse(
                    call: Call<Sports?>?,
                    response: Response<Sports?>
                ) {
                    val resource: Sports? = response.body()
                    getSports.postValue(Resource.success(resource))
                }

                override fun onFailure(call: Call<Sports?>, t: Throwable) {
                    Log.e("ERROR", "Error: " + t.message)
                    getSports.postValue(t.message?.let { Resource.error(it, null) })
                    call.cancel()
                }
            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun getSports(): MutableLiveData<Resource<Sports>> {
        return getSports
    }
}