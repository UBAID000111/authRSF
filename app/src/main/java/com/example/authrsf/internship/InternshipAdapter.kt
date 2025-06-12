package com.example.authrsf.internship

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.authrsf.R
import com.example.authrsf.model.Internship


class InternshipAdapter(private val context: Context, private val internshipList: List<Internship>,
    private val onApplyClicked: (Internship ) -> Unit) :
    RecyclerView.Adapter<InternshipAdapter.InternshipViewHolder>() {

    class InternshipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val typeTextView: TextView = itemView.findViewById(R.id.type)
        val locationTextView: TextView = itemView.findViewById(R.id.location)
        val durationTextView: TextView = itemView.findViewById(R.id.duration)
        val requirementsTextView: TextView = itemView.findViewById(R.id.requirements)

        val applyButton2: Button = itemView.findViewById(R.id.applyButton2)


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
        holder.requirementsTextView.text = internship.requirements
        holder.applyButton2.setOnClickListener {
            onApplyClicked(internshipList[position])
        }

    }



    override fun getItemCount(): Int = internshipList.size
}


