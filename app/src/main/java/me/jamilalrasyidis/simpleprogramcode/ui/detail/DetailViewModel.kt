package me.jamilalrasyidis.simpleprogramcode.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.CodeEntity
import me.jamilalrasyidis.simpleprogramcode.data.repository.CodeRepository

class DetailViewModel(
    private val codeRepository: CodeRepository
) : ViewModel() {

    val codesLiveData = MutableLiveData<String>()
    val currentTabNameLiveData = MutableLiveData<String>()
    val currentNameAndCodeLiveData = MutableLiveData<List<String>>()
    val programIdLiveData = MutableLiveData<String>()

    fun setCodes(codes: String) {
        codesLiveData.value = codes
    }

    fun setCurrentTabName(tabName: String) {
        currentTabNameLiveData.value = tabName
    }

    fun setProgramId(programId: String) {
        programIdLiveData.value = programId
    }

    fun getNames(programId: String) : LiveData<List<String>> {
        return codeRepository.getNames(programId)
    }

    fun getCodes(programId: String, name: String) : LiveData<String> {
        return codeRepository.getCodes(programId, name)
    }

    fun getCodeByProgramId(programId: String) : LiveData<List<CodeEntity>> {
        return codeRepository.getCodeByProgramId(programId)
    }

    init {
        viewModelScope.launch {
            codeRepository.getCodes()
        }
    }

    companion object {
        const val TAG = "DetailViewModel"
    }
}