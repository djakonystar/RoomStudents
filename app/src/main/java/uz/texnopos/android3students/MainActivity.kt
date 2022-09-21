package uz.texnopos.android3students

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            lifecycleScope.launch {
                try {
                    val response = studentDao.getAllStudents()
                    withContext(Dispatchers.Main) {
                        adapter.models = response
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }

            rvStudents.adapter = adapter

            adapter.setOnClick { student ->
                student.isFavorite = 1 - student.isFavorite
                lifecycleScope.launch {
                    studentDao.updateStudent(student)
                }
            }

            fabFavorite.setOnClickListener {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }

//            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    newText?.let { searchValue ->
//                        val newList = studentDao.searchStudents("%$searchValue%")
//                        adapter.models = newList
//                        return true
//                    }
//                    return false
//                }
//
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    query?.let { searchValue ->
//                        val newList = studentDao.searchStudents("%$searchValue%")
//                        adapter.models = newList
//                        return true
//                    }
//                    return false
//                }
//            })

            searchView.addTextChangedListener {
                it?.let { editable ->
                    val searchValue = editable.toString()
                    lifecycleScope.launch {
                        val newList = studentDao.searchStudents("%$searchValue%")
                        withContext(Dispatchers.Main) {
                            adapter.models = newList
                        }
                    }
                }
            }
        }
    }
}
