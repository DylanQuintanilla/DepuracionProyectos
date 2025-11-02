package com.udb.desafio3.controller

import com.udb.desafio3.model.Country
import com.udb.desafio3.model.WeatherResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

// Interface para RestCountries API
interface CountriesApiService {
    @GET("v3.1/all")
    suspend fun getAllCountries(): Response<List<Country>>

    @GET("v3.1/region/{region}")
    suspend fun getCountriesByRegion(@Path("region") region: String): Response<List<Country>>
}

// Interface para Weather API
interface WeatherApiService {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ): Response<WeatherResponse>
}

// Objeto singleton para crear instancias de Retrofit
object RetrofitClient {

    private const val COUNTRIES_BASE_URL = "https://restcountries.com/"
    private const val WEATHER_BASE_URL = "https://api.weatherapi.com/v1/"

    // Tu API Key de WeatherAPI (regístrate en weatherapi.com para obtenerla)
    const val WEATHER_API_KEY = "2f18cd3edeb94e1690912402252910"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val countriesApi: CountriesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(COUNTRIES_BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApiService::class.java)
    }

    val weatherApi: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }
}