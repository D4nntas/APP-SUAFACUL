package com.suafacul

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnUsuarios = findViewById<Button>(R.id.btnUsuarios)

        btnUsuarios.setOnClickListener {
            val intent = Intent(this, ListaUsuariosActivity::class.java)
            startActivity(intent)
        }
    }
}
