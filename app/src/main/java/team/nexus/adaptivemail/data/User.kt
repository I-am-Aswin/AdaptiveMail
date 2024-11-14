package team.nexus.adaptivemail.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "user_table" )
data class User (
    @PrimaryKey( autoGenerate = true ) val id: Int?,
    @ColumnInfo( name = "user_name" ) val uname: String?,
    @ColumnInfo( name = "email" ) val email: String?,
    @ColumnInfo( name = "password" ) val passwd: String?,
)