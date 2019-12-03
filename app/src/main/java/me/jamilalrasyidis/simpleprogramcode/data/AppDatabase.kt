package me.jamilalrasyidis.simpleprogramcode.data

import androidx.room.Database
import androidx.room.RoomDatabase
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.CodeEntity
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.ProgramEntity
import me.jamilalrasyidis.simpleprogramcode.data.services.dao.CodeDao
import me.jamilalrasyidis.simpleprogramcode.data.services.dao.ProgramDao

@Database(
    entities = [ProgramEntity::class, CodeEntity::class],
    version = 6
)
abstract class AppDatabase : RoomDatabase() {
    abstract val programDao: ProgramDao
    abstract val codeDao: CodeDao
}