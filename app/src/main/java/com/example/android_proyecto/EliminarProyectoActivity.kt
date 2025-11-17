package com.example.android_proyecto

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EliminarProyectoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var proyectos: MutableList<Proyecto>
    private lateinit var adapter: EliminarProyectosAdapter
    private lateinit var usuarioActual: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar)

        recyclerView = findViewById(R.id.recyclerEliminarProyectos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
        usuarioActual = prefs.getString("email", "") ?: ""

        // Cargar solo proyectos del usuario actual
        proyectos = obtenerProyectos(this, usuarioActual)

        adapter = EliminarProyectosAdapter(this, proyectos) { proyecto ->
            // Eliminar proyecto del almacenamiento
            eliminarProyecto(this, usuarioActual, proyecto)
            // Eliminar proyecto de la lista y actualizar RecyclerView
            proyectos.remove(proyecto)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Proyecto '${proyecto.titulo}' eliminado", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter
    }
}
