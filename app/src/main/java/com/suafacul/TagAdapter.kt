package com.suafacul

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TagAdapter(private val tags: List<Tag>) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tag_faculdade, parent, false)
        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val tag = tags[position]
        holder.bind(tag)
    }

    override fun getItemCount(): Int = tags.size

    class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tagName: TextView = itemView.findViewById(R.id.tag_name)

        fun bind(tag: Tag) {
            tagName.text = tag.nome
            // Aqui você pode adicionar lógica para mudar a aparência da tag se ela estiver selecionada
        }
    }
}