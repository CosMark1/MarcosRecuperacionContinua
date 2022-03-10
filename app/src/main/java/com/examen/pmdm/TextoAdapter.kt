package com.examen.pmdm

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examen.pmdm.databinding.ItemstarwarsBinding

class TextoAdapter(var lista: MutableList<Personaje>) :
    RecyclerView.Adapter<TextoAdapter.TextoViewHolder>() {

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
        holder.itemBinding.cl1.setOnClickListener {
            val intent = Intent(holder.itemView.context, ActivityPeliculas::class.java)
            intent.putExtra("PERSONAJE", lista[position].url)
            holder.itemView.context.startActivities(arrayOf(intent))
        }
    }
}