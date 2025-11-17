package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class OpcionesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)

        // Referencias a los botones
        val btnAgregar = findViewById<MaterialButton>(R.id.btnAgregarProyecto)
        val btnEliminar = findViewById<MaterialButton>(R.id.btnEliminarProyecto)
        val btnVer = findViewById<MaterialButton>(R.id.btnVerProyectos)
        val btnCambiar = findViewById<MaterialButton>(R.id.btnCambiarContrasena)
        val btnCerrar = findViewById<MaterialButton>(R.id.btnCerrarSesion)

        // Acciones al hacer clic
        btnAgregar.setOnClickListener {
            startActivity(Intent(this, AgregarProyectoActivity::class.java))
        }

        btnEliminar.setOnClickListener {
            startActivity(Intent(this, EliminarProyectoActivity::class.java))
        }

        btnVer.setOnClickListener {
            startActivity(Intent(this, VerProyectosActivity::class.java))
        }

        btnCambiar.setOnClickListener {
            startActivity(Intent(this, CambiarContrasena::class.java))
        }

        btnCerrar.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            // Limpiar historial de Activities para que no pueda volver atrás
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
