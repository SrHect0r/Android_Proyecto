package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VerProyectosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_proyectos) // Tu XML

        // RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerProyectos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Lista de proyectos de prueba
        val listaDeProyectos = listOf(
            Proyecto(1, "Proyecto A", "Descripción del proyecto A", "En progreso", "12/11/2025", "Alta"),
            Proyecto(2, "Proyecto B", "Descripción del proyecto B", "Pendiente", "13/11/2025", "Media"),
            Proyecto(3, "Proyecto C", "Descripción del proyecto C", "Completado", "14/11/2025", "Baja")
                                     )


        // Adapter
        val adapter = ProyectoAdapter(listaDeProyectos) { proyecto ->
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
}
