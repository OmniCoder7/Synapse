package com.proton.domain.util

import android.util.Patterns
import com.proton.domain.error.EmailFormatError

class EmailValidator {
    companion object {

        fun isEmailValid(email: String): Result<Unit, EmailFormatError> {
            val pattern = Patterns.EMAIL_ADDRESS.toRegex()
            return if (pattern.matches(email)) {
                if (emailContainsIllegalCharacters(email)) {
                    Result.Error(EmailFormatError.ILLEGAL_CHARACTERS)
                } else {
                    Result.Success(Unit)
                }
            } else {
                when {
                    email.isBlank() -> Result.Error(EmailFormatError.BLANK)
                    !email.contains("@") -> Result.Error(EmailFormatError.NO_AT_SYMBOL)
                    email.length > 254 -> Result.Error(EmailFormatError.TOO_LONG)
                    email.indexOf("@") == 0 || email.lastIndexOf("@") == email.length - 1 -> Result.Error(
                        EmailFormatError.AT_START_OR_END
                    )

                    email.indexOf("..") != -1 -> Result.Error(EmailFormatError.CONSECUTIVE_PERIODS)
                    email.contains(" ") -> Result.Error(EmailFormatError.CONTAINS_SPACES)
                    email.count { it == '@' } > 1 -> Result.Error(EmailFormatError.MULTIPLE_AT_SYMBOLS)
                    email.split("@")[0].length > 64 -> Result.Error(EmailFormatError.LOCAL_PART_TOO_LONG)
                    email.split("@")[1].length > 253 -> Result.Error(EmailFormatError.DOMAIN_PART_TOO_LONG)
                    else -> Result.Error(EmailFormatError.INVALID_FORMAT)
                }
            }
        }

        private fun emailContainsIllegalCharacters(email: String): Boolean {
            val illegalChars = listOf(
                '\'', '\"', '`', '!', '#', '$', '%', '^', '&', '*', '(', ')', '=', '+',
                '[', ']', '{', '}', '\\', '|', ';', ':', ',', '<', '>', '?', '/'
            )
            return email.any { illegalChars.contains(it) }
        }
    }
}