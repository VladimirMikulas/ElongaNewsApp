package com.vlamik.news.features.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vlamik.news.R
import com.vlamik.news.component.Toast
import com.vlamik.news.features.login.LoginViewModel.LoginUiState.IncorrectPassword
import com.vlamik.news.features.login.LoginViewModel.LoginUiState.InitialState
import com.vlamik.news.features.login.LoginViewModel.LoginUiState.LoginSuccess
import com.vlamik.news.theme.TemplateTheme
import com.vlamik.news.utils.preview.DeviceFormatPreview
import com.vlamik.news.utils.preview.FontScalePreview
import com.vlamik.news.utils.preview.ThemeModePreview


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    loginSuccess: (Boolean) -> Unit
) {
    val loginState by loginViewModel.state.collectAsState()

    when (val state = loginState) {
        is InitialState, IncorrectPassword -> {
            LoginUi(
                state = state,
                onLoginSuccess = loginSuccess,
                onLoginClicked = loginViewModel::login
            )
        }

        LoginSuccess -> loginSuccess(true)
    }
}

@Composable
private fun LoginUi(
    state: LoginViewModel.LoginUiState,
    onLoginSuccess: (Boolean) -> Unit,
    onLoginClicked: (String, String) -> Unit
) =
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(40.dp)
    ) {
        if (state == IncorrectPassword) {
            Toast(R.string.incorrect_password)
        }

        val email = remember {
            mutableStateOf("")
        }
        val password = remember {
            mutableStateOf("")
        }

        Text(
            text = stringResource(id = R.string.login_title), fontSize = 25.sp, color = Color.Blue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 50.dp, 0.dp, 0.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = email.value, onValueChange = {
                email.value = it
            },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = stringResource(id = R.string.email))
            },
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
        )

        OutlinedTextField(
            value = password.value, onValueChange = {
                password.value = it
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Info,
                    contentDescription = stringResource(id = R.string.password)
                )
            },
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp, 0.dp, 0.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        FilledTonalButton(
            onClick = { onLoginClicked(email.value, password.value) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 25.dp, 0.dp, 0.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login_button),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }

        TextButton(
            onClick = { onLoginSuccess(false) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 25.dp, 0.dp, 0.dp)
        ) {
            Text(
                text = stringResource(id = R.string.skip_button),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }

    }

@ThemeModePreview
@FontScalePreview
@DeviceFormatPreview
@Composable
private fun LoginPreview() {
    TemplateTheme {
        LoginUi(
            state = InitialState,
            onLoginClicked = { _, _ -> },
            onLoginSuccess = {}
        )
    }
}

