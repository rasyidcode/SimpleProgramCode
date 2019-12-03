package me.jamilalrasyidis.simpleprogramcode.data.services.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.CodeEntity

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

    @get:Query("SELECT * FROM CodeEntity")
    val codes: List<CodeEntity>?

}