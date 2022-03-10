package com.examen.pmdm

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.examen.pmdm.databinding.ActivityFinalBinding

class ActivityFinal : AppCompatActivity(){

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityFinalBinding
    lateinit var adapter: AdapterFinal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.peticionApiPersonajes()
        initObserver()
        val pelicula = intent.getStringExtra("PELICULA")
        viewModel.isVisible.observe(this) { isVisible ->
            if (!isVisible){
                if (viewModel.items.value != null) {
                    llamadaRecycler(viewModel.items.value as MutableList<Personaje>,pelicula)
                }
            }
        }
    }

    private fun initObserver() {
        viewModel.isVisible.observe(this) { isVisible ->
            if (isVisible) setVisible() else setGone()
        }

        viewModel.responseText.observe(this) { responseText ->
            showToast(responseText)
        }
    }

    fun llamadaRecycler(lista: MutableList<Personaje>, pelicula: String?) {
        adapter = AdapterFinal(lista,pelicula)
        binding.recyclerview.layoutManager = LinearLayoutManager(this@ActivityFinal)
        binding.recyclerview.adapter = adapter
    }
    private fun setVisible() {
        binding.pbDownloading.visibility = View.VISIBLE
    }

    private fun setGone() {
        binding.pbDownloading.visibility = View.GONE
    }
    private fun showToast(text: String) {
        Toast.makeText(this@ActivityFinal, text, Toast.LENGTH_SHORT).show()
    }
}