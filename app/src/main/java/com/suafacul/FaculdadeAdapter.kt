package com.suafacul

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class FaculdadeAdapter(private val faculdades: List<Faculdade>) :
    RecyclerView.Adapter<FaculdadeAdapter.FaculdadeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaculdadeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_faculdade_card, parent, false)
        return FaculdadeViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaculdadeViewHolder, position: Int) {
        val faculdade = faculdades[position]
        holder.bind(faculdade)
    }

    override fun getItemCount(): Int = faculdades.size

    class FaculdadeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val logoFaculdade: ImageView = itemView.findViewById(R.id.imageViewFaculdade)
        private val saveIcon: ImageButton = itemView.findViewById(R.id.imageButtonSave)

        fun bind(faculdade: Faculdade) {
            logoFaculdade.setImageResource(faculdade.logo)

            if (faculdade.isSalva) {
                saveIcon.setImageResource(R.drawable.ic_saved_active)
            } else {
                saveIcon.setImageResource(R.drawable.ic_saved_inactive)
            }

            saveIcon.setOnClickListener {
                faculdade.isSalva = !faculdade.isSalva
                if (faculdade.isSalva) {
                    saveIcon.setImageResource(R.drawable.ic_saved_active)
                } else {
                    saveIcon.setImageResource(R.drawable.ic_saved_inactive)
                }
            }
        }
    }
}