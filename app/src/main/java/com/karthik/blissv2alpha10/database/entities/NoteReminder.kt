package com.karthik.blissv2alpha10.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "note_reminder_table")
class NoteReminder(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "date_created")
    val dateCreated: String = "",
    @ColumnInfo(name = "audio_uri")
    val audioUri: String = "",
    @ColumnInfo(name = "image_uri")
    val imageUri: String = "",
    @ColumnInfo(name = "reminder")
    val reminder: String = ""
) : Parcelable
