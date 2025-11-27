package com.example.android_proyecto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProyectosAdapter(
    private val listaProyectos: List<Proyecto>,
    private val usuarioId: String,              // ID del usuario logueado
    private val onItemClick: (Proyecto) -> Unit
                      ) : RecyclerView.Adapter<ProyectosAdapter.ProyectoViewHolder>() {

    class ProyectoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreProyecto)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcionProyecto)
        val tvNumTareas: TextView = itemView.findViewById(R.id.tvNumTareas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProyectoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_proyecto, parent, false)
        return ProyectoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProyectoViewHolder, position: Int) {
        val proyecto = listaProyectos[position]

        holder.tvNombre.text = proyecto.nombre
        holder.tvDescripcion.text = proyecto.descripcion

        // Contar tareas asignadas al usuario
        val tareasUsuario = proyecto.tareas.filter { it.usuariosAsignados.contains(usuarioId) }
        if (tareasUsuario.isNotEmpty()) {
            holder.tvNumTareas.visibility = View.VISIBLE
            holder.tvNumTareas.text = "${tareasUsuario.size} tareas asignadas"
        } else {
            holder.tvNumTareas.visibility = View.GONE
        }

        // Click en proyecto → abrir detalle
        holder.itemView.setOnClickListener {
            onItemClick(proyecto)
        }
    }

    override fun getItemCount(): Int = listaProyectos.size
}
