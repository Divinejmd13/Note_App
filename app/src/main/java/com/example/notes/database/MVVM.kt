package com.example.notes.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> get() = _notes

    init {
        viewModelScope.launch {
            _notes.value = repository.getAllNotes()
        }
    }

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
        _notes.value += note
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
        _notes.value = _notes.value.map { if (it.id == note.id) note else it }
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
        _notes.value = _notes.value.filter { it.id != note.id }
    }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
