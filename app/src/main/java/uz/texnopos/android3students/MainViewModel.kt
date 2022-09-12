package uz.texnopos.android3students

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import uz.texnopos.android3students.data.dao.StudentDao
import uz.texnopos.android3students.data.model.Student

class MainViewModel(private val studentDao: StudentDao): ViewModel() {
    private var mutableStudents: MutableLiveData<List<Student>> = MutableLiveData()
    val students: LiveData<List<Student>>
        get() = mutableStudents

    private var mutableUpdate: MutableLiveData<String> = MutableLiveData()
    val update: LiveData<String> = mutableUpdate

    fun getAllStudents() {
        studentDao.getAllStudents()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    mutableStudents.value = response
                },
                { error ->

                }
            )
    }

    fun updateStudent(student: Student) {
        studentDao.updateStudent(student)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                mutableUpdate.value = "OK"
            }
    }

    fun searchStudents(searchValue: String) {
        studentDao.searchStudents("%$searchValue%")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    mutableStudents.value = list
                },
                { error ->

                }
            )
    }
}
