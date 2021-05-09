package com.karthik.blissv2alpha10.database

import android.content.Context
import androidx.room.Database
import androidx.room.Fts4
import androidx.room.Room
import androidx.room.RoomDatabase
import com.karthik.blissv2alpha10.database.daos.NoteReminderDao
import com.karthik.blissv2alpha10.database.daos.TodoDao
import com.karthik.blissv2alpha10.database.entities.NoteReminder
import com.karthik.blissv2alpha10.database.entities.Todo

@Fts4
@Database(entities = [NoteReminder::class, Todo::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

//    returns the daos
    abstract fun getNoteReminderDao() : NoteReminderDao
    abstract fun getTodoDao() : TodoDao

    companion object {
        /*
            Singleton prevents multiple instances of database opening at the
            same time.
        */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it, if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}