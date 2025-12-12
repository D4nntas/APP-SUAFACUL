package com.suafacul

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlin.concurrent.thread

class CadastroActivity : AppCompatActivity() {

    lateinit var edtNome: EditText
    lateinit var edtEmailCadastro: EditText
    lateinit var edtSenhaCadastro: EditText
    lateinit var btnSalvarCadastro: Button
    lateinit var btnVoltarLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        edtNome = findViewById(R.id.edtNome)
        edtEmailCadastro = findViewById(R.id.edtEmailCadastro)
        edtSenhaCadastro = findViewById(R.id.edtSenhaCadastro)
        btnSalvarCadastro = findViewById(R.id.btnSalvarCadastro)
        btnVoltarLogin = findViewById(R.id.btnVoltarLogin)

        // Instanciando o banco
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "suafacul_db"
        ).build()

        btnSalvarCadastro.setOnClickListener {

            val nome = edtNome.text.toString()
            val email = edtEmailCadastro.text.toString()
            val senha = edtSenhaCadastro.text.toString()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tipo = if (email == "admin@suafacul.com") 1 else 0

            val usuario = Usuario(
                nome = nome,
                email = email,
                senha = senha,
                tipo = tipo
            )


            thread {
                db.usuarioDao().inserir(usuario)
                runOnUiThread {
                    Toast.makeText(this, "Usuário cadastrado!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        btnVoltarLogin.setOnClickListener {
            finish()
        }
    }
}
