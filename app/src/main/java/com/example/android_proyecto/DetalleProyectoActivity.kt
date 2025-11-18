package com.example.android_proyecto

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class DetalleProyectoActivity : AppCompatActivity() {

    private lateinit var contenedorTareas: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_proyecto)

        // ---------- OBTENER PROYECTO DESDE EL INTENT ----------
        val proyectoJson = intent.getStringExtra("proyecto")
        if (proyectoJson.isNullOrEmpty()) {
            finish() // Si no recibimos datos, cerramos Activity
            return
        }

        // Intentamos parsear el JSON de manera segura
        val proyecto: Proyecto? = try {
            Gson().fromJson(proyectoJson, Proyecto::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (proyecto == null) {
            finish() // JSON inválido, cerramos Activity
            return
        }

        // ---------- REFERENCIAS A VISTAS ----------
        val txtTitulo = findViewById<TextView>(R.id.txtTituloDetalle)
        val txtDescripcion = findViewById<TextView>(R.id.txtDescripcionDetalle)
        val txtEstado = findViewById<TextView>(R.id.txtEstadoDetalle)
        val txtFecha = findViewById<TextView>(R.id.txtFechaDetalle)
        val txtPrioridad = findViewById<TextView>(R.id.txtPrioridadDetalle)
        contenedorTareas = findViewById(R.id.contenedorTareasDetalle)

        // ---------- ASIGNACIÓN DE DATOS ----------
        txtTitulo.text = proyecto.titulo ?: "Sin título"
        txtDescripcion.text = proyecto.descripcion ?: "Sin descripción"
        txtEstado.text = "Estado: ${proyecto.estado ?: "No definido"}"
        txtFecha.text = "Fecha límite: ${proyecto.fecha ?: "No definida"}"
        txtPrioridad.text = "Prioridad: ${proyecto.prioridad ?: "Normal"}"

        // ---------- MOSTRAR TAREAS ----------
        mostrarTareas(proyecto.tareas ?: emptyList())
    }

    private fun mostrarTareas(tareas: List<Tarea>) {
        contenedorTareas.removeAllViews()
        val inflater = LayoutInflater.from(this)

        if (tareas.isEmpty()) {
            // Mostrar mensaje si no hay tareas
            val txtSinTareas = TextView(this)
            txtSinTareas.text = "No hay tareas para este proyecto."
            txtSinTareas.textSize = 16f
            txtSinTareas.setTextColor(resources.getColor(android.R.color.black))
            contenedorTareas.addView(txtSinTareas)
            return
        }

        for (tarea in tareas) {
            val tareaView = inflater.inflate(R.layout.item_tarea_detalle, contenedorTareas, false)

            val txtNombre = tareaView.findViewById<TextView>(R.id.txtNombreTareaDetalle)
            val txtDescripcion = tareaView.findViewById<TextView>(R.id.txtDescripcionTareaDetalle)
            val txtPrioridad = tareaView.findViewById<TextView>(R.id.txtPrioridadTareaDetalle)
            val txtFechaLimite = tareaView.findViewById<TextView>(R.id.txtFechaLimiteTareaDetalle)
            val txtEstado = tareaView.findViewById<TextView>(R.id.txtEstadoTareaDetalle)

            txtNombre.text = tarea.nombre ?: "Sin nombre"
            txtDescripcion.text = tarea.descripcion ?: "Sin descripción"
            txtPrioridad.text = "Prioridad: ${tarea.prioridad ?: "Normal"}"
            txtFechaLimite.text = "Fecha límite: ${tarea.fechaLimite ?: "No definida"}"
            txtEstado.text = "Estado: ${tarea.estado ?: "No definido"}"

            contenedorTareas.addView(tareaView)
        }
    }
}
