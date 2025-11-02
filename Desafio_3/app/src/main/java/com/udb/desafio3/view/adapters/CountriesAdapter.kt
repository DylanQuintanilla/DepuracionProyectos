package com.udb.desafio3.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.udb.desafio3.R
import com.udb.desafio3.model.Country

class CountriesAdapter(
    private val countries: List<Country>,
    private val onCountryClick: (Country) -> Unit
) : RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFlag: ImageView = itemView.findViewById(R.id.ivFlag)
        val tvCountryName: TextView = itemView.findViewById(R.id.tvCountryName)
        val tvCapital: TextView = itemView.findViewById(R.id.tvCapital)

        fun bind(country: Country) {
            // Nombre del país
            tvCountryName.text = country.name.common

            // Capital del país (puede ser null o lista vacía)
            val capital = country.capital?.firstOrNull()
            tvCapital.text = if (!capital.isNullOrEmpty()) {
                "Capital: $capital"
            } else {
                "Capital: No disponible"
            }

            // Cargar bandera con Glide
            Glide.with(itemView.context)
                .load(country.flags.png)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ivFlag)

            // Click listener
            itemView.setOnClickListener {
                onCountryClick(country)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int = countries.size
}