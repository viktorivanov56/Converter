package com.example.converter.data

import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    @GET("{apiKey}/latest/{base}")
    suspend fun getRates(
        @Path("apiKey") apiKey: String,
        @Path("base") base: String
    ): CurrencyResponse
}



