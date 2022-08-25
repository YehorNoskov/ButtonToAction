package com.innovecs.test.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://s3-us-west-2.amazonaws.com/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val actionApi: ButtonToActionApi = retrofit.create(ButtonToActionApi::class.java)

