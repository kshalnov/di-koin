package ru.gb.course1.di1.di

import androidx.room.Room
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.gb.course1.di1.data.local.MemoryCacheNoteRepoImpl
import ru.gb.course1.di1.data.room.NoteDb
import ru.gb.course1.di1.data.room.RoomNoteRepoImpl
import ru.gb.course1.di1.domain.NoteRepo
import java.util.*

val dbModule = module {
    val dbPath = "note.db"

    single { Room.databaseBuilder(get(), NoteDb::class.java, dbPath).build() }
    single { get<NoteDb>().noteDao() }
    factory<NoteRepo> { RoomNoteRepoImpl(get()) }
}

val appModule = module {
    factory { UUID.randomUUID() }
    single<NoteRepo>(named("memory")) { MemoryCacheNoteRepoImpl() }
}