package ru.gb.course1.di1.ui

import android.os.Bundle
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import ru.gb.course1.di1.databinding.ActivityMainBinding
import ru.gb.course1.di1.domain.NoteEntity
import ru.gb.course1.di1.domain.NoteRepo
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val noteRepo: NoteRepo by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread {
            updateResult()
        }.start()

        binding.addButton.setOnClickListener {
            Thread {
                noteRepo.put(generateNote())
                updateResult()
            }.start()
        }
        binding.clearButton.setOnClickListener {
            Thread {
                noteRepo.clear()
                updateResult()
            }.start()
        }
    }

    @WorkerThread
    private fun updateResult() {
        val notes = noteRepo.getNotes()
        val sb = StringBuilder()
        notes.forEach {
            sb.appendLine(it.title)
            sb.appendLine(it.id)
            sb.appendLine()
        }
        runOnUiThread {
            binding.resultTextView.text = sb.toString()
        }
    }

    private var counter = 0
    private fun generateNote(): NoteEntity {
        return NoteEntity(get<UUID>().toString(), "Заголовок ${counter++}", "Текст заметки")
    }
}