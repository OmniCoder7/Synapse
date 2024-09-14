package com.proton.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    toRegister: () -> Unit,
    toForgotPassword: () -> Unit,
    toHome: (Long) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val viewModel = koinViewModel<LoginViewModel>()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    var isPasswordVisible by remember { mutableStateOf(false) }
    val loginError by viewModel.loginError.collectAsStateWithLifecycle()
    Box(
        modifier = modifier
            .clickable(interactionSource = null, indication = null) { focusManager.clearFocus() },
        contentAlignment = Alignment.Center
    ) {
        Image(imageVector = ImageVector.vectorResource(R.drawable.login), contentDescription = null)
        Text(
            text = stringResource(R.string.feature_login_login_title),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.login_title)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TextField(
                value = email,
                onValueChange = { viewModel.setEmail(it) },
                label = { Text(stringResource(R.string.feature_login_enter_your_email)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            TextField(
                value = password,
                onValueChange = { viewModel.setPassword(it) },
                label = { Text(stringResource(R.string.feature_login_enter_your_password)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )
            Row(
                modifier = Modifier.align(Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = isPasswordVisible,
                    onCheckedChange = { isPasswordVisible = !isPasswordVisible })
                Text(text = stringResource(R.string.feature_login_show_password))
            }
            Text(
                stringResource(R.string.feature_login_forgot_password),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { toForgotPassword.invoke() }
            )

            TextButton(
                onClick = { viewModel.login(onSuccess = toHome) },
                colors = ButtonDefaults.textButtonColors(containerColor = colorResource(R.color.loginButton))
            ) {
                Text(
                    stringResource(R.string.feature_login_login),
                    color = colorResource(R.color.loginButtonText),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(R.dimen.login_button_horizontal_padding)
                    ),
                    fontWeight = FontWeight.W400
                )
            }

            Text(text = stringResource(R.string.feature_login_create_account),
                modifier = Modifier
                    .align(Alignment.Start)
                    .clickable { toRegister.invoke() })

            loginError?.let {
                Text(text = it.asString(), color = Color.Red)
            }
        }
    }
}