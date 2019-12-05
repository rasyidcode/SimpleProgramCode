package me.jamilalrasyidis.simpleprogramcode.data.services.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.CodeEntity
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.CodeWithProgramEntity
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.ProgramWithCodeEntity

@Dao
interface CodeDao {

    @Query("SELECT * FROM CodeEntity WHERE program_id LIKE :programId")
    fun getCodeByProgramId(programId: String) : LiveData<List<CodeEntity>>

    @Query("SELECT name FROM CodeEntity WHERE program_id LIKE :programId")
    fun getNames(programId: String) : LiveData<List<String>>

    @Query("SELECT codes FROM CodeEntity WHERE program_id LIKE :programId AND name LIKE :name")
    fun getCodes(programId: String, name: String) : LiveData<String>

    @Query("UPDATE CodeEntity SET is_favored = :isFavored WHERE id = :codeId")
    suspend fun updateFavorite(isFavored: Boolean, codeId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(codes: List<CodeEntity>)

    @Query("SELECT * FROM CodeEntity")
    suspend fun getAllCodes() : List<CodeEntity>?

    @Query("SELECT * FROM CodeEntity WHERE is_favored = :isFavored")
    fun getAllFavorites(isFavored: Boolean = true) : LiveData<List<CodeEntity>>

    @Transaction
    @Query("SELECT * FROM ProgramEntity WHERE id = :programId")
    fun getProgramsWithCodes(programId: String) : LiveData<ProgramWithCodeEntity>
}