package com.proton.register.state

import android.util.Patterns
import com.proton.domain.error.EmailFormatError
import com.proton.domain.models.UIText
import com.proton.domain.util.EmailValidator
import com.proton.register.R
import java.util.regex.Pattern
import com.proton.domain.util.Result

class EmailState(email: String? = null) :
    TextFieldState(validator = ::isEmailValid, errorFor = ::emailValidationError) {
    init {
        email?.let {
            text = it
        }
    }
}

private fun emailValidationError(email: String): UIText {
    return when(val emailValidator = EmailValidator.isEmailValid(email)) {
        is Result.Error -> when(emailValidator.error) {
            EmailFormatError.BLANK -> UIText.StringResource(R.string.email_blank)
            EmailFormatError.NO_AT_SYMBOL -> UIText.StringResource(R.string.no_at_symbol)
            EmailFormatError.TOO_LONG -> UIText.StringResource(R.string.email_too_long)
            EmailFormatError.AT_START_OR_END -> UIText.StringResource(R.string.at_start_or_end)
            EmailFormatError.CONSECUTIVE_PERIODS -> UIText.StringResource(R.string.consecutive_periods)
            EmailFormatError.CONTAINS_SPACES -> UIText.StringResource(R.string.contains_spaces)
            EmailFormatError.MULTIPLE_AT_SYMBOLS -> UIText.StringResource(R.string.multiple_at_symbols)
            EmailFormatError.LOCAL_PART_TOO_LONG -> UIText.StringResource(R.string.local_part_too_long)
            EmailFormatError.DOMAIN_PART_TOO_LONG -> UIText.StringResource(R.string.domain_part_too_long)
            EmailFormatError.ILLEGAL_CHARACTERS -> UIText.StringResource(R.string.illegal_characters)
            EmailFormatError.INVALID_FORMAT -> UIText.StringResource(R.string.invalid_format)
        }
        is Result.Success -> UIText.DynamicString("Valid Email")
    }
}

private fun isEmailValid(email: String): Boolean {
    return Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), email)
}
