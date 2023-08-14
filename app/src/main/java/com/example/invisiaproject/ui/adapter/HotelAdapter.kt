package com.example.invisiaproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.invisiaproject.databinding.ItemsHotelBinding
import com.example.invisiaproject.ui.Hotels

class HotelAdapter(val listener: HotelClickListener) :
    RecyclerView.Adapter<HotelAdapter.HotelViewHolder>(), Filterable {
    private var hotelList = ArrayList<Hotels>()
    private var hotelListFiltered = ArrayList<Hotels>()

    fun addData(hotlList: ArrayList<Hotels>) {
        hotelList = hotlList
        hotelListFiltered = hotlList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        return HotelViewHolder(
            ItemsHotelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.bind(hotelListFiltered[position])
        holder.itemView.setOnClickListener {
            hotelListFiltered[position].name?.let { it1 -> listener.onHotelItemClick(it1) }
        }
    }

    override fun getItemCount() = hotelListFiltered.size

    inner class HotelViewHolder(private val rawMoviesItemLayoutBinding: ItemsHotelBinding) :
        RecyclerView.ViewHolder(rawMoviesItemLayoutBinding.root) {

        fun bind(results: Hotels) {

            rawMoviesItemLayoutBinding.apply {
                hotelName.text = results.name
            }

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                hotelListFiltered = if (charString.isEmpty()) hotelList else {
                    val filteredList = ArrayList<Hotels>()
                    hotelList
                        .filter {
                            (it.name?.lowercase()?.contains(constraint!!) == true)

                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = hotelListFiltered }
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                if (results?.values == null) {
                    hotelListFiltered = ArrayList()
                    listener.isHotelDataAvailable(false)
                }
                else {
                    results.values as ArrayList<Hotels>
                    listener.isHotelDataAvailable(true)
                }
                notifyDataSetChanged()
            }
        }
    }
}

interface HotelClickListener {
    fun onHotelItemClick(name: String)
    fun isHotelDataAvailable(hasData: Boolean)
}