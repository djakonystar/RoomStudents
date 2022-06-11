package uz.texnopos.android3students.data.dao

import androidx.room.Dao
import androidx.room.Query
import uz.texnopos.android3students.data.model.Student

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    fun getAllStudents(): List<Student>
}
