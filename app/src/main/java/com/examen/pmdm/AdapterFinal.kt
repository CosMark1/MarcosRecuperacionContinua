package com.examen.pmdm

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.examen.pmdm.databinding.ItemstarwarsBinding

class AdapterFinal(var lista: MutableList<Personaje>, var pelicula: String?) :
    RecyclerView.Adapter<AdapterFinal.TextoViewHolder>() {

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
        holder.itemBinding.nombre.text = "Nombre : ${lista[position].name}"
        holder.itemBinding.cl1.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
        lista[position].films.forEach {
            if (it ==  pelicula){
                holder.itemBinding.cl1.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
            }
        }
    }
}