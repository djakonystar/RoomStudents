package uz.texnopos.android3students

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
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
                    val newList = studentDao.searchStudents("%$searchValue%")
                    adapter.models = newList
                }
            }
        }
    }
}
