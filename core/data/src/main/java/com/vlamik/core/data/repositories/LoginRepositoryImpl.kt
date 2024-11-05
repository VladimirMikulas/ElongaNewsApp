package com.vlamik.core.data.repositories

import com.vlamik.core.commons.UserEmail
import com.vlamik.core.commons.UserPassword
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(
    @UserEmail private val userEmail: String,
    @UserPassword private val userPassword: String
) : LoginRepository {

    override fun getAuthentication(email: String, password: String): Boolean =
        email == userEmail && password == userPassword

}