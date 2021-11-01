package ru.gb.course1.di1.domain

interface NoteRepo {
    fun getNotes(): List<NoteEntity>
    fun clear()
    fun put(note: NoteEntity)
}