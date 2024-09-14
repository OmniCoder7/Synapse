package com.proton.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.proton.domain.error.NetworkError
import com.proton.domain.models.User
import com.proton.domain.service.AuthService
import com.proton.domain.useCase.RegisterUseCase
import com.proton.domain.util.Result
import com.proton.register.composable.Email
import com.proton.register.composable.NoTintIcon
import com.proton.register.composable.Password
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

@Composable
fun RegisterScreen(modifier: Modifier = Modifier, toLogin: () -> Unit, toHome: (Long) -> Unit) {
    val viewModel = koinViewModel<RegisterViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val emailState by viewModel.email.collectAsStateWithLifecycle()
    val passwordState by viewModel.password.collectAsStateWithLifecycle()
    val confirmPasswordState by viewModel.confirmPassword.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    var datePicker by remember { mutableStateOf(false) }
    val registerError by viewModel.registerError.collectAsStateWithLifecycle()

    Box(
        modifier = modifier.clickable(
            interactionSource = null, indication = null
        ) { focusManager.clearFocus() }, contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.register), contentDescription = null
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.register_title),
                modifier = Modifier
                    .padding(16.dp),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.register_title)
            )
            TextField(value = uiState.firstName,
                onValueChange = { viewModel.setFirstName(it) },
                label = { Text(stringResource(R.string.enter_first_name)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                ),
                leadingIcon = { NoTintIcon(id = R.drawable.first_name) },
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
            TextField(value = uiState.lastName,
                onValueChange = { viewModel.setLastName(it) },
                label = { Text(stringResource(R.string.enter_last_name)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                leadingIcon = { NoTintIcon(id = R.drawable.last_name) }

            )
            TextField(value = uiState.userName,
                onValueChange = { viewModel.setUsername(it) },
                label = { Text(stringResource(R.string.enter_username)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                ),
                leadingIcon = { NoTintIcon(id = R.drawable.username) },
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = uiState.gender == "Male", onClick = {
                        viewModel.onGenderChange("Male")
                    })
                    Text("Male")
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = uiState.gender != "Male", onClick = {
                        viewModel.onGenderChange("Female")
                    })
                    Text("Female")
                }
            }
            Text(text = uiState.dob.toString(),
                modifier = Modifier
                    .clickable { datePicker = true }
                    .align(Alignment.Start)
                    .border(0.3.dp, color = Color.Black)
                    .padding(horizontal = 4.dp))
            AnimatedVisibility(visible = datePicker) {
                DatePickerModal(onDateSelected = {
                    viewModel.setDob(it)
                }, onDismiss = { datePicker = false })

            }

            Email(emailState = emailState,
                imeAction = ImeAction.Next,
                onImeAction = { focusManager.moveFocus(FocusDirection.Down) })
            Password(label = stringResource(R.string.enter_password),
                passwordState = passwordState,
                imeAction = ImeAction.Next,
                onImeAction = { focusManager.moveFocus(FocusDirection.Down) })

            Password(label = stringResource(R.string.confirm_password),
                passwordState = confirmPasswordState,
                imeAction = ImeAction.Done,
                onImeAction = { focusManager.moveFocus(FocusDirection.Down) })
            Text(text = stringResource(R.string.already_have_an_account),
                modifier = Modifier
                    .clickable { toLogin.invoke() }
                    .align(Alignment.End))
            TextButton(
                onClick = { viewModel.register { toHome(it) } },
                colors = ButtonDefaults.textButtonColors(containerColor = colorResource(R.color.register_button))
            ) {
                Text(
                    text = stringResource(R.string.register),
                    color = colorResource(R.color.register_button_text),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(R.dimen.register_button_horizontal_padding)
                    ),
                    fontWeight = FontWeight.W400
                )
            }

            registerError?.let {
                Text(text = it.asString(), color = Color.Red)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = {
            onDateSelected(datePickerState.selectedDateMillis)
            onDismiss()
        }) {
            Text("OK")
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text("Cancel")
        }
    }) {
        DatePicker(state = datePickerState)
    }
}