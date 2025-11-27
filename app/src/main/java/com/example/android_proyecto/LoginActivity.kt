package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import org.json.JSONArray

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Referencias a UI
        val email = findViewById<EditText>(R.id.email)
        val contra = findViewById<EditText>(R.id.contra)
        val btnLogin = findViewById<MaterialButton>(R.id.btnIniciarSesion)

        btnLogin.setOnClickListener {
            val emailTxt = email.text.toString().trim()
            val passTxt = contra.text.toString().trim()

            if (emailTxt.isEmpty()) {
                email.error = "Introduce tu email"
                return@setOnClickListener
            }

            if (passTxt.isEmpty()) {
                contra.error = "Introduce tu contraseña"
                return@setOnClickListener
            }

            val usuarios = leerUsuariosDesdeJSON()
            val esAdmin = emailTxt == "admin@gmail.com" && passTxt == "1234"
            val usuarioActual = usuarios.find { it.email == emailTxt && it.password == passTxt }

            if (esAdmin || usuarioActual != null) {
                // Guardamos usuario logueado en SharedPreferences
                val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
                if (usuarioActual != null) {
                    prefs.edit()
                        .putString("usuario_actual_id", usuarioActual.id)
                        .putString("usuario_actual_email", usuarioActual.email)
                        .apply()
                } else {
                    // admin ficticio con ID "admin"
                    prefs.edit()
                        .putString("usuario_actual_id", "admin")
                        .putString("usuario_actual_email", "admin@gmail.com")
                        .apply()
                }

                Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, OpcionesActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ---- Leer usuarios desde JSON ----
    private fun leerUsuariosDesdeJSON(): List<Usuario> {
        return try {
            val jsonString = assets.open("usuarios.json").bufferedReader().use { it.readText() }
            val listaUsuarios = mutableListOf<Usuario>()
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val id = obj.getString("Id")
                val email = obj.getString("Email")
                val password = obj.getString("Password")
                listaUsuarios.add(Usuario(id, email, password))
            }
            listaUsuarios
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
