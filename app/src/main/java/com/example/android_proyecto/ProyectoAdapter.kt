package com.example.android_proyecto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProyectoAdapter(
    private var proyectos: MutableList<Proyecto>,
    private val onClick: (String) -> Unit // Recibirá JSON del proyecto
                     ) : RecyclerView.Adapter<ProyectoAdapter.ProyectoViewHolder>() {

    inner class ProyectoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.tvNombreProyecto)
        val descripcion: TextView = view.findViewById(R.id.tvDescripcionProyecto)
        val estado: TextView = view.findViewById(R.id.tvEstadoProyecto)
        val fecha: TextView = view.findViewById(R.id.tvFechaProyecto)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProyectoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_proyecto, parent, false)
        return ProyectoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProyectoViewHolder, position: Int) {
        val proyecto = proyectos[position]

        // Asignar datos
        holder.titulo.text = proyecto.titulo
        holder.descripcion.text = proyecto.descripcion
        holder.estado.text = "Estado: ${proyecto.estado}"
        holder.fecha.text = "Fecha límite: ${proyecto.fecha}"

        // Click en la tarjeta → abrir detalle pasando JSON
        holder.itemView.setOnClickListener {
            val gson = Gson()
            val proyectoJson = gson.toJson(proyecto)
            onClick(proyectoJson)
        }

        // Botón eliminar → eliminar proyecto de SharedPreferences y lista local
        holder.btnEliminar.setOnClickListener {
            eliminarProyecto(holder.itemView.context, proyecto)
            proyectos.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, proyectos.size)
        }
    }

    override fun getItemCount(): Int = proyectos.size

    // Actualizar lista de proyectos
    fun actualizarLista(nuevaLista: List<Proyecto>?) {
        proyectos.clear()
        if (!nuevaLista.isNullOrEmpty()) {
            proyectos.addAll(nuevaLista)
        }
        notifyDataSetChanged()
    }

    // Eliminar proyecto de SharedPreferences
    private fun eliminarProyecto(context: Context, proyecto: Proyecto) {
        val prefs = context.getSharedPreferences(
            "proyectos_${proyecto.usuarioEmail}",
            Context.MODE_PRIVATE
                                                )
        val gson = Gson()
        val proyectosJson = prefs.getString("lista_proyectos", "[]")
        val type = object : TypeToken<MutableList<Proyecto>>() {}.type
        val lista: MutableList<Proyecto> = gson.fromJson(proyectosJson, type) ?: mutableListOf()

        // Filtrar lista quitando el proyecto con el mismo id
        val nuevaLista = lista.filter { it.id != proyecto.id }

        prefs.edit().putString("lista_proyectos", gson.toJson(nuevaLista)).apply()
    }
}
