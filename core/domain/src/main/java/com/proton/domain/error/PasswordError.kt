package com.proton.domain.error

enum class PasswordError: Error {
    BLANK,
    TOO_SHORT,
    NO_LOWERCASE,
    NO_UPPERCASE,
    NO_DIGIT,
    NO_SPECIAL_CHAR,
    INVALID_FORMAT
}