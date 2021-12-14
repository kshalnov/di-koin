package ru.gb.course1.di1.di

import android.content.Context
import androidx.room.Room
import ru.gb.course1.di1.data.local.MemoryCacheNoteRepoImpl
import ru.gb.course1.di1.data.room.NoteDb
import ru.gb.course1.di1.data.room.RoomNoteRepoImpl
import ru.gb.course1.di1.domain.NoteRepo
import ru.gb.course1.di1.ui.Presenter
import ru.gb.course1.dil.fabric
import ru.gb.course1.dil.get
import ru.gb.course1.dil.singleton
import java.util.*

fun module(context: Context) {
    val dbPath = "note.db"

    singleton { context }

    singleton {
        Room.databaseBuilder(get(), NoteDb::class.java, dbPath).build()
    }

    singleton { get<NoteDb>().noteDao() }
    fabric<NoteRepo> { RoomNoteRepoImpl(get()) }
    fabric<NoteRepo>("memory") { MemoryCacheNoteRepoImpl() }
    fabric { UUID.randomUUID() }
    fabric("color") { Random().nextInt() }
    fabric { Presenter("${get<UUID>()}", get("color")) }
}