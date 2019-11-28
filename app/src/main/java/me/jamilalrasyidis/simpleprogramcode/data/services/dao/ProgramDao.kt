package me.jamilalrasyidis.simpleprogramcode.data.services.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.ProgramEntity

@Dao
interface ProgramDao {

    @get:Query("SELECT * FROM ProgramEntity")
    val programs: LiveData<List<ProgramEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programs: List<ProgramEntity>)

}