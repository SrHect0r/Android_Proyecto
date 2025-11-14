package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AgregarProyectoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)

        // Referencias a los campos del formulario
        val txtNombre = findViewById<TextInputEditText>(R.id.txtNombreProyecto)
        val txtDescripcion = findViewById<TextInputEditText>(R.id.txtDescripcion)
        val txtFechaInicio = findViewById<TextInputEditText>(R.id.txtFechaInicio)
        val txtFechaLimite = findViewById<TextInputEditText>(R.id.txtFechaLimite)
        val txtPrioridad = findViewById<AutoCompleteTextView>(R.id.txtPrioridad)
        val txtEstado = findViewById<AutoCompleteTextView>(R.id.txtEstado)

        val btnAgregar = findViewById<MaterialButton>(R.id.btnAddProject)
        val btnCancelar = findViewById<MaterialButton>(R.id.btnCancelar)

        // Configurar opciones para AutoCompleteTextViews
        val prioridades = listOf("Alta", "Media", "Baja")
        val adapterPrioridad = ArrayAdapter(this, android.R.layout.simple_list_item_1, prioridades)
        txtPrioridad.setAdapter(adapterPrioridad)
        txtPrioridad.keyListener = null // Desactiva edición manual
        txtPrioridad.setOnClickListener { txtPrioridad.showDropDown() }

        val estados = listOf("Pendiente", "En progreso", "Completado")
        val adapterEstado = ArrayAdapter(this, android.R.layout.simple_list_item_1, estados)
        txtEstado.setAdapter(adapterEstado)
        txtEstado.keyListener = null // Desactiva edición manual
        txtEstado.setOnClickListener { txtEstado.showDropDown() }

        // Obtener usuario logueado
        val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
        val usuarioActual = prefs.getString("email", "") ?: ""

        // Acción del botón Cancelar
        btnCancelar.setOnClickListener { finish() }

        // Acción del botón Agregar
        btnAgregar.setOnClickListener {
            val nombre = txtNombre.text.toString().trim()
            val descripcion = txtDescripcion.text.toString().trim()
            val fechaInicio = txtFechaInicio.text.toString().trim()
            val fechaLimite = txtFechaLimite.text.toString().trim()
            val prioridad = txtPrioridad.text.toString().trim()
            val estado = txtEstado.text.toString().trim()

            if (nombre.isEmpty() || descripcion.isEmpty() || fechaInicio.isEmpty() || estado.isEmpty() || prioridad.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear proyecto
            val nuevoProyecto = Proyecto(
                id = (0..1000).random(),
                titulo = nombre,
                descripcion = descripcion,
                estado = estado,
                fecha = fechaLimite,
                prioridad = prioridad
                                        )

            // Guardar proyecto para este usuario
            guardarProyecto(this, usuarioActual, nuevoProyecto)

            Toast.makeText(this, "Proyecto agregado", Toast.LENGTH_SHORT).show()

            // Ir a VerProyectosActivity
            val intent = Intent(this, VerProyectosActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
