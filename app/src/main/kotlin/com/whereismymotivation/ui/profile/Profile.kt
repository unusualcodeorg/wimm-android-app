package com.whereismymotivation.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereismymotivation.data.model.User
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.preview.UserParameterProvider
import com.whereismymotivation.ui.journal.Journals
import com.whereismymotivation.ui.journal.JournalsViewModel
import com.whereismymotivation.ui.moods.Moods
import com.whereismymotivation.ui.moods.MoodsViewModel
import com.whereismymotivation.ui.theme.AppTheme
import com.whereismymotivation.utils.common.PermissionUtils

@Composable
fun Profile(
    modifier: Modifier,
    profileViewModel: ProfileViewModel,
    moodViewModel: MoodsViewModel,
    journalsViewModel: JournalsViewModel
) {
    val user = profileViewModel.user.collectAsStateWithLifecycle().value
    val selectedTab = profileViewModel.selectedTab.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Header(
                logout = { profileViewModel.logout() },
                user = user
            )
        }
    ) { innerPadding ->
        Tabs(
            modifier = Modifier.padding(innerPadding),
            moodsViewModel = moodViewModel,
            journalsViewModel = journalsViewModel,
            selectedTab = selectedTab,
            selectTab = { profileViewModel.selectTab(it) }
        )
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
            .background(MaterialTheme.colorScheme.background)
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
            DropDownMenu(logout = logout)
        }
    }
}

@Composable
private fun DropDownMenu(logout: () -> Unit) {
    var menuExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
    ) {
        IconButton(onClick = { menuExpanded = true }) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null
            )
        }
        DropdownMenu(
            modifier = Modifier,
            expanded = menuExpanded,
            offset = DpOffset(0.dp, 0.dp),
            onDismissRequest = { menuExpanded = false },
        ) {
            val context = LocalContext.current
            DropdownMenuItem(
                onClick = {
                    menuExpanded = false
                    logout()
                },
                text = {
                    Text(
                        text = "Logout",
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            )
            if (PermissionUtils.needNotificationPermission())
                DropdownMenuItem(
                    onClick = {
                        menuExpanded = false
                        PermissionUtils.launchNotificationSettings(context)
                    },
                    text = {
                        Text(
                            text = "Notification",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )
            if (PermissionUtils.needAlarmPermission())
                DropdownMenuItem(
                    onClick = {
                        menuExpanded = false
                        PermissionUtils.launchAlarmSettings(context)
                    },
                    text = {
                        Text(
                            text = "Alarm",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                )
        }
    }
}

@Composable
private fun Tabs(
    modifier: Modifier = Modifier,
    moodsViewModel: MoodsViewModel,
    journalsViewModel: JournalsViewModel,
    selectedTab: ProfileTab = ProfileTab.MOOD,
    selectTab: (ProfileTab) -> Unit
) {

    Column(modifier = modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = selectedTab.index) {
            ProfileTab.entries.map {
                Tab(
                    text = { Text(it.title) },
                    selected = it == selectedTab,
                    onClick = { selectTab(it) }
                )
            }
        }
        when (selectedTab) {
            ProfileTab.MOOD -> Moods(viewModel = moodsViewModel)
            ProfileTab.JOURNAL -> Journals(viewModel = journalsViewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DropDownMenuPreview() {
    AppTheme {
        DropDownMenu(logout = {})
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