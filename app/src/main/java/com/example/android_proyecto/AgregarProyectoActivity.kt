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

        val txtNombre = findViewById<TextInputEditText>(R.id.txtNombreProyecto)
        val txtDescripcion = findViewById<TextInputEditText>(R.id.txtDescripcion)
        val txtFechaInicio = findViewById<TextInputEditText>(R.id.txtFechaInicio)
        val txtFechaLimite = findViewById<TextInputEditText>(R.id.txtFechaLimite)
        val txtPrioridad = findViewById<AutoCompleteTextView>(R.id.txtPrioridad)
        val txtEstado = findViewById<AutoCompleteTextView>(R.id.txtEstado)

        val btnAgregar = findViewById<MaterialButton>(R.id.btnAddProject)
        val btnCancelar = findViewById<MaterialButton>(R.id.btnCancelar)

        // Configurar AutoCompleteTextViews
        val prioridades = listOf("Alta", "Media", "Baja")
        txtPrioridad.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, prioridades))
        txtPrioridad.keyListener = null
        txtPrioridad.setOnClickListener { txtPrioridad.showDropDown() }

        val estados = listOf("Pendiente", "En progreso", "Completado")
        txtEstado.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, estados))
        txtEstado.keyListener = null
        txtEstado.setOnClickListener { txtEstado.showDropDown() }

        // Cancelar
        btnCancelar.setOnClickListener { finish() }

        // Agregar proyecto
        btnAgregar.setOnClickListener {
            val nombre = txtNombre.text.toString().trim()
            val descripcion = txtDescripcion.text.toString().trim()
            val fechaInicio = txtFechaInicio.text.toString().trim()
            val fechaLimite = txtFechaLimite.text.toString().trim()
            val prioridad = txtPrioridad.text.toString().trim()
            val estado = txtEstado.text.toString().trim()

            if (nombre.isEmpty() || descripcion.isEmpty() || fechaInicio.isEmpty() ||
                fechaLimite.isEmpty() || prioridad.isEmpty() || estado.isEmpty()
            ) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Obtener usuario logueado
            val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
            val usuarioActual = prefs.getString("usuario_actual", "") ?: ""

            // Crear proyecto asociado al usuario actual
            val nuevoProyecto = Proyecto(
                id = (0..1000).random(),
                titulo = nombre,
                descripcion = descripcion,
                estado = estado,
                fecha = fechaLimite,
                prioridad = prioridad,
                usuarioEmail = usuarioActual
                                        )

            // Guardar proyecto solo para este usuario
            guardarProyecto(this, usuarioActual, nuevoProyecto)

            Toast.makeText(this, "Proyecto agregado", Toast.LENGTH_SHORT).show()

            // Ir a VerProyectosActivity mostrando solo los proyectos del usuario
            val intent = Intent(this, VerProyectosActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
