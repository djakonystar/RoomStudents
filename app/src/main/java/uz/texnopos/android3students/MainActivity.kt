package uz.texnopos.android3students

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import uz.texnopos.android3students.data.dao.StudentDao
import uz.texnopos.android3students.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = StudentAdapter()
    private lateinit var studentDao: StudentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentDao = StudentDatabase.getInstance(this).studentDao()

        binding.apply {
            adapter.models = studentDao.getAllStudents()

            adapter.setOnPhoneClickListener { phone ->

            }

            rvStudents.adapter = adapter
        }
    }
}
