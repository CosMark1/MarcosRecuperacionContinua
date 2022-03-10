package com.examen.pmdm

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.examen.pmdm.databinding.ActivityPeliculasBinding

class ActivityPeliculas : AppCompatActivity(){

    private val viewModel: PeliculasActivityViewModel by viewModels()
    private lateinit var binding: ActivityPeliculasBinding
    lateinit var adapter: AdapterPeliculas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeliculasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.peticionApiPeliculas()
        initObserver()
        val personaje = intent.getStringExtra("PERSONAJE")
        viewModel.isVisible.observe(this) { isVisible ->
            if (!isVisible){
                if (viewModel.itemsPeliculas.value != null) {
                    llamadaRecycler(viewModel.itemsPeliculas.value as MutableList<Peliculas>,personaje)
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

    fun llamadaRecycler(lista: MutableList<Peliculas>, personaje: String?) {
        adapter = AdapterPeliculas(lista,personaje)
        binding.recyclerview.layoutManager = LinearLayoutManager(this@ActivityPeliculas)
        binding.recyclerview.adapter = adapter
    }
    private fun setVisible() {
        binding.pbDownloading.visibility = View.VISIBLE
    }

    private fun setGone() {
        binding.pbDownloading.visibility = View.GONE
    }
    private fun showToast(text: String) {
        Toast.makeText(this@ActivityPeliculas, text, Toast.LENGTH_SHORT).show()
    }
}