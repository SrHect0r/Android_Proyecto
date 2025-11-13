package com.example.android_proyecto

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleProyectoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_proyecto) // Tu XML de detalles

        // Obtener proyecto desde el Intent
        val proyecto = intent.getParcelableExtra<Proyecto>("proyectoSeleccionado")

        if (proyecto != null) {
            // Referencias a los TextViews del layout
            val txtTitulo = findViewById<TextView>(R.id.txtTituloDetalle)
            val txtDescripcion = findViewById<TextView>(R.id.txtDescripcionDetalle)
            val txtEstado = findViewById<TextView>(R.id.txtEstadoDetalle)
            val txtFecha = findViewById<TextView>(R.id.txtFechaDetalle)
            val txtPrioridad = findViewById<TextView>(R.id.txtPrioridadDetalle)

            // Asignar valores del proyecto a los TextViews
            txtTitulo.text = proyecto.titulo
            txtDescripcion.text = proyecto.descripcion
            txtEstado.text = "Estado: ${proyecto.estado}"
            txtFecha.text = "Fecha límite: ${proyecto.fecha}"
            txtPrioridad.text = "Prioridad: ${proyecto.prioridad}"
        }
    }
}
