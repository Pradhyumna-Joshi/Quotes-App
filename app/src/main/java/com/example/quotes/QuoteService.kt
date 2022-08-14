package com.example.quotes

import com.example.quotes.Model.Quote
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://type.fit/"

interface QuoteInterface {

    @GET("api/quotes")
    fun getQuotes(): Call<ArrayList<Quote>>

}

object QuoteService {

    val quoteInstance: QuoteInterface

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        quoteInstance = retrofit.create(QuoteInterface::class.java)
    }

}

