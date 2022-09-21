package uz.texnopos.android3students

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.texnopos.android3students.data.dao.StudentDao
import uz.texnopos.android3students.data.model.Student

@Database(entities = [Student::class], version = 2)
abstract class StudentDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: StudentDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): StudentDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    context,
                    StudentDatabase::class.java,
                    "android3.db"
                )
                    .createFromAsset("android3.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }

    abstract fun studentDao(): StudentDao
}
