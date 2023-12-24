package com.whereismymotivation.ui.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.PersonPinCircle
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Topic
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.preview.ContentPreviewParameterProvider
import com.whereismymotivation.ui.theme.AppTheme
import com.whereismymotivation.utils.common.Formatter

@Composable
fun FeedContentItem(
    modifier: Modifier = Modifier,
    content: Content,
    cardClick: (Content) -> Unit,
    profileClick: (Content) -> Unit,
    likeClick: (Content) -> Unit,
    shareClick: (Content) -> Unit,
    whatsAppClick: (Content) -> Unit,
    media: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = RectangleShape,
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (profilePic, name, contentType, subtitle, views, thumbnail, play, title, divider, likesAndShares) = createRefs()

            NetworkImage(url = content.creator.profilePicUrl ?: "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .size(30.dp)
                    .constrainAs(profilePic) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .clip(CircleShape)
                    .clickable { profileClick(content) })

            Text(text = content.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(profilePic.top, 16.dp)
                    start.linkTo(profilePic.end, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Text(text = content.creator.name ?: "",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(title.bottom, 8.dp)
                    start.linkTo(profilePic.end, 16.dp)
                    width = Dimension.fillToConstraints
                })

            Icon(
                contentDescription = null,
                tint = Color(0xFF0077cc),
                modifier = Modifier
                    .size(16.dp)
                    .constrainAs(contentType) {
                        top.linkTo(name.bottom, 5.dp)
                        start.linkTo(name.start)
                    },
                imageVector = when (content.category) {
                    Content.Category.AUDIO -> Icons.Outlined.Audiotrack
                    Content.Category.VIDEO -> Icons.Outlined.PlayCircle
                    Content.Category.IMAGE -> Icons.Outlined.Image
                    Content.Category.YOUTUBE -> Icons.Outlined.PlayCircle
                    Content.Category.FACEBOOK_VIDEO -> Icons.Outlined.PlayCircle
                    Content.Category.ARTICLE -> Icons.Outlined.Article
                    Content.Category.QUOTE -> Icons.Default.FormatQuote
                    Content.Category.MENTOR_INFO -> Icons.Outlined.PersonPinCircle
                    Content.Category.TOPIC_INFO -> Icons.Outlined.Topic
                }
            )

            Text(text = content.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF0077cc),
                modifier = Modifier.constrainAs(subtitle) {
                    top.linkTo(contentType.top)
                    bottom.linkTo(contentType.bottom)
                    start.linkTo(contentType.end, 5.dp)
                    width = Dimension.fillToConstraints
                })

            Text(
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.constrainAs(views) {
                    top.linkTo(subtitle.top)
                    bottom.linkTo(subtitle.bottom)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                },
                text = when (content.views) {
                    null -> ""
                    1L -> stringResource(R.string.view_count_1)
                    else -> stringResource(
                        R.string.views_count, Formatter.format(content.views ?: 0)
                    )
                }
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(thumbnail) {
                        top.linkTo(subtitle.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .clickable { cardClick(content) },
                content = media
            )

            when (content.category) {
                Content.Category.AUDIO -> {}
                Content.Category.IMAGE -> {}
                Content.Category.ARTICLE -> {}
                Content.Category.QUOTE -> {}
                Content.Category.MENTOR_INFO -> {}
                Content.Category.TOPIC_INFO -> {}
                Content.Category.FACEBOOK_VIDEO,
                Content.Category.YOUTUBE,
                Content.Category.VIDEO ->
                    Icon(imageVector = Icons.Outlined.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(45.dp)
                            .background(Color(0x88000000))
                            .padding(8.dp)
                            .constrainAs(play) {
                                bottom.linkTo(thumbnail.bottom, 8.dp)
                                end.linkTo(parent.end, 16.dp)
                            }
                            .clickable { cardClick(content) })
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(likesAndShares) {
                        top.linkTo(thumbnail.bottom)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    },
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LikeAndShareText(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp),
                    likes = content.likes ?: 0L,
                    shares = content.shares ?: 0L
                )

                IconButton(
                    onClick = { likeClick(content) }, modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (content.liked == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (content.liked == true) Color.Red else MaterialTheme.colorScheme.onSurface,
                    )

                }

                IconButton(
                    onClick = { shareClick(content) }, modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                IconButton(
                    onClick = { whatsAppClick(content) }, modifier = Modifier.size(24.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_whatsapp),
                        contentDescription = "whatsapp",
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
            Divider(thickness = 0.75.dp, modifier = Modifier
                .fillMaxWidth()
                .constrainAs(divider) {
                    top.linkTo(likesAndShares.bottom, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        }
    }
}

@Composable
private fun LikeAndShareText(modifier: Modifier, likes: Long, shares: Long) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.labelSmall,
        text = if (likes == 0L && shares == 1L) stringResource(R.string.share_count_1)
        else if (likes == 0L && shares > 1L) stringResource(
            R.string.shares_count,
            Formatter.format(shares)
        )
        else if (likes == 1L && shares == 0L) stringResource(R.string.like_count_1)
        else if (likes > 1L && shares == 0L) stringResource(
            R.string.likes_count,
            Formatter.format(likes)
        )
        else if (likes == 1L && shares == 1L) stringResource(R.string.like_and_share_count_1)
        else if (likes > 1L && shares == 1L) stringResource(
            R.string.likes_and_share_count, Formatter.format(likes)
        )
        else if (likes == 1L && shares > 1L) stringResource(
            R.string.like_and_shares_count, Formatter.format(shares)
        )
        else if (likes > 1L && shares > 1L) stringResource(
            R.string.likes_and_shares_count, Formatter.format(likes), Formatter.format(shares)
        )
        else ""
    )
}

@Preview(showBackground = true, name = "Dark")
@Composable
private fun FeedContentItemPreview(
    @PreviewParameter(ContentPreviewParameterProvider::class, limit = 1) content: Content
) {
    AppTheme(dark = true) {
        FeedContentItem(content = content,
            cardClick = {},
            profileClick = {},
            likeClick = {},
            shareClick = {},
            whatsAppClick = {}) {
            NetworkImage(
                url = content.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(220.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedQuoteItemPreview(
    @PreviewParameter(ContentPreviewParameterProvider::class, limit = 1) content: Content
) {
    AppTheme {
        FeedContentItem(content = content.copy(category = Content.Category.QUOTE),
            cardClick = {},
            profileClick = {},
            likeClick = {},
            shareClick = {},
            whatsAppClick = {}) {
            FeedQuote(
                saying = "Don't let what you cannot do interfere with what you can do.",
                author = "Anonymous"
            )
        }
    }
}

