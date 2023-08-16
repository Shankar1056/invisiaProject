package com.example.invisiaproject.delphicProject

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.invisiaproject.R
import com.example.myapplication.delphicProject.DummyData

class BasicListAdapter(val list: ArrayList<DummyData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val ItemsViewModel = list[position]

       // holder.name.text = ItemsViewModel.name

        Log.i("", "name:::${ItemsViewModel.name}")

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.name)
    }
}