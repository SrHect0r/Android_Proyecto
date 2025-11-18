package com.example.android_proyecto

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class AgregarProyectoActivity : AppCompatActivity() {

    private lateinit var contenedorTareas: LinearLayout
    private lateinit var txtCompartirUsuario: MultiAutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)

        // --- Referencias ---
        val txtNombre = findViewById<TextInputEditText>(R.id.txtNombreProyecto)
        val txtDescripcion = findViewById<TextInputEditText>(R.id.txtDescripcion)
        val txtFechaInicio = findViewById<TextInputEditText>(R.id.txtFechaInicio)
        val txtFechaLimite = findViewById<TextInputEditText>(R.id.txtFechaLimite)
        val txtPrioridad = findViewById<AutoCompleteTextView>(R.id.txtPrioridad)
        val txtEstado = findViewById<AutoCompleteTextView>(R.id.txtEstado)
        txtCompartirUsuario = findViewById(R.id.txtCompartirUsuario)
        contenedorTareas = findViewById(R.id.contenedorTareas)

        val btnAgregar = findViewById<MaterialButton>(R.id.btnAddProject)
        val btnCancelar = findViewById<MaterialButton>(R.id.btnCancelar)
        val btnAgregarTarea = findViewById<MaterialButton>(R.id.btnAgregarTarea)

        // --- Configurar AutoCompleteTextViews ---
        val prioridades = listOf("Alta", "Media", "Baja")
        txtPrioridad.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                prioridades
                        )
                               )
        txtPrioridad.keyListener = null
        txtPrioridad.setOnClickListener { txtPrioridad.showDropDown() }

        val estados = listOf("Pendiente", "En progreso", "Completado")
        txtEstado.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, estados))
        txtEstado.keyListener = null
        txtEstado.setOnClickListener { txtEstado.showDropDown() }

        // --- Calendario para fechas ---
        txtFechaInicio.setOnClickListener { mostrarCalendario(txtFechaInicio) }
        txtFechaLimite.setOnClickListener { mostrarCalendario(txtFechaLimite) }

        // --- Configurar MultiAutoCompleteTextView para usuarios compartidos ---
        val usuarios = obtenerListaUsuarios(this).map { it.email }
        val adapterUsuarios = ArrayAdapter(this, android.R.layout.simple_list_item_1, usuarios)
        txtCompartirUsuario.setAdapter(adapterUsuarios)
        txtCompartirUsuario.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        // --- Botones ---
        btnAgregarTarea.setOnClickListener { agregarTarea() }
        btnCancelar.setOnClickListener { finish() }

        btnAgregar.setOnClickListener {
            val nombre = txtNombre.text.toString().trim()
            val descripcion = txtDescripcion.text.toString().trim()
            val fechaInicio = txtFechaInicio.text.toString().trim()
            val fechaLimite = txtFechaLimite.text.toString().trim()
            val prioridad = txtPrioridad.text.toString().trim()
            val estado = txtEstado.text.toString().trim()
            val usuariosSeleccionados =
                txtCompartirUsuario.text.toString().split(",").map { it.trim() }
                    .filter { it.isNotEmpty() }

            // Validaciones
            if (nombre.isEmpty() || descripcion.isEmpty() || fechaInicio.isEmpty() || fechaLimite.isEmpty() || prioridad.isEmpty() || estado.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Usuario actual
            val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
            val usuarioActual = prefs.getString("usuario_actual", "") ?: ""

            // --- Construir lista de tareas ---
            val tareasProyecto = mutableListOf<Tarea>()
            for (i in 0 until contenedorTareas.childCount) {
                val tareaView = contenedorTareas.getChildAt(i)
                val nombreT =
                    tareaView.findViewById<EditText>(R.id.etNombreTarea)?.text.toString().trim()
                val descripcionT =
                    tareaView.findViewById<EditText>(R.id.etDescripcionTarea)?.text.toString()
                        .trim()
                val prioridadT =
                    tareaView.findViewById<AutoCompleteTextView>(R.id.etPrioridadTarea)?.text.toString()
                        .trim()
                val fechaLimiteT =
                    tareaView.findViewById<TextView>(R.id.etFechaLimiteTarea)?.text.toString()
                        .trim()
                val estadoT =
                    tareaView.findViewById<AutoCompleteTextView>(R.id.etEstadoTarea)?.text.toString()
                        .trim()

                if (nombreT.isNotEmpty() && prioridadT.isNotEmpty() && estadoT.isNotEmpty()) {
                    tareasProyecto.add(
                        Tarea(
                            nombreT,
                            descripcionT,
                            prioridadT,
                            fechaLimiteT,
                            estadoT
                             )
                                      )
                }
            }

            // --- Crear proyecto ---
            val nuevoProyecto = Proyecto(
                id = System.currentTimeMillis(), // ID único
                titulo = nombre,
                descripcion = descripcion,
                estado = estado,
                fecha = fechaLimite,
                prioridad = prioridad,
                usuarioEmail = usuarioActual,
                tareas = tareasProyecto,
                usuariosCompartidos = usuariosSeleccionados.toMutableList()
                                        )

            // --- Guardar proyecto ---
            guardarProyecto(this, usuarioActual, nuevoProyecto)
            usuariosSeleccionados.forEach { u ->
                if (usuarios.contains(u)) guardarProyecto(this, u, nuevoProyecto)
            }

            Toast.makeText(this, "Proyecto agregado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, VerProyectosActivity::class.java))
            finish()
        }
    }

    // --- Mostrar calendario ---
    private fun mostrarCalendario(editText: TextView) {
        val calendario = Calendar.getInstance()
        val dpd = DatePickerDialog(
            this,
            { _, year, month, day ->
                editText.text = String.format("%02d/%02d/%04d", day, month + 1, year)
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
                                  )
        dpd.show()
    }

    // --- Agregar tarea dinámica ---
    private fun agregarTarea() {
        val tareaView = layoutInflater.inflate(R.layout.item_tarea, contenedorTareas, false)
        contenedorTareas.addView(tareaView)

        val etPrioridad = tareaView.findViewById<AutoCompleteTextView>(R.id.etPrioridadTarea)
        val etFechaLimite = tareaView.findViewById<TextView>(R.id.etFechaLimiteTarea)
        val etEstado = tareaView.findViewById<AutoCompleteTextView>(R.id.etEstadoTarea)

        val prioridades = listOf("Alta", "Media", "Baja")
        etPrioridad.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, prioridades))
        etPrioridad.keyListener = null
        etPrioridad.setOnClickListener { etPrioridad.showDropDown() }

        val estados = listOf("Pendiente", "En progreso", "Completado")
        etEstado.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, estados))
        etEstado.keyListener = null
        etEstado.setOnClickListener { etEstado.showDropDown() }

        etFechaLimite.setOnClickListener { mostrarCalendario(etFechaLimite) }
    }

    // --- Guardar proyecto ---
// --- Guardar proyecto ---
    private fun guardarProyecto(context: Context, usuarioEmail: String, proyecto: Proyecto) {
        val prefs = context.getSharedPreferences("proyectos_$usuarioEmail", Context.MODE_PRIVATE)
        val gson = Gson()
        val proyectosJson = prefs.getString("lista_proyectos", "[]")
        val type = object : TypeToken<MutableList<Proyecto>>() {}.type

        // Convertimos a lista, filtrando posibles null
        val proyectos: MutableList<Proyecto> =
            gson.fromJson<MutableList<Proyecto>>(proyectosJson, type)?.filterNotNull()?.toMutableList()
                ?: mutableListOf()

        // Añadimos el nuevo proyecto
        proyectos.add(proyecto)

        // Guardamos de nuevo
        prefs.edit().putString("lista_proyectos", gson.toJson(proyectos)).apply()
    }

}