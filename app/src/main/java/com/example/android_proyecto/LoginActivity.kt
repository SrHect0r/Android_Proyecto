package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)   // Asegúrate de que el XML tiene este nombre

        // Referencias
        val email = findViewById<EditText>(R.id.email)
        val contra = findViewById<EditText>(R.id.contra)
        val btnLogin = findViewById<MaterialButton>(R.id.btnIniciarSesion)
        val tvRegistro = findViewById<TextView>(R.id.tvRegistro)
        val tvOlvidoContra = findViewById<TextView>(R.id.tvOlvidoContra)

        // ---- REGISTRARSE ----
        tvRegistro.setOnClickListener {
            startActivity(Intent(this, CrearCuentaActivity::class.java))
        }

        // ---- OLVIDÉ CONTRASEÑA ----
        tvOlvidoContra.setOnClickListener {
            startActivity(Intent(this, CambiarContrasena::class.java))
        }

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

            // Validación con lista de usuarios + admin por defecto
            val esValido = validarUsuario(this, emailTxt, passTxt)
            val esAdmin = (emailTxt == "admin@gmail.com" && passTxt == "1234")

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
}
