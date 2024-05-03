package com.beam.todo_app.addTask.data.di

import android.content.Context
import androidx.room.Room
import com.beam.todo_app.addTask.data.TaskDao
import com.beam.todo_app.addTask.data.TodoDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun provideTodoDB(@ApplicationContext appContext: Context): TodoDB {
        return Room.databaseBuilder(appContext, TodoDB::class.java, "TodoDB").build()
    }

    @Provides
    fun provideTaskDao(todoDB: TodoDB): TaskDao {
        return todoDB.taskDao()
    }
}