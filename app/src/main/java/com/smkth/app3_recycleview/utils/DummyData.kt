package com.smkth.app3_recycleview.utils

import com.smkth.app3_recycleview.model.Student

object DummyData {
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

    fun generateDummyList(): List<Student> {
        return studentList
    }
}
