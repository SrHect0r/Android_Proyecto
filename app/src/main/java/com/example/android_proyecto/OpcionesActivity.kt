package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class OpcionesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones) // Asegúrate de que el XML se llame así

        // Referencias a los botones
        val btnAgregar = findViewById<MaterialButton>(R.id.btnAgregarProyecto)
        val btnEliminar = findViewById<MaterialButton>(R.id.btnEliminarProyecto)
        val btnVer = findViewById<MaterialButton>(R.id.btnVerProyectos)
        val btnCambiar = findViewById<MaterialButton>(R.id.btnCambiarContrasena)
        val btnCerrar = findViewById<MaterialButton>(R.id.btnCerrarSesion)

        // Acciones al hacer clic
        btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarProyectoActivity::class.java)
            startActivity(intent)
        }

        btnEliminar.setOnClickListener {
            val intent = Intent(this, EliminarProyectoActivity::class.java)
            startActivity(intent)
        }

        btnVer.setOnClickListener {
            val intent = Intent(this, VerProyectosActivity::class.java)
            startActivity(intent)
        }

        btnCambiar.setOnClickListener {
            val intent = Intent(this, CambiarContrasena::class.java)
            startActivity(intent)
        }

        btnCerrar.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            // Limpia el historial para que no pueda volver atrás
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
