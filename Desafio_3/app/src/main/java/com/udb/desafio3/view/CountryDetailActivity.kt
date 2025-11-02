package com.udb.desafio3.view

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.udb.desafio3.R
import com.udb.desafio3.controller.RetrofitClient
import com.udb.desafio3.model.Country
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.NumberFormat
import java.util.Locale

class CountryDetailActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var ivCountryFlag: ImageView
    private lateinit var tvCountryName: TextView
    private lateinit var tvOfficialName: TextView

    // Items de detalle
    private lateinit var itemCapital: LinearLayout
    private lateinit var itemRegion: LinearLayout
    private lateinit var itemSubregion: LinearLayout
    private lateinit var itemPopulation: LinearLayout

    private lateinit var tvCodes: TextView
    private lateinit var tvCurrencies: TextView
    private lateinit var tvLanguages: TextView
    private lateinit var tvCoordinates: TextView

    // Views del clima
    private lateinit var cardWeather: MaterialCardView
    private lateinit var progressBarWeather: ProgressBar
    private lateinit var tvWeatherError: TextView
    private lateinit var ivWeatherIcon: ImageView
    private lateinit var tvWeatherCondition: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvWind: TextView
    private lateinit var tvHumidity: TextView

    private var country: Country? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)

        initViews()

        val countryJson = intent.getStringExtra("COUNTRY_JSON")
        country = Gson().fromJson(countryJson, Country::class.java)

        btnBack.setOnClickListener {
            finish()
        }

        country?.let {
            displayCountryInfo(it)
            loadWeather(it)
        } ?: run {
            Toast.makeText(this, "Error al cargar información del país", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        ivCountryFlag = findViewById(R.id.ivCountryFlag)
        tvCountryName = findViewById(R.id.tvCountryName)
        tvOfficialName = findViewById(R.id.tvOfficialName)

        itemCapital = findViewById(R.id.itemCapital)
        itemRegion = findViewById(R.id.itemRegion)
        itemSubregion = findViewById(R.id.itemSubregion)
        itemPopulation = findViewById(R.id.itemPopulation)

        tvCodes = findViewById(R.id.tvCodes)
        tvCurrencies = findViewById(R.id.tvCurrencies)
        tvLanguages = findViewById(R.id.tvLanguages)
        tvCoordinates = findViewById(R.id.tvCoordinates)

        cardWeather = findViewById(R.id.cardWeather)
        progressBarWeather = findViewById(R.id.progressBarWeather)
        tvWeatherError = findViewById(R.id.tvWeatherError)
        ivWeatherIcon = findViewById(R.id.ivWeatherIcon)
        tvWeatherCondition = findViewById(R.id.tvWeatherCondition)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvWind = findViewById(R.id.tvWind)
        tvHumidity = findViewById(R.id.tvHumidity)
    }

    private fun displayCountryInfo(country: Country) {
        tvCountryName.text = country.name.common
        tvOfficialName.text = country.name.official

        // Llenar items de detalle
        setDetailItem(itemCapital, "CAPITAL", country.capital?.firstOrNull() ?: "N/A")
        setDetailItem(itemRegion, "REGIÓN", country.region)
        setDetailItem(itemSubregion, "SUBREGIÓN", country.subregion ?: "N/A")

        val formatter = NumberFormat.getNumberInstance(Locale.US)
        val formattedPopulation = "${formatter.format(country.population)} habitantes"
        setDetailItem(itemPopulation, "POBLACIÓN", formattedPopulation)

        // Códigos ISO con emoji
        tvCodes.text = "🏷️ ${country.cca2} / ${country.cca3}"

        // Monedas con emoji
        val currencies = country.currencies?.values?.joinToString(", ") { currency ->
            "${currency.name}${currency.symbol?.let { " ($it)" } ?: ""}"
        } ?: "N/A"
        tvCurrencies.text = "💵 $currencies"

        // Idiomas con emoji
        val languages = country.languages?.values?.joinToString(", ") ?: "N/A"
        tvLanguages.text = "🗣️ $languages"

        // Coordenadas con emoji
        val coordinates = country.latlng?.let { latlng ->
            if (latlng.size >= 2) {
                "${String.format("%.4f", latlng[0])}, ${String.format("%.4f", latlng[1])}"
            } else {
                "N/A"
            }
        } ?: "N/A"
        tvCoordinates.text = "📍 $coordinates"

        // Bandera
        Glide.with(this)
            .load(country.flags.png)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(ivCountryFlag)
    }

    private fun setDetailItem(layout: LinearLayout, label: String, value: String) {
        val tvLabel = layout.findViewById<TextView>(R.id.tvLabel)
        val tvValue = layout.findViewById<TextView>(R.id.tvValue)
        tvLabel.text = label
        tvValue.text = value
    }

    private fun loadWeather(country: Country) {
        val capital = country.capital?.firstOrNull()

        if (capital.isNullOrEmpty()) {
            showWeatherError("Este país no tiene capital registrada")
            return
        }

        showWeatherLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.weatherApi.getCurrentWeather(
                    RetrofitClient.WEATHER_API_KEY,
                    capital
                )

                withContext(Dispatchers.Main) {
                    showWeatherLoading(false)

                    if (response.isSuccessful && response.body() != null) {
                        val weather = response.body()!!
                        displayWeather(weather)
                    } else {
                        val errorMsg = when (response.code()) {
                            401 -> "API Key inválida o no configurada"
                            403 -> "Acceso denegado. Verifica tu API Key"
                            404 -> "Ciudad no encontrada en la base de datos"
                            else -> "Error ${response.code()}: No se pudo obtener el clima"
                        }
                        showWeatherError(errorMsg)
                    }
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    showWeatherLoading(false)
                    showWeatherError("Tiempo de espera agotado al consultar clima")
                }
            } catch (e: UnknownHostException) {
                withContext(Dispatchers.Main) {
                    showWeatherLoading(false)
                    showWeatherError("Sin conexión a Internet")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showWeatherLoading(false)
                    showWeatherError("Error: ${e.localizedMessage ?: "Error desconocido"}")
                }
            }
        }
    }

    private fun displayWeather(weather: com.udb.desafio3.model.WeatherResponse) {
        cardWeather.visibility = View.VISIBLE

        tvWeatherCondition.text = weather.current.condition.text
        tvTemperature.text = "${weather.current.tempC}°C / ${weather.current.tempF}°F"
        tvWind.text = "💨 ${weather.current.windKph} km/h"
        tvHumidity.text = "💧 ${weather.current.humidity}%"

        val iconUrl = if (weather.current.condition.icon.startsWith("//")) {
            "https:${weather.current.condition.icon}"
        } else {
            weather.current.condition.icon
        }

        Glide.with(this)
            .load(iconUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(ivWeatherIcon)
    }

    private fun showWeatherLoading(show: Boolean) {
        progressBarWeather.visibility = if (show) View.VISIBLE else View.GONE
        cardWeather.visibility = if (show) View.GONE else View.VISIBLE
        tvWeatherError.visibility = View.GONE
    }

    private fun showWeatherError(message: String) {
        tvWeatherError.text = message
        tvWeatherError.visibility = View.VISIBLE
        cardWeather.visibility = View.GONE
        progressBarWeather.visibility = View.GONE
    }
}