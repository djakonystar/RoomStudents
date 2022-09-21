package uz.texnopos.android3students

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
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
            studentDao.getAllStudents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        adapter.models = response
                    },
                    { error ->
                        Toast.makeText(
                            this@MainActivity,
                            error.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )

            adapter.setOnPhoneClickListener { phone ->

            }

            rvStudents.adapter = adapter

            adapter.setOnClick { student ->
                student.isFavorite = 1 - student.isFavorite
                studentDao.updateStudent(student)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Toast.makeText(
                            this@MainActivity,
                            "Updated successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
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
                    studentDao.searchStudents("%$searchValue%")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { list ->
                                adapter.models = list
                            },
                            { error ->
                                Toast.makeText(
                                    this@MainActivity,
                                    error.localizedMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                }
            }
        }
    }
}
