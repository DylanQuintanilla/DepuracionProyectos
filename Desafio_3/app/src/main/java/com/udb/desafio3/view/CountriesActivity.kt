package com.udb.desafio3.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udb.desafio3.R
import com.udb.desafio3.controller.RetrofitClient
import com.udb.desafio3.model.Country
import com.udb.desafio3.view.adapters.CountriesAdapter
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CountriesActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var tvToolbarTitle: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView
    private var regionName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)

        regionName = intent.getStringExtra("REGION") ?: ""

        initViews()
        setupToolbar()
        setupRecyclerView()
        loadCountries()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle)
        recyclerView = findViewById(R.id.recyclerViewCountries)
        progressBar = findViewById(R.id.progressBar)
        tvError = findViewById(R.id.tvError)
    }

    private fun setupToolbar() {
        tvToolbarTitle.text = regionName
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        // Usar GridLayoutManager para mostrar en grid (opcional, puedes dejarlo en lista)
        // Si quieres 2 columnas en tablets:
        val spanCount = if (resources.configuration.screenWidthDp >= 600) 2 else 1
        recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        recyclerView.setHasFixedSize(true)
    }

    private fun loadCountries() {
        showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.countriesApi.getCountriesByRegion(regionName)

                withContext(Dispatchers.Main) {
                    showLoading(false)

                    if (response.isSuccessful && response.body() != null) {
                        val countries = response.body()!!

                        if (countries.isEmpty()) {
                            showError("No se encontraron países en esta región")
                        } else {
                            val sortedCountries = countries.sortedBy { it.name.common }
                            displayCountries(sortedCountries)
                        }
                    } else {
                        val errorMessage = when (response.code()) {
                            404 -> "Región no encontrada"
                            500 -> "Error del servidor"
                            else -> "Error al cargar países: ${response.code()}"
                        }
                        showError(errorMessage)
                    }
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    showError("Tiempo de espera agotado. Verifica tu conexión.")
                    Toast.makeText(
                        this@CountriesActivity,
                        "Timeout: Verifica tu conexión a Internet",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: UnknownHostException) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    showError("Sin conexión a Internet")
                    Toast.makeText(
                        this@CountriesActivity,
                        "No hay conexión a Internet",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    showError("Error: ${e.localizedMessage ?: "Error desconocido"}")
                    Toast.makeText(
                        this@CountriesActivity,
                        "Error: ${e.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun displayCountries(countries: List<Country>) {
        val adapter = CountriesAdapter(countries) { country ->
            openCountryDetail(country)
        }
        recyclerView.adapter = adapter
    }

    private fun openCountryDetail(country: Country) {
        val intent = Intent(this, CountryDetailActivity::class.java)
        intent.putExtra("COUNTRY_JSON", Gson().toJson(country))
        startActivity(intent)
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        recyclerView.visibility = if (show) View.GONE else View.VISIBLE
        tvError.visibility = View.GONE
    }

    private fun showError(message: String) {
        tvError.text = message
        tvError.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
    }
}