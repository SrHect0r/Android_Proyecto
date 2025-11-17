package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CrearCuentaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_cuenta)

        val nombreInput = findViewById<EditText>(R.id.NombreCompleto)
        val emailInput = findViewById<EditText>(R.id.Correo)
        val contrasenaInput = findViewById<EditText>(R.id.ContrasenaUsuario)
        val fechaNacimientoInput = findViewById<EditText>(R.id.FechaNacimiento)
        val botonRegistrar = findViewById<Button>(R.id.Registrar)

        botonRegistrar.setOnClickListener {
            val nombre = nombreInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val contrasena = contrasenaInput.text.toString().trim()
            val fechaNacimiento = fechaNacimientoInput.text.toString().trim()

            if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty() || fechaNacimiento.isEmpty()) {
                Toast.makeText(this, "Por favor rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoUsuario = Usuario(nombre, email, contrasena, fechaNacimiento)

            // Guardar el usuario usando la función centralizada
            guardarUsuario(this, nuevoUsuario)

            Toast.makeText(this, "Cuenta creada para $nombre", Toast.LENGTH_SHORT).show()

            // Ir al login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
