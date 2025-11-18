package com.example.android_proyecto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class TareasAdapter(private val tareas: MutableList<Tarea>) :
    RecyclerView.Adapter<TareasAdapter.TareaViewHolder>() {

    class TareaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreTarea)
        val spinnerEstado: Spinner = itemView.findViewById(R.id.spinnerEstado)
        val etHoras: EditText = itemView.findViewById(R.id.etHoras)
        val etComentario: EditText = itemView.findViewById(R.id.etComentario)
        val btnGuardar: Button = itemView.findViewById(R.id.btnGuardarTarea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea, parent, false)
        return TareaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea = tareas[position]

        holder.tvNombre.text = tarea.nombre
        holder.etHoras.setText(tarea.horas.toString())
        holder.etComentario.setText(tarea.comentario)

        // Spinner
        val estados = arrayOf("pendiente", "en progreso", "completada")
        val adapterSpinner = ArrayAdapter(holder.itemView.context, android.R.layout.simple_spinner_item, estados)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.spinnerEstado.adapter = adapterSpinner
        holder.spinnerEstado.setSelection(estados.indexOf(tarea.estado))

        // Guardar cambios
        holder.btnGuardar.setOnClickListener {
            tarea.estado = holder.spinnerEstado.selectedItem.toString()
            tarea.horas = holder.etHoras.text.toString().toIntOrNull() ?: 0
            tarea.comentario = holder.etComentario.text.toString()
            Toast.makeText(holder.itemView.context, "Tarea actualizada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = tareas.size
}
