package com.example.android_proyecto

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EliminarProyectoActivity : AppCompatActivity() {

    private lateinit var proyectos: MutableList<Proyecto>
    private lateinit var adapter: EliminarProyectosAdapter
    private var usuarioActual: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerEliminarProyectos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Obtener usuario logueado
        val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
        usuarioActual = prefs.getString("email", "") ?: ""

        // Cargar proyectos del usuario
        proyectos = obtenerProyectos(this, usuarioActual)

        adapter = EliminarProyectosAdapter(this, proyectos) { proyecto ->
            // Eliminar proyecto del listado y de SharedPreferences
            proyectos.remove(proyecto)
            eliminarProyecto(this, usuarioActual, proyecto)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Proyecto '${proyecto.titulo}' eliminado", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        // Actualizar lista en caso de que haya cambios
        proyectos.clear()
        proyectos.addAll(obtenerProyectos(this, usuarioActual))
        adapter.notifyDataSetChanged()
    }
}
