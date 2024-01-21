package com.whereismymotivation.ui.common.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whereismymotivation.data.local.db.entity.Journal
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.model.Role
import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.data.model.UniversalSearchResult
import com.whereismymotivation.data.model.User
import com.whereismymotivation.utils.common.CalendarUtils

class MentorPreviewParameterProvider : PreviewParameterProvider<Mentor> {
    override val values = sequenceOf(
        Mentor(
            id = "xyz",
            name = "Computer Guy",
            thumbnail = "https://upload.wikimedia.org/wikipedia/en/c/cb/Osho.jpg",
            occupation = "Computer Engineer",
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Eget lorem dolor sed viverra ipsum nunc aliquet bibendum. Et netus et malesuada fames ac turpis egestas maecenas pharetra. Facilisis gravida neque convallis a cras semper. \n\n Maecenas ultricies mi eget mauris pharetra et ultrices neque. Et odio pellentesque diam volutpat commodo sed. Et ultrices neque ornare aenean euismod. Ac feugiat sed lectus vestibulum mattis ullamcorper velit. Ut tristique et egestas quis ipsum \n\n suspendisse ultrices gravida. Vel orci porta non pulvinar neque. Laoreet sit amet cursus sit amet dictum. Nibh tellus molestie nunc non blandit massa enim nec dui.",
            coverImgUrl = "https://cdn.pixabay.com/photo/2017/09/12/11/56/universe-2742113_1280.jpg",
            subscribed = false
        )
    )
}

class TopicPreviewParameterProvider : PreviewParameterProvider<Topic> {
    override val values = sequenceOf(
        Topic(
            id = "abc",
            name = "Art",
            thumbnail = "https://upload.wikimedia.org/wikipedia/en/c/cb/Osho.jpg",
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            coverImgUrl = "https://cdn.pixabay.com/photo/2017/09/12/11/56/universe-2742113_1280.jpg",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Eget lorem dolor sed viverra ipsum nunc aliquet bibendum. Et netus et malesuada fames ac turpis egestas maecenas pharetra. Facilisis gravida neque convallis a cras semper. \n\n Maecenas ultricies mi eget mauris pharetra et ultrices neque. Et odio pellentesque diam volutpat commodo sed. Et ultrices neque ornare aenean euismod. Ac feugiat sed lectus vestibulum mattis ullamcorper velit. Ut tristique et egestas quis ipsum \n\n suspendisse ultrices gravida. Vel orci porta non pulvinar neque. Laoreet sit amet cursus sit amet dictum. Nibh tellus molestie nunc non blandit massa enim nec dui.",
            subscribed = false
        )
    )
}

class ContentPreviewParameterProvider : PreviewParameterProvider<Content> {
    override val values = sequenceOf(
        Content(
            id = "5d3abb01d7e737505f6283a8",
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt",
            subtitle = "Lorem",
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
            liked = false,
            likes = 12456356,
            shares = 974524
        )
    )
}

class SearchPreviewParameterProvider : PreviewParameterProvider<UniversalSearchResult> {
    override val values = sequenceOf(
        UniversalSearchResult(
            id = "5d3abb01d7e737505f6283a8",
            title = "What Booth Said After He Killed Lincoln",
            category = Content.Category.YOUTUBE,
            thumbnail = "https://i3.ytimg.com/vi/hVLM0BSqx5o/hqdefault.jpg",
            extra = "https://www.youtube.com/watch?v=9TCMHVmNc5w",
        )
    )
}

class TopicMentorPreviewParameterProvider : PreviewParameterProvider<Pair<Topic, Mentor>> {
    override val values: Sequence<Pair<Topic, Mentor>> = sequenceOf(
        Topic(
            id = "abc",
            name = "Art",
            thumbnail = "https://upload.wikimedia.org/wikipedia/en/c/cb/Osho.jpg",
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            coverImgUrl = "https://cdn.pixabay.com/photo/2017/09/12/11/56/universe-2742113_1280.jpg",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Eget lorem dolor sed viverra ipsum nunc aliquet bibendum. Et netus et malesuada fames ac turpis egestas maecenas pharetra. Facilisis gravida neque convallis a cras semper. \n\n Maecenas ultricies mi eget mauris pharetra et ultrices neque. Et odio pellentesque diam volutpat commodo sed. Et ultrices neque ornare aenean euismod. Ac feugiat sed lectus vestibulum mattis ullamcorper velit. Ut tristique et egestas quis ipsum \n\n suspendisse ultrices gravida. Vel orci porta non pulvinar neque. Laoreet sit amet cursus sit amet dictum. Nibh tellus molestie nunc non blandit massa enim nec dui.",
            subscribed = false
        ) to Mentor(
            id = "xyz",
            name = "Computer Guy",
            thumbnail = "https://upload.wikimedia.org/wikipedia/en/c/cb/Osho.jpg",
            occupation = "Computer Engineer",
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Eget lorem dolor sed viverra ipsum nunc aliquet bibendum. Et netus et malesuada fames ac turpis egestas maecenas pharetra. Facilisis gravida neque convallis a cras semper. \n\n Maecenas ultricies mi eget mauris pharetra et ultrices neque. Et odio pellentesque diam volutpat commodo sed. Et ultrices neque ornare aenean euismod. Ac feugiat sed lectus vestibulum mattis ullamcorper velit. Ut tristique et egestas quis ipsum \n\n suspendisse ultrices gravida. Vel orci porta non pulvinar neque. Laoreet sit amet cursus sit amet dictum. Nibh tellus molestie nunc non blandit massa enim nec dui.",
            coverImgUrl = "https://cdn.pixabay.com/photo/2017/09/12/11/56/universe-2742113_1280.jpg",
            subscribed = false
        )
    )
}

class UserParameterProvider : PreviewParameterProvider<User> {
    override val values = sequenceOf(
        User(
            id = "5d3abb01d7e737505f6283a8",
            name = "Janishar Ali",
            email = "hello@janisharali.com",
            profilePicUrl = "https://avatars.githubusercontent.com/u/11065002?v=4",
            roles = emptyList(),
        )
    )
}

class JournalParameterProvider : PreviewParameterProvider<Journal> {
    override val values = sequenceOf(
        Journal(
            id = 0,
            _id = "abc",
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Eget lorem dolor sed viverra ipsum nunc aliquet bibendum. Et netus et malesuada fames ac turpis egestas maecenas pharetra. Facilisis gravida neque convallis a cras semper. \n\n Maecenas ultricies mi eget mauris pharetra et ultrices neque. Et odio pellentesque diam volutpat",
            createdBy = "Janishar Ali",
            createdAt = CalendarUtils.now(),
            updatedAt = CalendarUtils.now(),
            synced = false
        )
    )
}

class ContentUserPreviewParameterProvider : PreviewParameterProvider<Pair<Content, User>> {
    override val values: Sequence<Pair<Content, User>> = sequenceOf(
        Content(
            id = "5d3abb01d7e737505f6283a8",
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt",
            subtitle = "Lorem",
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
            liked = false,
            likes = 12456356,
            shares = 974524
        ) to User(
            id = "5d3abb01d7e737505f6283a8",
            name = "Janishar Ali",
            email = "hello@janisharali.com",
            profilePicUrl = "https://avatars.githubusercontent.com/u/11065002?v=4",
            roles = listOf(
                Role("1", Role.RoleCode.VIEWER),
                Role("1", Role.RoleCode.ADMIN),
                Role("1", Role.RoleCode.MANAGER),
            ),
        )
    )
}