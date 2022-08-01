package uz.texnopos.android3students.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    val phone: String,
    @ColumnInfo(name = "is_favorite") var isFavorite: Int
)
