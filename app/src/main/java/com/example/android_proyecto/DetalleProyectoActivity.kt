package com.example.android_proyecto

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DetalleProyectoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_proyecto)

        val proyecto = intent.getSerializableExtra("proyecto") as? Proyecto
        if (proyecto == null) {
            Toast.makeText(this, "Proyecto no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // RecyclerView de tareas
        val rvTareas = findViewById<RecyclerView>(R.id.rvTareas)
        rvTareas.layoutManager = LinearLayoutManager(this)
        rvTareas.adapter = TareasAdapter(proyecto.tareas)
    }
}
