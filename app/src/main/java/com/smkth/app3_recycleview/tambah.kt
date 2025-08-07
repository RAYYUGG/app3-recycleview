package com.smkth.app3_recycleview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smkth.app3_recycleview.adapter.StudentAdapter
import com.smkth.app3_recycleview.model.Student
import com.smkth.app3_recycleview.utils.DummyData

class tambah : AppCompatActivity() {
    private val studentList = mutableListOf<Student>()
    private lateinit var adapter : StudentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val studentList = mutableListOf<Student>().apply {
            addAll(DummyData.generateDummyList())
        }

        val dataList = DummyData.studentList
        adapter = StudentAdapter(this, dataList)

        val inputName = findViewById<EditText>(R.id.etNama)
        val inputNIS = findViewById<EditText>(R.id.etNIS)
        val inputKelas = findViewById<EditText>(R.id.etKelas)

        val btnTamnbah = findViewById<Button>(R.id.btnSimpanData)
        btnTamnbah.setOnClickListener {
            val name = inputName.text.toString()
            val nis = inputNIS.text.toString()
            val kelas = inputKelas.text.toString()

            if(name.isNotEmpty() && nis.isNotEmpty() && kelas.isNotEmpty()) {
                DummyData.studentList.add(Student(name, nis, kelas))
                adapter.updateData(studentList)
                inputKelas.text.clear()
                inputNIS.text.clear()
                inputName.text.clear()
                Toast.makeText(this, "Succes Save", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }

        val nama = intent.getStringExtra("nama")
        val kelas = intent.getStringExtra("kelas")
        val nis = intent.getStringExtra("nis")

        inputName.setText(nama)
        inputNIS.setText(nis)
        inputKelas.setText(kelas)

    }
}