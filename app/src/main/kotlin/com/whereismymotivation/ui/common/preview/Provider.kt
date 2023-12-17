package com.whereismymotivation.ui.common.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.model.User

class MentorPreviewParameterProvider : PreviewParameterProvider<Mentor> {
    override val values = sequenceOf(
        Mentor(
            id = "abc",
            name = "Computer Guy",
            thumbnail = "https://upload.wikimedia.org/wikipedia/en/c/cb/Osho.jpg",
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            coverImgUrl = "https://cdn.pixabay.com/photo/2017/09/12/11/56/universe-2742113_1280.jpg",
            occupation = "Computer Engineer",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Eget lorem dolor sed viverra ipsum nunc aliquet bibendum. Et netus et malesuada fames ac turpis egestas maecenas pharetra. Facilisis gravida neque convallis a cras semper. \n\n Maecenas ultricies mi eget mauris pharetra et ultrices neque. Et odio pellentesque diam volutpat commodo sed. Et ultrices neque ornare aenean euismod. Ac feugiat sed lectus vestibulum mattis ullamcorper velit. Ut tristique et egestas quis ipsum \n\n suspendisse ultrices gravida. Vel orci porta non pulvinar neque. Laoreet sit amet cursus sit amet dictum. Nibh tellus molestie nunc non blandit massa enim nec dui.",
            subscribed = false,
        )
    )
}

class ContentPreviewParameterProvider : PreviewParameterProvider<Content> {
    override val values = sequenceOf(
        Content(
            id = "5d3abb01d7e737505f6283a8",
            title = "What Booth Said After He Killed Lincoln",
            subtitle = "Assasination",
            thumbnail = "https://i3.ytimg.com/vi/hVLM0BSqx5o/hqdefault.jpg",
            extra = "https://www.youtube.com/watch?v=9TCMHVmNc5w",
            creator = User(
                id = "6569996fb0013db6a35f2f84",
                name = "Janishar Ali",
                email = null,
                profilePicUrl = "https://avatars.githubusercontent.com/u/11065002?v=4",
            ),
            views = 54,
            category = Content.Category.YOUTUBE,
            liked = false
        )
    )
}