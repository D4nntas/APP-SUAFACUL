package com.suafacul

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var btnEntrar: Button
    private lateinit var btnCadastrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtEmail = findViewById(R.id.edtEmail)
        edtSenha = findViewById(R.id.edtSenha)
        btnEntrar = findViewById(R.id.btnEntrar)
        btnCadastrar = findViewById(R.id.btnCadastrar)
        btnEntrar.setOnClickListener {
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "suafacul_db"
            ).build()

            thread {
                val usuario = db.usuarioDao().login(email, senha)

                runOnUiThread {
                    if (usuario != null) {
                        if (usuario.tipo == 1) {
                            startActivity(Intent(this, HomeActivity::class.java))
                        } else {
                            val intent = Intent(this, HomeUserActivity::class.java)
                            intent.putExtra("nome", usuario.nome) // MANDANDO O NOME
                            startActivity(intent)
                        }
                        Toast.makeText(this, "Bem-vindo!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Login inválido!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btnCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}
