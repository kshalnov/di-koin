package ru.gb.course1.di1.ui

import android.content.Intent
import android.os.Bundle
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import ru.gb.course1.di1.databinding.ActivityMainBinding
import ru.gb.course1.di1.domain.NoteEntity
import ru.gb.course1.di1.domain.NoteRepo
import ru.gb.course1.dil.get
import ru.gb.course1.dil.inject
import java.util.*

class Presenter(val message: String, val color: Int)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val noteRepo: NoteRepo by inject()
    private val presenter: Presenter by inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread {
            updateResult()
        }.start()

        binding.launchActivityButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

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

        supportActionBar?.title = presenter.message
        binding.launchActivityButton.setBackgroundColor(presenter.color)
        binding.clearButton.setBackgroundColor(presenter.color)
        binding.addButton.setBackgroundColor(presenter.color)
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