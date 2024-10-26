package com.jetbrains.kmpapp.screens.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.TextFieldDecorationBox
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.screens.BackButton
import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.email
import kmp_app_template.composeapp.generated.resources.password
import kmp_app_template.composeapp.generated.resources.signUp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpScreen(
    navigateBack: () -> Unit,
    navigateUp: () -> Unit
) {
    val viewModel = koinViewModel<SignUpViewModel>()
    val infoMessage by viewModel.userInfoMessage.collectAsState()

    // A little lazy with the animated content.
    AnimatedContent(true) {
        SignUpGrid(
            infoMessage = infoMessage,
            onBackClick = navigateBack,
            onSignUpClick = { email, password ->
                CoroutineScope(Dispatchers.IO).launch {
                    if (viewModel.signUp(email, password)) {
                        CoroutineScope(Dispatchers.Main).launch {
                            navigateUp()
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SignUpGrid(
    infoMessage: String?,
    onBackClick: () -> Unit,
    onSignUpClick: (email: String, password: String) -> Unit,
    modifier: Modifier = Modifier,
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colors.background) {
                BackButton(onClick = onBackClick)
            }
        },
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars),
    ) { paddingValues ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            infoMessage?.let {
                Text(it)
            }
            BasicTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.LightGray),
                decorationBox = { innerTextField ->
                    TextFieldDecorationBox(
                        value = email,
                        innerTextField = innerTextField,
                        singleLine = true,
                        enabled = true,
                        placeholder = { Text(stringResource(Res.string.email)) },
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        colors = TextFieldDefaults.textFieldColors()
                    )
                }
            )
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.LightGray),
                decorationBox = { innerTextField ->
                    TextFieldDecorationBox(
                        value = password,
                        innerTextField = innerTextField,
                        singleLine = true,
                        enabled = true,
                        placeholder = { Text(stringResource(Res.string.password)) },
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        colors = TextFieldDefaults.textFieldColors()
                    )
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = {
                    onSignUpClick(email, password)
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            ) {
                Text(text = stringResource(Res.string.signUp))
            }
        }
    }
}
