package com.jetbrains.kmpapp.screens.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.data.MuseumObject
import com.jetbrains.kmpapp.screens.EmptyScreenContent
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.login
import kmp_app_template.composeapp.generated.resources.logout
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ListScreen(
    navigateToDetails: (objectId: Int) -> Unit,
    navigateToLogin: () -> Unit
) {
    val viewModel = koinViewModel<ListViewModel>()
    val objects by viewModel.objects.collectAsState()
    val showLoginButton by viewModel.showLoginButton.collectAsState()

    AnimatedContent(objects.isNotEmpty()) { objectsAvailable ->
        if (objectsAvailable) {
            ObjectGrid(
                objects = objects,
                onObjectClick = navigateToDetails,
                onLoginClick = navigateToLogin.takeIf { showLoginButton },
                onLogoutClick = {
                    viewModel.logout()
                }.takeUnless { showLoginButton }
            )
        } else {
            EmptyScreenContent(Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun ObjectGrid(
    objects: List<MuseumObject>,
    onObjectClick: (Int) -> Unit,
    onLoginClick: (() -> Unit)?,
    onLogoutClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colors.background) {
                onLoginClick?.let {
                    IconButton(onClick = it) {
                        Text(stringResource(Res.string.login))
                    }
                }
                onLogoutClick?.let {
                    IconButton(onClick = it) {
                        Text(stringResource(Res.string.logout))
                    }
                }
            }
        },
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(180.dp),
            // TODO simplify padding after https://issuetracker.google.com/issues/365052672 is fixed
            modifier = modifier
                .fillMaxSize()
                .padding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal).asPaddingValues()),
            contentPadding = WindowInsets.safeDrawing.only(WindowInsetsSides.Vertical)
                .asPaddingValues(),
            ) {
            items(objects, key = { it.objectID }) { obj ->
                ObjectFrame(
                    obj = obj,
                    onClick = { onObjectClick(obj.objectID) },
                    )
            }
        }
    }
}

@Composable
private fun ObjectFrame(
    obj: MuseumObject,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        KamelImage(
            resource = asyncPainterResource(data = obj.primaryImageSmall),
            contentDescription = obj.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.LightGray),
        )

        Spacer(Modifier.height(2.dp))

        Text(
            obj.title,
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
        Text(obj.artistDisplayName, style = MaterialTheme.typography.body2)
        Text(obj.objectDate, style = MaterialTheme.typography.caption)
    }
}
