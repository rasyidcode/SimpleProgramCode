package me.jamilalrasyidis.simpleprogramcode.ui.home

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.ProgramEntity
import me.jamilalrasyidis.simpleprogramcode.data.repository.ProgramRepository

class HomeViewModel(
    private val programRepository: ProgramRepository
) : ViewModel() {

    val programs: LiveData<List<ProgramEntity>> = programRepository.programs
    val isSuccessToFirebase = MutableLiveData<Boolean>()

    fun runGetProgramsAgain() {
        viewModelScope.launch {
            programRepository.getPrograms()
        }
    }

    init {
        viewModelScope.launch {
            programRepository.getPrograms()
        }
    }
}