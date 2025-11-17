package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VerProyectosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProyectoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_proyectos)

        recyclerView = findViewById(R.id.recyclerProyectos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializar adaptador con lista vacía
        adapter = ProyectoAdapter(mutableListOf()) { proyecto ->
            // Pasamos solo el ID del proyecto al detalle
            val intent = Intent(this, DetalleProyectoActivity::class.java)
            intent.putExtra("proyectoId", proyecto.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Botón para agregar nuevo proyecto
        val btnNuevoProyecto = findViewById<FloatingActionButton>(R.id.btnNuevoProyecto)
        btnNuevoProyecto.setOnClickListener {
            startActivity(Intent(this, AgregarProyectoActivity::class.java))
        }

        // Cargar proyectos del usuario actual
        actualizarListaProyectos()
    }

    override fun onResume() {
        super.onResume()
        // Refrescar la lista cada vez que volvemos a la activity
        actualizarListaProyectos()
    }

    private fun actualizarListaProyectos() {
        val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
        // Usar "usuario_actual" coherentemente en toda la app
        val usuarioActual = prefs.getString("usuario_actual", "") ?: ""
        val listaDeProyectos = obtenerProyectos(this, usuarioActual) // proyectos filtrados por usuario
        adapter.actualizarLista(listaDeProyectos)
    }

}
