package com.vlamik.core.domain

import com.vlamik.core.data.repositories.LoginRepository
import javax.inject.Inject

class GetAuthentication @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    operator fun invoke(email: String, password: String): Boolean {
        return loginRepository.getAuthentication(email, password)
    }
}
