package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Referencia al botón Iniciar
        val btnIniciar: MaterialButton = findViewById(R.id.btnIniciar)

        // Acción al hacer clic en el botón
        btnIniciar.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Recomendado para que no vuelva atrás a la pantalla de inicio
        }
    }
}
