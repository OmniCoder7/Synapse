package com.proton.domain.error

enum class EmailFormatError : Error {
    BLANK,
    NO_AT_SYMBOL,
    TOO_LONG,
    AT_START_OR_END,
    CONSECUTIVE_PERIODS,
    CONTAINS_SPACES,
    MULTIPLE_AT_SYMBOLS,
    LOCAL_PART_TOO_LONG,
    DOMAIN_PART_TOO_LONG,
    ILLEGAL_CHARACTERS,
    INVALID_FORMAT
}