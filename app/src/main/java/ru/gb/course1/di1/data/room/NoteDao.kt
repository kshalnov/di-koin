package ru.gb.course1.di1.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.gb.course1.di1.domain.NoteEntity

@Dao
interface NoteDao {
    @Query("DELETE FROM notes")
    fun clear()

    @Query("SELECT * FROM notes")
    fun getNotes(): List<NoteEntity>

    @Insert
    fun put(noteEntity: NoteEntity)
}