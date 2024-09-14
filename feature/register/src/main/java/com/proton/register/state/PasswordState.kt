import com.proton.register.R
import com.proton.register.state.TextFieldState
import com.proton.domain.models.UIText

class PasswordState :
    TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError)

class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError(): UIText? {
        return if (showErrors()) {
            passwordConfirmationError()
        } else {
            null
        }
    }
}

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return com.proton.domain.util.PasswordValidator.isPasswordValid(password) is com.proton.domain.util.Result.Success && password == confirmedPassword
}

private fun isPasswordValid(password: String): Boolean {
    return when (com.proton.domain.util.PasswordValidator.isPasswordValid(password)) {
        is com.proton.domain.util.Result.Error -> false
        is com.proton.domain.util.Result.Success -> true
    }
}

private fun passwordValidationError(password: String): UIText {
    return when (val passwordValidity = com.proton.domain.util.PasswordValidator.isPasswordValid(password)) {
        is com.proton.domain.util.Result.Error -> when(passwordValidity.error) {
            com.proton.domain.error.PasswordError.BLANK -> UIText.StringResource(R.string.password_blank)
            com.proton.domain.error.PasswordError.TOO_SHORT -> UIText.StringResource(R.string.too_short)
            com.proton.domain.error.PasswordError.NO_LOWERCASE -> UIText.StringResource(R.string.no_lowercase)
            com.proton.domain.error.PasswordError.NO_UPPERCASE -> UIText.StringResource(R.string.no_uppercase)
            com.proton.domain.error.PasswordError.NO_DIGIT -> UIText.StringResource(R.string.no_digit)
            com.proton.domain.error.PasswordError.NO_SPECIAL_CHAR -> UIText.StringResource(R.string.no_special_char)
            com.proton.domain.error.PasswordError.INVALID_FORMAT -> UIText.StringResource(R.string.invalid_format)
        }
        is com.proton.domain.util.Result.Success -> UIText.DynamicString("No Error")
    }
}

private fun passwordConfirmationError(): UIText {
    return UIText.StringResource(R.string.password_mismatch)
}