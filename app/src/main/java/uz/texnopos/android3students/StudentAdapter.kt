package uz.texnopos.android3students

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.android3students.data.model.Student
import uz.texnopos.android3students.databinding.ItemStudentBinding

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    var models = listOf<Student>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class StudentViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student) {
            binding.apply {
                tvName.text = student.name
                tvPhone.text = student.phone

                if (student.isFavorite == 1) {
                    ivFavorite.setImageResource(R.drawable.ic_favorite)
                } else {
                    ivFavorite.setImageResource(R.drawable.ic_favorite_border)
                }

                ivFavorite.setOnClickListener {
                    onClick(student)
                    if (student.isFavorite == 1) {
                        ivFavorite.setImageResource(R.drawable.ic_favorite)
                    } else {
                        ivFavorite.setImageResource(R.drawable.ic_favorite_border)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = models.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        val binding = ItemStudentBinding.bind(view)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(models[position])
    }

    private var onClick: (student: Student) -> Unit = {}
    fun setOnClick(onClick: (student: Student) -> Unit) {
        this.onClick = onClick
    }
}
