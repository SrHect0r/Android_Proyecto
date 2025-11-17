package com.example.android_proyecto

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EliminarProyectoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EliminarProyectoAdapter
    private lateinit var usuarioActual: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar)

        recyclerView = findViewById(R.id.recyclerEliminarProyectos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
        usuarioActual = prefs.getString("usuario_actual", "") ?: ""

        // Inicializar adapter con la lista de proyectos del usuario actual
        val proyectos = obtenerProyectos(this, usuarioActual).toMutableList()
        adapter = EliminarProyectoAdapter(this, proyectos) { proyecto ->
            // Eliminar del almacenamiento
            eliminarProyecto(this, usuarioActual, proyecto)
            // Actualizar RecyclerView con los proyectos actuales
            val nuevaLista = obtenerProyectos(this, usuarioActual).toMutableList()
            adapter.actualizarLista(nuevaLista)
            Toast.makeText(this, "Proyecto '${proyecto.titulo}' eliminado", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter
    }
}
