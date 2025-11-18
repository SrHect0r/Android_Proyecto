package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class OpcionesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)

        // Referencias a botones
        val btnVerProyectos = findViewById<MaterialButton>(R.id.btnVerProyectos)
        val btnCerrarSesion = findViewById<MaterialButton>(R.id.btnCerrarSesion)

        // ---- Ver proyectos ----
        btnVerProyectos.setOnClickListener {
            startActivity(Intent(this, ProyectosActivity::class.java))
        }

        // ---- Cerrar sesión ----
        btnCerrarSesion.setOnClickListener {
            val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
            prefs.edit().remove("usuario_actual").apply()

            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
