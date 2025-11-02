package com.udb.desafio3.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.udb.desafio3.R

class RegionsAdapter(
    private val regions: List<String>,
    private val onRegionClick: (String) -> Unit
) : RecyclerView.Adapter<RegionsAdapter.RegionViewHolder>() {

    inner class RegionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardBackground: ConstraintLayout = itemView.findViewById(R.id.cardBackground)
        val tvRegionName: TextView = itemView.findViewById(R.id.tvRegionName)
        val tvRegionDescription: TextView = itemView.findViewById(R.id.tvRegionDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_region, parent, false)
        return RegionViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        val region = regions[position]
        holder.tvRegionName.text = region
        holder.tvRegionDescription.text = "Toca para explorar"

        // Asignar color según la región
        val colorRes = when (region) {
            "Africa" -> R.color.region_africa
            "Americas" -> R.color.region_americas
            "Asia" -> R.color.region_asia
            "Europe" -> R.color.region_europe
            "Oceania" -> R.color.region_oceania
            else -> R.color.accent
        }

        val color = ContextCompat.getColor(holder.itemView.context, colorRes)
        holder.cardBackground.setBackgroundColor(color)

        holder.itemView.setOnClickListener {
            onRegionClick(region)
        }
    }

    override fun getItemCount() = regions.size
}