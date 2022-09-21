package uz.texnopos.android3students.data.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import uz.texnopos.android3students.data.model.Student

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    suspend fun getAllStudents(): List<Student>

    @Query("SELECT * FROM students WHERE name LIKE :searchValue")
    suspend fun searchStudents(searchValue: String): List<Student>

    @Query("SELECT * FROM students WHERE is_favorite = 1")
    suspend fun getFavorites(): List<Student>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateStudent(student: Student)
}
