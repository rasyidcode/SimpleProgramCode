package me.jamilalrasyidis.simpleprogramcode.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.CodeEntity
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.ProgramWithCodeEntity
import me.jamilalrasyidis.simpleprogramcode.data.services.dao.CodeDao
import org.koin.core.KoinComponent

class CodeRepository(
    private val codeDao: CodeDao
) : KoinComponent {

    private val database by lazy { FirebaseFirestore.getInstance() }

    private val codeRefs by lazy { database.collection("codes") }

    fun getNames(programId: String): LiveData<List<String>> {
        return codeDao.getNames(programId)
    }

    fun getCodes(programId: String, name: String): LiveData<String> {
        return codeDao.getCodes(programId, name)
    }

    fun getProgramsWithCodes(programId: String) : LiveData<ProgramWithCodeEntity> {
        return codeDao.getProgramsWithCodes(programId)
    }

    fun getAllFavorites() : LiveData<List<CodeEntity>> {
        return codeDao.getAllFavorites()
    }

    fun getCodeByProgramId(programId: String): LiveData<List<CodeEntity>> {
        return codeDao.getCodeByProgramId(programId)
    }

    suspend fun updateFavorite(isFavored: Boolean, codeId: String) {
        codeDao.updateFavorite(isFavored, codeId)
    }

    suspend fun getCodes() {
        val codes = mutableListOf<CodeEntity>()
        val codeFromDb: List<CodeEntity>? = codeDao.getAllCodes()

        codeRefs.get().addOnSuccessListener {
            if (codeFromDb?.isNotEmpty()!!) {
                for (i in 0 until it.documents.size) {
                    try {
                        codes.add(
                            CodeEntity(
                                id = it.documents[i].id,
                                name = it.documents[i]["name"].toString(),
                                codes = it.documents[i]["codes"].toString(),
                                programId = it.documents[i]["program_id"].toString(),
                                isFavored = codeFromDb[i].isFavored ?: false,
                                output = it.documents[i]["output"].toString()
                            )
                        )
                    } catch (e: IndexOutOfBoundsException) {
                        codes.add(
                            CodeEntity(
                                id = it.documents[i].id,
                                name = it.documents[i]["name"].toString(),
                                codes = it.documents[i]["codes"].toString(),
                                programId = it.documents[i]["program_id"].toString(),
                                isFavored = false,
                                output = it.documents[i]["output"].toString()
                            )
                        )
                    }
                }
            } else {
                for (document in it) {
                    codes.add(
                        CodeEntity(
                            id = document.id,
                            name = document["name"].toString(),
                            codes = document["codes"].toString(),
                            programId = document["program_id"].toString(),
                            output = document["output"].toString()
                        )
                    )
                }
            }

            runBlocking {
                codeDao.insert(codes)
            }
        }
    }

    companion object {
        const val TAG = "CodeRepository"
    }

}