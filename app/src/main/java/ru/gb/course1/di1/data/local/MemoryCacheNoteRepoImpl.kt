package ru.gb.course1.di1.data.local

import ru.gb.course1.di1.domain.NoteEntity
import ru.gb.course1.di1.domain.NoteRepo

class MemoryCacheNoteRepoImpl : NoteRepo {

    private val cache: MutableList<NoteEntity> = mutableListOf()

    override fun getNotes(): List<NoteEntity> = ArrayList(cache)

    override fun clear() {
        cache.clear()
    }

    override fun put(note: NoteEntity) {
        cache.add(note)
    }
}