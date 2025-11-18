package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class ProyectosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proyectos)

        val rvProyectos = findViewById<RecyclerView>(R.id.rvProyectos)
        rvProyectos.layoutManager = LinearLayoutManager(this)

        // Obtener usuario logueado
        val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
        val usuarioActual = prefs.getString("usuario_actual", "") ?: ""

        // Leer proyectos del JSON
        val proyectos = leerProyectosDeUsuario(usuarioActual)

        if (proyectos.isEmpty()) {
            Toast.makeText(this, "No hay proyectos para este usuario", Toast.LENGTH_SHORT).show()
        }

        // Adaptador
        val adapter = ProyectosAdapter(proyectos) { proyectoSeleccionado ->
            // Al hacer click en un proyecto, abrimos DetalleProyectoActivity
            val intent = Intent(this, DetalleProyectoActivity::class.java)
            intent.putExtra("proyecto", proyectoSeleccionado)
            startActivity(intent)
        }

        rvProyectos.adapter = adapter
    }

    private fun leerProyectosDeUsuario(emailUsuario: String): List<Proyecto> {
        return try {
            val jsonString = assets.open("proyectos.json")
                .bufferedReader()
                .use { it.readText() }

            val jsonObj = JSONObject(jsonString)
            if (!jsonObj.has(emailUsuario)) return emptyList()

            val listaProyectos = mutableListOf<Proyecto>()
            val proyectosArray = jsonObj.getJSONArray(emailUsuario)

            for (i in 0 until proyectosArray.length()) {
                val proyectoObj = proyectosArray.getJSONObject(i)
                val nombre = proyectoObj.getString("nombre")
                val descripcion = proyectoObj.getString("descripcion")
                val fechaLimite = proyectoObj.getString("fechaLimite")

                // Leer tareas
                val tareasArray = proyectoObj.getJSONArray("tareas")
                val tareasLista = mutableListOf<Tarea>()
                for (j in 0 until tareasArray.length()) {
                    val tareaObj = tareasArray.getJSONObject(j)
                    val tarea = Tarea(
                        nombre = tareaObj.getString("nombre"),
                        estado = tareaObj.getString("estado"),
                        horas = tareaObj.getInt("horas"),
                        comentario = tareaObj.getString("comentario")
                                     )
                    tareasLista.add(tarea)
                }

                // Añadir proyecto completo
                listaProyectos.add(Proyecto(nombre, descripcion, fechaLimite, tareasLista))
            }

            listaProyectos

        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
