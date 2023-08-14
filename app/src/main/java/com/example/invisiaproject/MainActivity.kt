package com.example.invisiaproject

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.invisiaproject.databinding.ActivityMainBinding
import com.example.invisiaproject.ui.MainViewModel
import com.example.invisiaproject.ui.adapter.HotelAdapter
import com.example.invisiaproject.ui.adapter.HotelClickListener
import com.example.invisiaproject.ui.adapter.RegionAdapter
import com.example.invisiaproject.ui.adapter.RegionClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), HotelClickListener, RegionClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var regionAdapter: RegionAdapter
    private lateinit var hotelAdapter: HotelAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpRegionRecyclerView()
        setUpHotelRecyclerView()
        mainViewModel.getMoviesFromAPI()

        editTextWatcher(binding.searchAnyName)

    }

    private fun editTextWatcher(editTextField: EditText) {
        editTextField.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                regionAdapter.filter.filter(s.toString())
                hotelAdapter.filter.filter(s.toString())

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun setUpRegionRecyclerView() = binding.regionRV.apply {
        this.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                LinearLayoutManager.VERTICAL
            )
        )
        regionAdapter = RegionAdapter(this@MainActivity)
        adapter = regionAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun setUpHotelRecyclerView() = binding.hotelRV.apply {
        this.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                LinearLayoutManager.VERTICAL
            )
        )
        hotelAdapter = HotelAdapter(this@MainActivity)
        adapter = hotelAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun setUpViewModel() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.responseContainer.observe(this, Observer {
            if (it?.body != null) {
                it.body!!.regions?.let { it1 -> regionAdapter.addData(it1) }
                regionAdapter.notifyDataSetChanged()
            }
        })

        mainViewModel.responseContainer.observe(this, Observer {
            if (it?.body != null) {
                it.body!!.hotels?.let { it1 -> hotelAdapter.addData(it1) }
                hotelAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onHotelItemClick(name: String) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show()

    }

    override fun onRegionItemClick(name: String) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
    }


}