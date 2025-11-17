package com.example.android_proyecto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProyectoAdapter(
    private val proyectos: MutableList<Proyecto>, // mutable para poder actualizar
    private val onClick: (Proyecto) -> Unit
                     ) : RecyclerView.Adapter<ProyectoAdapter.ProyectoViewHolder>() {

    inner class ProyectoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.tvNombreProyecto)
        val descripcion: TextView = view.findViewById(R.id.tvDescripcionProyecto)
        val estado: TextView = view.findViewById(R.id.tvEstadoProyecto)
        val fecha: TextView = view.findViewById(R.id.tvFechaProyecto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProyectoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_proyecto, parent, false)
        return ProyectoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProyectoViewHolder, position: Int) {
        val proyecto = proyectos[position]
        holder.titulo.text = proyecto.titulo
        holder.descripcion.text = proyecto.descripcion
        holder.estado.text = "Estado: ${proyecto.estado}"
        holder.fecha.text = "Fecha límite: ${proyecto.fecha}"

        holder.itemView.setOnClickListener { onClick(proyecto) }
    }

    override fun getItemCount(): Int = proyectos.size

    // Método para actualizar la lista de proyectos y refrescar el RecyclerView
    fun actualizarLista(nuevaLista: List<Proyecto>?) {
        proyectos.clear()
        if (nuevaLista != null) {
            proyectos.addAll(nuevaLista)
        }
        notifyDataSetChanged()
    }
}
