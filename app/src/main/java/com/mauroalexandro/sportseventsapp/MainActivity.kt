package com.mauroalexandro.sportseventsapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mauroalexandro.sportseventsapp.adapters.SportsRecyclerViewAdapter
import com.mauroalexandro.sportseventsapp.databinding.ActivityMainBinding
import com.mauroalexandro.sportseventsapp.models.Sports
import com.mauroalexandro.sportseventsapp.network.Status

class MainActivity : AppCompatActivity() {
    private lateinit var sportsViewModel: SportsViewModel
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var sportsRecyclerViewAdapter: SportsRecyclerViewAdapter
    private lateinit var sports: Sports

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        sportsViewModel = ViewModelProvider(this)[SportsViewModel::class.java]
        sportsRecyclerViewAdapter = SportsRecyclerViewAdapter(this)

        setViewModel()
        sportsViewModel.getCollectionsFromService()

        val view = binding.root
        setContentView(view)
    }

    private fun setViewModel() {
        //Get Sports
        this.let { it ->
            sportsViewModel.getSports().observe(it, {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        it.data?.let { sports -> this.sports = sports
                            sportsRecyclerViewAdapter.setSportsList(sports)
                        }
                        binding.sportsRecyclerview.visibility = View.VISIBLE
                        binding.sportsRecyclerview.adapter = sportsRecyclerViewAdapter
                        binding.sportsRecyclerview.layoutManager =
                            LinearLayoutManager(this)
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.sportsRecyclerview.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}