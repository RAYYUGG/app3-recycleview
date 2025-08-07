package com.smkth.app3_recycleview.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.smkth.app3_recycleview.DetailActivity
import com.smkth.app3_recycleview.R
import com.smkth.app3_recycleview.model.Student
import com.smkth.app3_recycleview.tambah


class StudentAdapter(
    private val context: Context,
    private var studentList: MutableList<Student>
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    private var filteredList: MutableList<Student> = studentList.toMutableList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvNama)
        val tvNis: TextView = itemView.findViewById(R.id.tvNis)
        val tvKelas: TextView = itemView.findViewById(R.id.tvKelas)
        val btnRemove: Button = itemView.findViewById(R.id.removeButton)
        val btnEdit: Button = itemView.findViewById(R.id.editButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = filteredList[position]
        holder.tvName.text = student.nama
        holder.tvNis.text = "NIS: ${student.nis}"
        holder.tvKelas.text = "Kelas: ${student.kelas}"

        holder.btnEdit.setOnClickListener {
            val intent = Intent(holder.itemView.context, tambah::class.java)
            intent.putExtra("nama", student.nama)
            intent.putExtra("nis", student.nis)
            intent.putExtra("kelas", student.kelas)
            holder.itemView.context.startActivity(intent)
        }

        holder.btnRemove.setOnClickListener {
            removeItem(holder.adapterPosition)
        }

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Memilih ${student.nama}", Toast.LENGTH_SHORT).show()

            AlertDialog.Builder(context)
                .setTitle("Lihat Detail?")
                .setMessage("Ingin melihat detail dari ${student.nama}?")
                .setPositiveButton("Lihat") { _, _ ->
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("student_nama", student.nama)
                    intent.putExtra("student_nis", student.nis)
                    intent.putExtra("student_kelas", student.kelas)
                    context.startActivity(intent)
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    fun filter(query: String) {
        filteredList = if (query.isBlank()) {
            studentList.toMutableList()
        } else {
            studentList.filter {
                it.nama.contains(query, ignoreCase = true) ||
                        it.nis.contains(query, ignoreCase = true) ||
                        it.kelas.contains(query, ignoreCase = true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if (position != RecyclerView.NO_POSITION && position < filteredList.size) {
            val removedStudent = filteredList.removeAt(position)
            studentList.remove(removedStudent)
            notifyItemRemoved(position)
        }
    }

    fun updateData(newList: List<Student>) {
        studentList = newList.toMutableList()
        filteredList = newList.toMutableList()
        notifyDataSetChanged()
    }
}
