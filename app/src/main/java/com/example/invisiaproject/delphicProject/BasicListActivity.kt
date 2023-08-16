package com.example.invisiaproject.delphicProject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.invisiaproject.R
import com.example.myapplication.delphicProject.DummyData
import kotlin.collections.ArrayList

class BasicListActivity: AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var adapter: BasicListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_list)

        setUpAdapter()


    }

    private fun setUpAdapter() {
        rv = findViewById(R.id.rv)
        adapter = BasicListAdapter(getList())
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.adapter = adapter
    }

    private fun getList(): ArrayList<DummyData> {

        var list = ArrayList<DummyData>()
        list.add(DummyData("shankar", "1234567890"))
        list.add(DummyData("shankar", "1234567890"))
        list.add(DummyData("shankar", "1234567890"))
        list.add(DummyData("shankar", "1234567890"))
        list.add(DummyData("shankar", "1234567890"))
        list.add(DummyData("shankar", "1234567890"))
        list.add(DummyData("shankar", "1234567890"))

        return list

    }

}