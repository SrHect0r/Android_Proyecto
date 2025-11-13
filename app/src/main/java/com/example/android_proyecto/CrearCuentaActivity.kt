package com.example.android_proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

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

        botonRegistrar.setOnClickListener {
            val nombre = Nombre.text.toString()
            val email = Email.text.toString()
            val contrasena = Contrasena.text.toString()
            val fechanacimiento = FechaNacimiento.text.toString()

            if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty() || fechanacimiento.isEmpty()) {
                Toast.makeText(this, "Porfavor rellene los campos obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Cuenta creada para $nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }
}