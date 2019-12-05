package me.jamilalrasyidis.simpleprogramcode.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CodeWithProgramEntity(
    @Embedded val codeEntity: CodeEntity,
    @Relation(
        entityColumn = "program_id",
        parentColumn = "id"
    )
    val programEntity: ProgramEntity
)