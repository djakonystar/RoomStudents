package uz.texnopos.android3students

import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.texnopos.android3students.data.dao.StudentDao
import uz.texnopos.android3students.data.model.Student

class MainViewModel(private val studentDao: StudentDao): ViewModel() {
    private var mutableStudents: MutableLiveData<List<Student>> = MutableLiveData()
    val students: LiveData<List<Student>>
        get() = mutableStudents

    fun getAllStudents() {
        viewModelScope.launch {
            try {
                val response = studentDao.getAllStudents()
                withContext(Dispatchers.Main) {
                    mutableStudents.value = response
                }
            } catch (e: Exception) {

            }
        }
    }
}
