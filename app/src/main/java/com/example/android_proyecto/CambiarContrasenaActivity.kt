package com.example.android_proyecto

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CambiarContrasena : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_contrasena)

        val emailUsuario = findViewById<EditText>(R.id.EmailUsuario)
        val contrasenaActual = findViewById<EditText>(R.id.ContrasenaActual)
        val contrasenaNueva = findViewById<EditText>(R.id.NuevaContrasena)
        val confirmarNueva = findViewById<EditText>(R.id.ConfirmarContrasena)
        val botonConfirmar = findViewById<Button>(R.id.ConfirmarCambio)

        botonConfirmar.setOnClickListener {
            val email = emailUsuario.text.toString().trim()
            val actual = contrasenaActual.text.toString().trim()
            val nueva = contrasenaNueva.text.toString().trim()
            val confirmar = confirmarNueva.text.toString().trim()

            if (email.isEmpty() || actual.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Obtener la lista de usuarios
            val prefs = getSharedPreferences("mis_prefs", Context.MODE_PRIVATE)
            val gson = Gson()
            val usuariosJson = prefs.getString("usuarios", null)
            val tipo = object : TypeToken<MutableList<Usuario>>() {}.type
            val listaUsuarios: MutableList<Usuario> = if (usuariosJson != null) {
                gson.fromJson(usuariosJson, tipo)
            } else {
                mutableListOf()
            }

            // Buscar el usuario y verificar la contraseña actual
            val usuario = listaUsuarios.find { it.email == email }
            if (usuario == null) {
                Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (usuario.password != actual) {
                Toast.makeText(this, "La contraseña actual no es correcta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificar que la nueva contraseña coincide con la confirmación
            if (nueva != confirmar) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cambiar la contraseña y guardar la lista
            usuario.password = nueva
            prefs.edit().putString("usuarios", gson.toJson(listaUsuarios)).apply()

            Toast.makeText(this, "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show()

            // Redirigir al login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
