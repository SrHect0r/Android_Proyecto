package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VerProyectosActivity : AppCompatActivity() {

    private lateinit var listaDeProyectos: MutableList<Proyecto>
    private lateinit var adapter: ProyectoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_proyectos)

        // RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerProyectos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Obtener usuario logueado
        val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
        val usuarioActual = prefs.getString("email", "") ?: ""

        // Cargar proyectos del usuario
        listaDeProyectos = obtenerProyectos(this, usuarioActual)

        // Adapter
        adapter = ProyectoAdapter(listaDeProyectos) { proyecto ->
            // Acción al hacer click en un proyecto
            val intent = Intent(this, DetalleProyectoActivity::class.java)
            intent.putExtra("proyectoId", proyecto.id)
            startActivity(intent)
        }

        recyclerView.adapter = adapter

        // Botón flotante para agregar nuevo proyecto
        val btnNuevoProyecto = findViewById<FloatingActionButton>(R.id.btnNuevoProyecto)
        btnNuevoProyecto.setOnClickListener {
            val intent = Intent(this, AgregarProyectoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Actualizar la lista al volver de AgregarProyectoActivity
        val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
        val usuarioActual = prefs.getString("email", "") ?: ""
        listaDeProyectos.clear()
        listaDeProyectos.addAll(obtenerProyectos(this, usuarioActual))
        adapter.notifyDataSetChanged()
    }
}
