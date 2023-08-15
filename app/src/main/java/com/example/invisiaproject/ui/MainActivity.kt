package com.example.invisiaproject.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.invisiaproject.databinding.ActivityMainBinding
import com.example.invisiaproject.ui.adapter.HotelAdapter
import com.example.invisiaproject.ui.adapter.HotelClickListener
import com.example.invisiaproject.ui.adapter.RegionAdapter
import com.example.invisiaproject.ui.adapter.RegionClickListener
import com.example.invisiaproject.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), HotelClickListener, RegionClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var regionAdapter: RegionAdapter
    private lateinit var hotelAdapter: HotelAdapter
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this
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
        mainViewModel.responseContainer.observe(this, Observer {
            if (it?.body != null) {
                it.body!!.regions?.let { it1 -> regionAdapter.addData(it1) }
                it.body!!.hotels?.let { it1 -> hotelAdapter.addData(it1) }

                regionAdapter.notifyDataSetChanged()
                hotelAdapter.notifyDataSetChanged()
            }
        })

        mainViewModel.errorMessage.observe(this, Observer {
            showToast(it)
        })
    }

    override fun onHotelItemClick(name: String) {
        showToast(name)

    }

    override fun isHotelDataAvailable(hasData: Boolean) {
        mainViewModel.isHotelDataFound.value = hasData
    }

    private fun showToast(name: String) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
    }

    override fun onRegionItemClick(name: String) {
        showToast(name)
    }

    override fun isRegionDataAvailable(hasData: Boolean) {
        mainViewModel.isRegionDataFound.value = hasData
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}