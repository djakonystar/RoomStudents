package uz.texnopos.android3students.data.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import uz.texnopos.android3students.data.model.Student

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    fun getAllStudents(): Observable<List<Student>>

    @Query("SELECT * FROM students WHERE name LIKE :searchValue")
    fun searchStudents(searchValue: String): Observable<List<Student>>

    @Query("SELECT * FROM students WHERE is_favorite = 1")
    fun getFavorites(): Observable<List<Student>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateStudent(student: Student): Completable
}
