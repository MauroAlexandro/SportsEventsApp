package com.mauroalexandro.sportseventsapp.network

import com.mauroalexandro.sportseventsapp.models.Sports
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Mauro_Chegancas
 */
interface ApiEndpointInterface {
    @GET("sports/")
    fun getSports(): Call<Sports>
}