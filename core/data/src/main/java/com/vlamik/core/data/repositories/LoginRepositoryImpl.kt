package com.vlamik.core.data.repositories


class LoginRepositoryImpl : LoginRepository {
    companion object {
        private const val LOGIN_EMAIL = "elonga@elonga.com"
        private const val LOGIN_PASSWORD = "ElongaTheBest"
    }

    override fun getAuthentication(email: String, password: String): Boolean =
        email == LOGIN_EMAIL && password == LOGIN_PASSWORD

}