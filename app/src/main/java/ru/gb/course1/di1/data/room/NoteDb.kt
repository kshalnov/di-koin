package ru.gb.course1.di1.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.gb.course1.di1.domain.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDb: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}