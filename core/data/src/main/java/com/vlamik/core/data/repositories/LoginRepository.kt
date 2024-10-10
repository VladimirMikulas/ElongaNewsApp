package com.vlamik.core.data.repositories

interface LoginRepository {
    fun getAuthentication(email: String, password: String): Boolean
}