package com.proton.register.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.proton.register.R
import com.proton.register.state.TextFieldState

@Composable
fun Email(
    emailState: TextFieldState, imeAction: ImeAction = ImeAction.Next, onImeAction: () -> Unit,
) {
        TextField(
            value = emailState.text,
            onValueChange = {
                emailState.text = it
            },
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            modifier = Modifier.onFocusChanged { focusState ->
                emailState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    emailState.enableShowErrors()
                }
            },
            isError = emailState.showErrors(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction, keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(onDone = {
                onImeAction()
            }),
            singleLine = true,
            leadingIcon = {
                NoTintIcon(
                    id = R.drawable.email, contentDescription = stringResource(R.string.email)
                )
            },
            supportingText = {
                emailState.getError()?.let { error -> TextFieldError(textError = error.asString()) }
            },
            placeholder = { Text(text = stringResource(R.string.enter_email)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent
            )
        )
}

@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = textError,
        )
    }
}