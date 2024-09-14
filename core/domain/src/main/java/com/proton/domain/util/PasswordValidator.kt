package com.proton.domain.util

import com.proton.domain.error.PasswordError

class PasswordValidator {
    companion object {
        private const val PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!?])[A-Za-z\\d@#$%^&+=!?]{8,}$"

        fun isPasswordValid(password: String): Result<Unit, PasswordError> {
            val pattern = PASSWORD_PATTERN.toRegex()
            return if (pattern.matches(password)) {
                Result.Success(Unit)
            } else {
                when {
                    password.isBlank() -> Result.Error(PasswordError.BLANK)
                    password.length < 8 -> Result.Error(PasswordError.TOO_SHORT)
                    !containsLowercase(password) -> Result.Error(PasswordError.NO_LOWERCASE)
                    !containsUppercase(password) -> Result.Error(PasswordError.NO_UPPERCASE)
                    !containsDigit(password) -> Result.Error(PasswordError.NO_DIGIT)
                    !containsSpecialChar(password) -> Result.Error(PasswordError.NO_SPECIAL_CHAR)
                    else -> Result.Error(PasswordError.INVALID_FORMAT)
                }
            }
        }

        private fun containsLowercase(password: String): Boolean {
            return password.any { it.isLowerCase() }
        }

        private fun containsUppercase(password: String): Boolean {
            return password.any { it.isUpperCase() }
        }

        private fun containsDigit(password: String): Boolean {
            return password.any { it.isDigit() }
        }

        private fun containsSpecialChar(password: String): Boolean {
            val specialChars = setOf('@', '#', '$', '%', '^', '&', '+', '=', '!', '?')
            return password.any { it in specialChars }
        }
    }
}