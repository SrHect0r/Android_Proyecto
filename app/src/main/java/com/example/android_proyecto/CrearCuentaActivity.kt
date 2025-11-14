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

        val Nombre = findViewById<EditText>(R.id.NombreCompleto)
        val Contrasena = findViewById<EditText>(R.id.ContrasenaUsuario)
        val Email = findViewById<EditText>(R.id.Correo)
        val Telefono = findViewById<EditText>(R.id.Telefono)
        val FechaNacimiento = findViewById<EditText>(R.id.FechaNacimiento)
        val botonRegistrar = findViewById<Button>(R.id.Registrar)

        val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)

        botonRegistrar.setOnClickListener {
            val nombre = Nombre.text.toString().trim()
            val email = Email.text.toString().trim()
            val contrasena = Contrasena.text.toString().trim()
            val fechanacimiento = FechaNacimiento.text.toString().trim()

            if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty() || fechanacimiento.isEmpty()) {
                Toast.makeText(this, "Por favor rellene los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar los datos en SharedPreferences
            val editor = prefs.edit()
            editor.putString("nombre", nombre)
            editor.putString("email", email)
            editor.putString("password", contrasena)
            editor.putString("fechaNacimiento", fechanacimiento)
            editor.apply()

            Toast.makeText(this, "Cuenta creada para $nombre", Toast.LENGTH_SHORT).show()

            // Ir a OpcionesActivity automáticamente después de crear la cuenta
            val intent = Intent(this, OpcionesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
