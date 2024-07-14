package com.example.notes.database

class NoteRepository(private val noteDAO: NoteDAO) {
    suspend fun insert(note: Note) = noteDAO.insert(note)
    suspend fun update(note: Note) = noteDAO.update(note)
    suspend fun delete(note: Note) = noteDAO.delete(note)
    suspend fun getAllNotes(): List<Note> = noteDAO.getAllNotes()
}
