package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CambiarContrasena : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_contrasena)

        val emailUsuario = findViewById<EditText>(R.id.EmailUsuario)
        val contrasenaActual = findViewById<EditText>(R.id.ContrasenaActual)
        val contrasenaNueva = findViewById<EditText>(R.id.NuevaContrasena)
        val confirmarNueva = findViewById<EditText>(R.id.ConfirmarContrasena)
        val botonConfirmar = findViewById<Button>(R.id.ConfirmarCambio)

        val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)

        botonConfirmar.setOnClickListener {
            val email = emailUsuario.text.toString().trim()
            val actual = contrasenaActual.text.toString().trim()
            val nueva = contrasenaNueva.text.toString().trim()
            val confirmar = confirmarNueva.text.toString().trim()

            if (email.isEmpty() || actual.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Leer credenciales guardadas
            val emailGuardado = prefs.getString("email", "")
            val passwordGuardado = prefs.getString("password", "")

            if (email != emailGuardado) {
                Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (actual != passwordGuardado) {
                Toast.makeText(this, "La contraseña actual no es correcta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (nueva != confirmar) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar la nueva contraseña
            prefs.edit().putString("password", nueva).apply()
            Toast.makeText(this, "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show()

            // ✅ Redirigir al LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Para cerrar esta actividad y no volver con "atrás"
        }
    }
}
