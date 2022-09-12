package uz.texnopos.android3students

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import uz.texnopos.android3students.data.dao.StudentDao
import uz.texnopos.android3students.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = StudentAdapter()
    private lateinit var studentDao: StudentDao
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentDao = StudentDatabase.getInstance(this).studentDao()
        viewModel = MainViewModel(studentDao)

        binding.apply {
            viewModel.getAllStudents()

            viewModel.students.observe(this@MainActivity) {
                adapter.models = it
            }

            rvStudents.adapter = adapter

            adapter.setOnClick { student ->
                student.isFavorite = 1 - student.isFavorite
                viewModel.updateStudent(student)

                viewModel.update.observe(this@MainActivity) {
                    if (it == "OK") {
                        Toast.makeText(
                            this@MainActivity,
                            "Updated successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            fabFavorite.setOnClickListener {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }

            searchView.addTextChangedListener {
                it?.let { editable ->
                    val searchValue = editable.toString()
                    viewModel.searchStudents(searchValue)
                }
            }
        }
    }
}
