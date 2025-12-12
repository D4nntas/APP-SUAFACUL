package com.suafacul

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuarioAdapter(
    private val lista: List<Usuario>,
    private val onEditar: (Usuario) -> Unit,
    private val onDeletar: (Usuario) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    class UsuarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome = view.findViewById<TextView>(R.id.txtNomeLista)
        val email = view.findViewById<TextView>(R.id.txtEmailLista)
        val btnEditar = view.findViewById<Button>(R.id.btnEditar)
        val btnDeletar = view.findViewById<Button>(R.id.btnDeletar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = lista[position]
        holder.nome.text = usuario.nome
        holder.email.text = usuario.email

        holder.btnEditar.setOnClickListener {
            onEditar(usuario)
        }

        holder.btnDeletar.setOnClickListener {
            onDeletar(usuario)
        }
    }

    override fun getItemCount(): Int = lista.size
}
