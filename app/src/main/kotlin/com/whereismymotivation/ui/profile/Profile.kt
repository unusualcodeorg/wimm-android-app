package com.whereismymotivation.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereismymotivation.data.model.User
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.preview.UserParameterProvider
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun Profile(modifier: Modifier, viewModel: ProfileViewModel) {
    val user = viewModel.user.collectAsStateWithLifecycle().value
    ProfileView(
        modifier = modifier.fillMaxSize(),
        user = user,
        logout = { viewModel.logout() }
    )
}

@Composable
private fun ProfileView(
    user: User,
    modifier: Modifier,
    logout: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Header(logout = logout, user = user)
        }
    ) { innerPadding ->
        Tabs(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    user: User,
    logout: () -> Unit
) {
    Column(
        modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Row {
            Surface(
                color = Color.Transparent,
                shape = RoundedCornerShape(6.dp),
            ) {
                if (user.profilePicUrl != null)
                    NetworkImage(
                        url = user.profilePicUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .aspectRatio(1f)
                    )
                else
                    Icon(
                        imageVector = Icons.Filled.AccountBox,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .wrapContentSize()
                            .size(64.dp)
                    )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = user.name ?: "",
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = user.email ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { logout() }) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun Tabs(modifier: Modifier = Modifier) {
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Moods", "Journals")

    Column(modifier = modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> Column {}
            1 -> Column {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePreview(
    @PreviewParameter(UserParameterProvider::class, limit = 1) user: User
) {
    AppTheme {
        ProfileView(modifier = Modifier, user = user, logout = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun HeaderPreview(
    @PreviewParameter(UserParameterProvider::class, limit = 1) user: User
) {
    AppTheme {
        Header(modifier = Modifier, user = user, logout = {})
    }
}