package uz.texnopos.android3students.data.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import uz.texnopos.android3students.data.model.Student

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    fun getAllStudents(): List<Student>

    @Query("SELECT * FROM students WHERE name LIKE :searchValue")
    fun searchStudents(searchValue: String): List<Student>

    @Query("SELECT * FROM students WHERE is_favorite = 1")
    fun getFavorites(): List<Student>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateStudent(student: Student)
}
