# Project RecyclerView

Ini adalah proyek sederhana untuk belajar RecyclerView menggunakan Git & Android Studio

## 👥 Tim
- Naraya Agastya
- Raditya Yuga Pramatama
- Rama Aditya Faisal
  
## 📱 Fitur-fitur yang sudah ada
### Halaman Utama
- Menampilkan daftar siswa:
  - Nama
  - NIS
  - Kelas

### Halaman Detail
- Menampilkan:
  - Foto siswa
  - Nama lengkap
  - NIS
  - Kelas

## 🔧 Teknologi
- Kotlin
- Android Studio
- Git + GitHub

### 1. **MainActivity**
- Menampilkan data siswa dari DummyData.studentList
- Mengatur RecyclerView menggunakan StudentAdapter
- Menyediakan fitur pencarian dan tombol tambah data.

```kotlin
val dataList = DummyData.studentList
adapter = StudentAdapter(this, dataList)

recyclerView = findViewById(R.id.recyclerView)
recyclerView.layoutManager = LinearLayoutManager(this)
recyclerView.adapter = adapter

```

```kotlin
searchInput.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        adapter.filter(s.toString())
    }
    ...
})
```
Input teks akan mem-filter data pada RecyclerView secara realtime

```kotlin
btnTambah.setOnClickListener {
    val intent = Intent(this, tambah::class.java)
    startActivity(intent)
}
```

Menavigasi ke activity tambah untuk menambahkan data baru.


### 2. **DummyData.kt**
- Menyediakan data siswa berupa list objek Student.
```kt
val studentList = mutableListOf(
        Student("Adit", "18212", "XI TJKT 1"),
        Student("Rama", "1231", "XII TOI 1"),
        Student("Radit", "19124", "X TJKT 4"),
        Student("Arya", "19212", "XII TITL 2"),
        Student("Faisal", "1121", "X TJKT 2"),
        Student("Sukar", "19121", "XII DKV 2"),
        Student("Yono", "19821", "X LAS 1"),
        Student("Sukamdi", "19812", "XII MESIN 1"),
        Student("Sulasih", "12112", "XII KA")
    )
```
Menavigasi ke activity tambah untuk menambahkan data baru.

### 3. **StudentAdapter.kt**
- Logika Dari beberapa fungsi
  Sebagai Contoh
```kotlin
override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val student = filteredList[position]
    holder.tvName.text = student.nama
    holder.tvNis.text = "NIS: ${student.nis}"
    holder.tvKelas.text = "Kelas: ${student.kelas}"
}
```

Untuk Menampilkan Data Siswa

### 4. **DetailActivity.kt**
- Menyediakan data siswa berupa list objek Student.
- Menerima data Intent dari adapter.
- Menampilkan nama, NIS, dan kelas pada layout activity_detail.xml.
```kotlin
val nama = intent.getStringExtra("student_nama")
tvNama.text = nama
```

## 🆕 Fitur Baru yang Ditambahkan

### ✅ 1. Tombol Aksi per Siswa
- Menambahkan dua tombol baru di `item_student.xml`:
  - **Edit**
  - **Hapus**
```xml
                <Button
                    android:id="@+id/removeButton"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Hapus"/>

                <Button
                    android:id="@+id/editButton"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Edit"/>
```

### ✅ 4. Fitur Edit
- Pengguna bisa mengubah `nama` dan `kelas`.
- 🧩 Cuplikan Kode Edit (StudentAdapter.kt).

```kotlin
  fun updateData(newList: List<Student>) {
        studentList = newList.toMutableList()
        filteredList = newList.toMutableList()
        notifyDataSetChanged()
    }
```

### ✅ 4. Fitur Tambah
- Pengguna Dapat manambahkan Field `Nama`, `NIS`, `Kelas`
- Setelah pengguna mengisi field tersebut dapat menekan Tombol **Simpan**
- Setelah itu data akan ditambahkan
  - 🧩 Cuplikan Kode Add (tambah.kt).


```kotlin
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
```

### ✅ 5. Fitur Hapus
- Saat tombol "Hapus" ditekan:
  - Muncul konfirmasi.
  - Jika disetujui, siswa dihapus dari daftar (RecyclerView diperbarui secara langsung).
    - 🧩 Cuplikan Kode Add (StudentAdapter.kt).
   
      ```kotlin
      fun removeItem(position: Int) {
        if (position != RecyclerView.NO_POSITION && position < filteredList.size) {
            val removedStudent = filteredList.removeAt(position)
            studentList.remove(removedStudent)
            notifyItemRemoved(position)
        }
      }
    
      ```

## 📸 Screenshot

<img width="310" height="697" alt="image" src="https://github.com/user-attachments/assets/1e2cde00-5bbc-42d2-b6a1-6dad69b48e71" />
<img width="315" height="696" alt="image" src="https://github.com/user-attachments/assets/7085574c-b5bc-45e6-93b1-edd10e91055c" />
<img width="305" height="692" alt="image" src="https://github.com/user-attachments/assets/4ef0e052-1f3c-4cf0-8c7a-ddbb9469707b" />



