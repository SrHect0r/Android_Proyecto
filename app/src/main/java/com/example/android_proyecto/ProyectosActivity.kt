package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class ProyectosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proyectos)

        val rvProyectos = findViewById<RecyclerView>(R.id.rvProyectos)
        rvProyectos.layoutManager = LinearLayoutManager(this)

        // Obtener ID del usuario logueado
        val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
        val usuarioId = prefs.getString("usuario_actual_id", "") ?: ""

        // Leer proyectos filtrados por usuario
        val proyectos = leerProyectosDeUsuario(usuarioId)

        if (proyectos.isEmpty()) {
            Toast.makeText(this, "No hay proyectos para este usuario", Toast.LENGTH_SHORT).show()
        }

        // Configurar adaptador pasando usuarioId
        val adapter = ProyectosAdapter(proyectos, usuarioId) { proyectoSeleccionado ->
            val intent = Intent(this, DetalleProyectoActivity::class.java)
            intent.putExtra("proyecto", proyectoSeleccionado) // Proyecto debe implementar Serializable
            startActivity(intent)
        }

        rvProyectos.adapter = adapter
    }

    private fun leerProyectosDeUsuario(usuarioId: String): List<Proyecto> {
        return try {
            val jsonString = assets.open("proyectos.json")
                .bufferedReader()
                .use { it.readText() }

            val proyectosArray = JSONArray(jsonString)
            val listaProyectos = mutableListOf<Proyecto>()

            for (i in 0 until proyectosArray.length()) {
                val proyectoObj = proyectosArray.getJSONObject(i)

                // IDs de usuarios asignados al proyecto
                val usuariosJson = proyectoObj.getJSONArray("UsuariosAsignados")
                val listaUsuarios = mutableListOf<String>()
                for (j in 0 until usuariosJson.length()) {
                    listaUsuarios.add(usuariosJson.getString(j))
                }

                // Filtrar proyectos por usuario
                if (!listaUsuarios.contains(usuarioId)) continue

                // Leer tareas asignadas al usuario
                val tareasJson = proyectoObj.getJSONArray("Tareas")
                val listaTareas = mutableListOf<Tarea>()
                for (k in 0 until tareasJson.length()) {
                    val tareaObj = tareasJson.getJSONObject(k)

                    val usuariosTareaJson = tareaObj.getJSONArray("UsuariosAsignados")
                    val listaUsuariosTarea = mutableListOf<String>()
                    for (m in 0 until usuariosTareaJson.length()) {
                        listaUsuariosTarea.add(usuariosTareaJson.getString(m))
                    }

                    // Filtrar tareas por usuario
                    if (!listaUsuariosTarea.contains(usuarioId)) continue

                    val tarea = Tarea(
                        id = tareaObj.getString("Id"),
                        nombre = tareaObj.getString("Nombre"),
                        descripcion = tareaObj.getString("Descripcion"),
                        estado = tareaObj.getString("Estado"),
                        prioridad = tareaObj.getString("Prioridad"),
                        fechaCreacion = tareaObj.getString("FechaCreacion"),
                        fechaInicio = tareaObj.getString("FechaInicio"),
                        fechaFin = tareaObj.getString("FechaFin"),
                        completada = tareaObj.getBoolean("Completada"),
                        usuariosAsignados = listaUsuariosTarea,
                        horas = tareaObj.optInt("Horas", 0),
                        comentario = tareaObj.optString("Comentario", "")
                                     )
                    listaTareas.add(tarea)
                }

                val proyecto = Proyecto(
                    id = proyectoObj.getString("Id"),
                    nombre = proyectoObj.getString("Nombre"),
                    descripcion = proyectoObj.getString("Descripcion"),
                    fechaInicio = proyectoObj.getString("FechaInicio"),
                    fechaFin = proyectoObj.getString("FechaFin"),
                    usuariosAsignados = listaUsuarios,
                    tareas = listaTareas
                                       )

                listaProyectos.add(proyecto)
            }

            listaProyectos
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
