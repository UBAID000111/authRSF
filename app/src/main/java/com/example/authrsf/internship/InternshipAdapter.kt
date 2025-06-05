package com.example.authrsf.internship

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.authrsf.R
import com.example.authrsf.model.Internship

class InternshipAdapter(private val internshipList: List<Internship>) :
    RecyclerView.Adapter<InternshipAdapter.InternshipViewHolder>() {

    class InternshipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val typeTextView: TextView = itemView.findViewById(R.id.type)
        val locationTextView: TextView = itemView.findViewById(R.id.location)
        val durationTextView: TextView = itemView.findViewById(R.id.duration)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description)
        val requirementsTextView: TextView = itemView.findViewById(R.id.requirements)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InternshipViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_internship, parent, false)
        return InternshipViewHolder(view)
    }

    override fun onBindViewHolder(holder: InternshipViewHolder, position: Int) {
        val internship = internshipList[position]
        holder.typeTextView.text = internship.type
        holder.locationTextView.text = internship.location
        holder.durationTextView.text = internship.duration
        holder.descriptionTextView.text = internship.description
        holder.requirementsTextView.text = internship.requirements
    }

    override fun getItemCount(): Int = internshipList.size
}