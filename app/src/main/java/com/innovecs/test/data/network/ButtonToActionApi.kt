package com.innovecs.test.data.network

import com.innovecs.test.data.ButtonToActionResponse
import retrofit2.Response
import retrofit2.http.GET

interface ButtonToActionApi {
    //todo maybe should be button_to_action_config instead of butto_to_action_config?
    @GET("/androidexam/butto_to_action_config.json")
    suspend fun getActions(): Response<List<ButtonToActionResponse>>
}