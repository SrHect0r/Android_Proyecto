package com.example.android_proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val email = findViewById<EditText>(R.id.email)
        val contra = findViewById<EditText>(R.id.contra)
        val btnLogin = findViewById<Button>(R.id.btnIniciarSesion)
        val tvRegistro = findViewById<TextView>(R.id.tvRegistro)
        val tvOlvidoContra = findViewById<TextView>(R.id.tvOlvidoContra)

        val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)

        // BOTÓN "REGÍSTRATE"
        tvRegistro.setOnClickListener {
            startActivity(Intent(this, CrearCuentaActivity::class.java))
        }

        // BOTÓN "OLVIDÉ CONTRASEÑA"
        tvOlvidoContra.setOnClickListener {
            startActivity(Intent(this, CambiarContrasena::class.java))
        }

        // BOTÓN LOGIN
        btnLogin.setOnClickListener {
            val emailTxt = email.text.toString().trim()
            val passTxt = contra.text.toString().trim()

            if (emailTxt.isEmpty()) {
                email.error = "Introduce tu email o número"
                return@setOnClickListener
            }

            if (passTxt.isEmpty()) {
                contra.error = "Introduce tu contraseña"
                return@setOnClickListener
            }

            // Leer credenciales guardadas
            val emailGuardado = prefs.getString("email", "")
            val passwordGuardado = prefs.getString("password", "")

            if ((emailTxt == emailGuardado && passTxt == passwordGuardado) ||
                (emailTxt == "admin@gmail.com" && passTxt == "1234")) {
                Toast.makeText(this, "Inicio correcto", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, OpcionesActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Login incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
