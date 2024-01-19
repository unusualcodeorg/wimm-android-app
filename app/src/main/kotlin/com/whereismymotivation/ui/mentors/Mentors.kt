package com.whereismymotivation.ui.mentors

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.ui.common.appbar.LogoAppBar
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.image.OutlinedAvatar
import com.whereismymotivation.ui.common.preview.MentorPreviewParameterProvider
import com.whereismymotivation.ui.common.progress.LoadingPlaceholder
import com.whereismymotivation.ui.theme.AppTheme
import java.util.Locale

@Composable
fun Mentors(
    modifier: Modifier = Modifier,
    viewModel: MentorsViewModel,
) {
    val mentors = viewModel.mentors.collectAsStateWithLifecycle().value
    LoadingPlaceholder(loading = mentors.isEmpty())
    MentorsView(
        modifier = modifier.fillMaxSize(),
        mentors = mentors,
        selectMentor = { viewModel.selectMentor(it) },
        explore = { viewModel.explore() }
    )
}

@Composable
fun MentorsView(
    mentors: List<Mentor>,
    modifier: Modifier = Modifier,
    selectMentor: (Mentor) -> Unit,
    explore: () -> Unit
) {
    val state = rememberLazyStaggeredGridState()
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        LogoAppBar(
            title = stringResource(R.string.my_mentors),
            actions = {
                IconButton(onClick = explore) {
                    Icon(
                        tint = MaterialTheme.colorScheme.primary,
                        imageVector = Icons.Filled.GroupAdd,
                        contentDescription = null
                    )
                }
            })
        LazyVerticalStaggeredGrid(
            state = state,
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 2.dp,
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
        ) {
            items(mentors, key = { it.id }) { mentor ->
                MentorView(mentor, selectMentor)
            }
        }
    }
}

@Composable
private fun MentorView(
    mentor: Mentor,
    selectMentor: (Mentor) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.padding(4.dp),
        tonalElevation = 4.dp,
        shape = MaterialTheme.shapes.extraSmall
    ) {
        ConstraintLayout(
            modifier = Modifier
                .clickable(
                    onClick = { selectMentor(mentor) }
                )
                .semantics {
                    contentDescription = "featured"
                }
        ) {
            val (cover, avatar, occupation, name, steps, icon) = createRefs()
            NetworkImage(
                url = mentor.coverImgUrl,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(4f / 3f)
                    .constrainAs(cover) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    }
            )
            OutlinedAvatar(
                url = mentor.thumbnail,
                modifier = Modifier
                    .size(48.dp)
                    .constrainAs(avatar) {
                        centerHorizontallyTo(parent)
                        centerAround(cover.bottom)
                    }
            )
            Text(
                text = mentor.occupation.uppercase(Locale.getDefault()),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(16.dp)
                    .constrainAs(occupation) {
                        centerHorizontallyTo(parent)
                        top.linkTo(avatar.bottom)
                    }
            )
            Text(
                text = mentor.name,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(name) {
                        centerHorizontallyTo(parent)
                        top.linkTo(occupation.bottom)
                    }
            )
            Text(
                text = mentor.title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    )
                    .constrainAs(steps) {
                        centerHorizontallyTo(parent)
                        top.linkTo(name.bottom)
                    }
            )
        }
    }
}

@Preview(name = "MentorsPreview: Light", showBackground = true)
@Composable
private fun MentorsPreview(
    @PreviewParameter(MentorPreviewParameterProvider::class, limit = 1) mentor: Mentor
) {
    AppTheme {
        MentorsView(
            selectMentor = {},
            explore = {},
            mentors = listOf(
                mentor.copy(id = "1"),
                mentor.copy(id = "2"),
                mentor.copy(id = "3"),
                mentor.copy(id = "4"),
                mentor.copy(id = "5"),
            )
        )
    }
}

@Preview(name = "MentorInfoPreview: Light")
@Composable
private fun MentorPreview(
    @PreviewParameter(MentorPreviewParameterProvider::class, limit = 1) mentor: Mentor
) {
    AppTheme {
        MentorView(
            modifier = Modifier,
            mentor = mentor,
            selectMentor = {}
        )
    }
}