package com.example.android_proyecto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EliminarProyectoAdapter(
    private val context: Context,
    private val proyectos: MutableList<Proyecto>,
    private val onEliminarClick: (Proyecto) -> Unit
                             ) : RecyclerView.Adapter<EliminarProyectoAdapter.ProyectoViewHolder>() {

    inner class ProyectoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreProyecto: TextView = view.findViewById(R.id.tvNombreProyecto)
        val descripcionProyecto: TextView = view.findViewById(R.id.tvDescripcionProyecto)
        val estadoProyecto: TextView = view.findViewById(R.id.tvEstadoProyecto)
        val fechaProyecto: TextView = view.findViewById(R.id.tvFechaProyecto)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProyectoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_proyecto, parent, false)
        return ProyectoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProyectoViewHolder, position: Int) {
        val proyecto = proyectos[position]
        holder.nombreProyecto.text = proyecto.titulo
        holder.descripcionProyecto.text = proyecto.descripcion
        holder.estadoProyecto.text = "Estado: ${proyecto.estado}"
        holder.fechaProyecto.text = proyecto.fecha

        holder.btnEliminar.setOnClickListener {
            onEliminarClick(proyecto)
        }
    }

    override fun getItemCount(): Int = proyectos.size

    fun actualizarLista(nuevaLista: MutableList<Proyecto>) {
        proyectos.clear()
        proyectos.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}
