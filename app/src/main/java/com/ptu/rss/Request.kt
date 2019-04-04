package com.ptu.rss

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Request {
    @GET("{path}")
    fun request(@Path("path") path: String): Call<ResponseBody>
}