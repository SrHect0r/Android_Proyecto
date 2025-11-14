package com.example.android_proyecto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProyectoAdapter(
    private val proyectos: List<Proyecto>,
    private val onClick: (Proyecto) -> Unit
                     ) : RecyclerView.Adapter<ProyectoAdapter.ProyectoViewHolder>() {

    inner class ProyectoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo = view.findViewById<TextView>(R.id.tvNombreProyecto)
        val descripcion = view.findViewById<TextView>(R.id.tvDescripcionProyecto)
        val estado = view.findViewById<TextView>(R.id.tvEstadoProyecto)
        val fecha = view.findViewById<TextView>(R.id.tvFechaProyecto)
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
        holder.fecha.text = proyecto.fecha

        holder.itemView.setOnClickListener { onClick(proyecto) }
    }

    override fun getItemCount() = proyectos.size
}
