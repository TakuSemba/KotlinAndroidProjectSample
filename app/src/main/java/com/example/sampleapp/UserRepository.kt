package com.example.sampleapp

import kotlinx.coroutines.runBlocking

class UserRepository {

    suspend fun register(): User = runBlocking { User("registered-user") }
}