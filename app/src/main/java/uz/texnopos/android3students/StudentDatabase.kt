package uz.texnopos.android3students

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.texnopos.android3students.data.dao.StudentDao
import uz.texnopos.android3students.data.model.Student

@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase: RoomDatabase() {
    companion object {
        fun getInstance(context: Context): StudentDatabase {
            return Room.databaseBuilder(
                context,
                StudentDatabase::class.java,
                "android3.db"
            )
                .createFromAsset("android3.db")
                .allowMainThreadQueries()
                .build()
        }
    }

    abstract fun studentDao(): StudentDao
}