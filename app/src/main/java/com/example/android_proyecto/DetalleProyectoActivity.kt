package com.example.android_proyecto

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleProyectoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_proyecto)

        // Obtener el ID del proyecto desde el Intent
        val proyectoId = intent.getIntExtra("proyectoId", -1)
        if (proyectoId == -1) {
            finish() // ID inválido, cerrar activity
            return
        }

        // Obtener usuario logueado
        val prefs: SharedPreferences = getSharedPreferences("mis_prefs", MODE_PRIVATE)
        val usuarioActual = prefs.getString("usuario_actual", "") ?: ""

        // Obtener lista de proyectos solo del usuario actual
        val proyectos = obtenerProyectos(this, usuarioActual)
        val proyecto = proyectos.find { it.id == proyectoId }

        if (proyecto == null) {
            finish() // Proyecto no encontrado, cerrar activity
            return
        }

        // Referencias a TextViews
        val txtTitulo = findViewById<TextView>(R.id.txtTituloDetalle)
        val txtDescripcion = findViewById<TextView>(R.id.txtDescripcionDetalle)
        val txtEstado = findViewById<TextView>(R.id.txtEstadoDetalle)
        val txtFecha = findViewById<TextView>(R.id.txtFechaDetalle)
        val txtPrioridad = findViewById<TextView>(R.id.txtPrioridadDetalle)

        // Asignar valores del proyecto
        txtTitulo.text = proyecto.titulo
        txtDescripcion.text = proyecto.descripcion
        txtEstado.text = "Estado: ${proyecto.estado}"
        txtFecha.text = "Fecha límite: ${proyecto.fecha}"
        txtPrioridad.text = "Prioridad: ${proyecto.prioridad}"
    }
}
