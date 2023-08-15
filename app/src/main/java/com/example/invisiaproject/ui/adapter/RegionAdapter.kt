package com.example.invisiaproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.invisiaproject.databinding.ItemsHotelBinding
import com.example.invisiaproject.network.model.Regions

class RegionAdapter(val listener: RegionClickListener) : RecyclerView.Adapter<RegionAdapter.HRViewHolder>(), Filterable {
    private var regionList = ArrayList<Regions>()
    private var regionListFiltered = ArrayList<Regions>()

    fun addData(hotlList: ArrayList<Regions>) {
        regionList = hotlList
        regionListFiltered = hotlList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HRViewHolder {
        return HRViewHolder(
            ItemsHotelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HRViewHolder, position: Int) {
        holder.bind(regionListFiltered[position])
        holder.itemView.setOnClickListener {
            regionListFiltered[position].name?.let { it1 -> listener.onRegionItemClick(it1) }
        }
    }

    override fun getItemCount() = regionListFiltered.size

    inner class HRViewHolder(private val rawMoviesItemLayoutBinding: ItemsHotelBinding) :
        RecyclerView.ViewHolder(rawMoviesItemLayoutBinding.root) {

        fun bind(results: Regions) {

            rawMoviesItemLayoutBinding.apply {
                hotelName.text = results.name
            }

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                regionListFiltered = if (charString.isEmpty()) regionList else {
                    val filteredList = ArrayList<Regions>()
                    regionList
                        .filter {
                            (it.name?.lowercase()?.contains(constraint!!) == true)

                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = regionListFiltered }
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                if (results?.values == null)
                    regionListFiltered = ArrayList()
                else
                    results.values as ArrayList<Regions>
                listener.isRegionDataAvailable(regionListFiltered.isEmpty())
                notifyDataSetChanged()
            }
        }
    }
}

interface RegionClickListener {
    fun onRegionItemClick(name: String)
    fun isRegionDataAvailable(hasData: Boolean)
}