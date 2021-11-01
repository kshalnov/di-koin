package ru.gb.course1.di1.data.room

import ru.gb.course1.di1.domain.NoteEntity
import ru.gb.course1.di1.domain.NoteRepo

class RoomNoteRepoImpl(
    private val noteDao: NoteDao
) : NoteRepo {

    override fun getNotes(): List<NoteEntity> {
        return noteDao.getNotes()
    }

    override fun clear() {
        noteDao.clear()
    }

    override fun put(note: NoteEntity) {
        noteDao.put(note)
    }
}