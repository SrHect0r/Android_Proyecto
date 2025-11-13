package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start) // Asegúrate de que el XML se llame así

        // Referencia al botón
        val btnIniciar = findViewById<MaterialButton>(R.id.btnIniciar)

        // Acción al hacer clic
        btnIniciar.setOnClickListener {
            val intent = Intent(this, OpcionesActivity::class.java)
            startActivity(intent)
        }
    }
}
