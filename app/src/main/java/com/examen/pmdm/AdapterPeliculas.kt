package com.examen.pmdm

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.examen.pmdm.databinding.ItemstarwarsBinding

class AdapterPeliculas(var lista: MutableList<Peliculas>, var personaje: String?) :
    RecyclerView.Adapter<AdapterPeliculas.TextoViewHolder>() {

    class TextoViewHolder(var itemBinding: ItemstarwarsBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextoViewHolder {
        val binding = ItemstarwarsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextoViewHolder, position: Int) {
        holder.itemBinding.nombre.text = "Nombre : ${lista[position].title}"
        holder.itemBinding.cl1.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
        lista[position].characters.forEach {
            if (it == personaje){
                holder.itemBinding.cl1.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
            }
        }
        holder.itemBinding.cl1
        holder.itemBinding.cl1.setOnClickListener {
            val intent = Intent(holder.itemView.context, ActivityFinal::class.java)
            intent.putExtra("PELICULA", lista[position].url)
            holder.itemView.context.startActivities(arrayOf(intent))
        }
    }
}