package com.example.android_proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CambiarContrasena : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cambiar_contrasena)

        val contrasenaactual = findViewById<EditText>(R.id.ContrasenaActual)
        val contrasenanueva = findViewById<EditText>(R.id.NuevaContrasena)
        val confirmarnueva = findViewById<EditText>(R.id.ConfirmarContrasena)
        val botonconfirmar = findViewById<Button>(R.id.ConfirmarCambio)
        val prefs = getSharedPreferences("mis_prefs",MODE_PRIVATE)

        botonconfirmar.setOnClickListener {
            val actual = contrasenaactual.text.toString()
            val nueva = contrasenanueva.text.toString()
            val confirmar = confirmarnueva.text.toString()
            val guardada = prefs.getString("password","")
            if (actual != guardada){
                Toast.makeText(this, "La contraseña actual no es correcta", Toast.LENGTH_SHORT).show()
            } else if (nueva != confirmar) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Contraseña cambiada con exito", Toast.LENGTH_SHORT).show()
            }
        }
    }
}