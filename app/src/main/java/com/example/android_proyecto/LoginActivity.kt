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
        setContentView(R.layout.activity_login) // Asegúrate de que tu XML tiene este nombre

        // Referencias a los elementos de UI
        val email = findViewById<EditText>(R.id.email)
        val contra = findViewById<EditText>(R.id.contra)
        val btnLogin = findViewById<MaterialButton>(R.id.btnIniciarSesion)

        // ---- LOGIN ----
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

            // Validación de usuario
            val esValido = validarUsuario(emailTxt, passTxt)
            val esAdmin = emailTxt == "admin@gmail.com" && passTxt == "1234"

            if (esValido || esAdmin) {
                // Guardamos el usuario logueado
                val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
                prefs.edit().putString("usuario_actual", emailTxt).apply()

                Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, OpcionesActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ---- FUNCIONES DE JSON ----
    private fun leerUsuariosDesdeJSON(): List<Usuario> {
        return try {
            val jsonString = assets.open("usuarios.json")
                .bufferedReader()
                .use { it.readText() }

            val listaUsuarios = mutableListOf<Usuario>()
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val email = obj.getString("email")
                val password = obj.getString("password")
                listaUsuarios.add(Usuario(email, password))
            }
            listaUsuarios
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun validarUsuario(email: String, pass: String): Boolean {
        val usuarios = leerUsuariosDesdeJSON()
        return usuarios.any { it.email == email && it.password == pass }
    }
}
