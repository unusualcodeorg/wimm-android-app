package com.whereismymotivation.ui.mentors

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.image.OutlinedAvatar
import com.whereismymotivation.ui.home.HomeAppBar
import com.whereismymotivation.ui.theme.AppTheme
import java.util.Locale

@Composable
fun Mentors(
    modifier: Modifier = Modifier,
    mentorViewModel: MentorViewModel,
) {
    val mentors = mentorViewModel.mentors.collectAsState().value
    MentorsView(
        modifier = modifier,
        mentors = mentors,
        selectMentor = { mentorViewModel.selectMentor(it) }
    )
}

@Composable
fun MentorsView(
    mentors: List<Mentor>,
    modifier: Modifier = Modifier,
    selectMentor: (Mentor) -> Unit,
) {
    Column(modifier = modifier) {
        HomeAppBar(title = stringResource(R.string.my_mentors))
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 2.dp,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            content = {
                items(mentors) { mentor ->
                    MentorView(mentor, selectMentor)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
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
        color = MaterialTheme.colorScheme.surface
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
                    .size(38.dp)
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

@Preview(name = "MentorInfoPreview: Light")
@Composable
private fun MentorPreview() {
    AppTheme {
        MentorView(
            modifier = Modifier,
            mentor = Mentor(
                id = "abc",
                name = "Mr. ABC XYZ",
                thumbnail = "https://upload.wikimedia.org/wikipedia/en/c/cb/Osho.jpg",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                title = "Some introduction about the personalities",
                coverImgUrl = "https://cdn.pixabay.com/photo/2017/09/12/11/56/universe-2742113_1280.jpg",
                occupation = "Spirituality Guru",
                subscribed = false
            ),
            selectMentor = {}
        )
    }
}

@Preview(name = "MentorsPreview: Light")
@Composable
private fun MentorsPreview() {
    val mentor1 = Mentor(
        id = "abc",
        name = "Computer Guy",
        thumbnail = "https://upload.wikimedia.org/wikipedia/en/c/cb/Osho.jpg",
        title = "He is a great scientist",
        coverImgUrl = "https://cdn.pixabay.com/photo/2017/09/12/11/56/universe-2742113_1280.jpg",
        occupation = "Computer Engineer",
        description = "",
        subscribed = false,
    )
    val mentor2 = Mentor(
        id = "abc2",
        name = "Mr. ABC XYZ",
        thumbnail = "https://upload.wikimedia.org/wikipedia/en/c/cb/Osho.jpg",
        title = "Some introduction about the personalities",
        coverImgUrl = "https://cdn.pixabay.com/photo/2017/09/12/11/56/universe-2742113_1280.jpg",
        occupation = "Spirituality Guru",
        description = "",
        subscribed = false
    )
    AppTheme {
        MentorsView(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            selectMentor = {},
            mentors = listOf(
                mentor1, mentor2, mentor2, mentor1, mentor2
            )
        )
    }
}