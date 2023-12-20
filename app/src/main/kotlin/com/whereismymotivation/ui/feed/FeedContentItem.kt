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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
    onCardClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onLikeClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onWhatsAppClick: () -> Unit = {},
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = RectangleShape,
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (profilePic, name, contentType, subtitle, views, thumbnail, play, title, divider, likesAndShares) = createRefs()

            NetworkImage(
                url = content.creator.profilePicUrl ?: "",
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
                    .clickable { onProfileClick() }
            )

            Text(
                text = content.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(profilePic.top, 16.dp)
                        start.linkTo(profilePic.end, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    }

            )

            Text(
                text = content.creator.name ?: "",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                modifier = Modifier
                    .constrainAs(name) {
                        top.linkTo(title.bottom, 8.dp)
                        start.linkTo(profilePic.end, 16.dp)
                        width = Dimension.fillToConstraints
                    }
            )

            Icon(
                imageVector = Icons.Outlined.PlayCircle,
                contentDescription = null,
                tint = Color(0xFF0077cc),
                modifier = Modifier
                    .size(16.dp)
                    .constrainAs(contentType) {
                        top.linkTo(name.bottom, 5.dp)
                        start.linkTo(name.start)
                    }
            )

            Text(
                text = content.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF0077cc),
                modifier = Modifier
                    .constrainAs(subtitle) {
                        top.linkTo(contentType.top)
                        bottom.linkTo(contentType.bottom)
                        start.linkTo(contentType.end, 5.dp)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = content.views.toString(),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .constrainAs(views) {
                        top.linkTo(subtitle.top)
                        bottom.linkTo(subtitle.bottom)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    }
            )

            NetworkImage(
                url = content.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .constrainAs(thumbnail) {
                        top.linkTo(subtitle.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .clickable { onCardClick() }
            )

            Icon(
                imageVector = Icons.Outlined.PlayArrow,
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
                    .clickable { onCardClick() }
            )

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
                    onClick = { onLikeClick() },
                    modifier = Modifier
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.Black
                    )

                }

                IconButton(
                    onClick = { onShareClick() },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }

                IconButton(
                    onClick = { onWhatsAppClick() },
                    modifier = Modifier.size(24.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_whatsapp),
                        contentDescription = "whatsapp",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
            Divider(
                thickness = 0.75.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(divider) {
                        top.linkTo(likesAndShares.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}

@Composable
fun LikeAndShareText(modifier: Modifier, likes: Long, shares: Long) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.labelSmall,
        text = if (likes == 0L && shares == 1L)
            stringResource(R.string.share_count_1)
        else if (likes == 0L && shares > 1L)
            stringResource(R.string.shares_count, Formatter.format(shares))
        else if (likes == 1L && shares == 0L)
            stringResource(R.string.like_count_1)
        else if (likes > 1L && shares == 0L)
            stringResource(R.string.likes_count, Formatter.format(likes))
        else if (likes == 1L && shares == 1L)
            stringResource(R.string.like_and_share_count_1)
        else if (likes > 1L && shares == 1L)
            stringResource(
                R.string.likes_and_share_count, Formatter.format(likes)
            )
        else if (likes == 1L && shares > 1L)
            stringResource(
                R.string.like_and_shares_count, Formatter.format(shares)
            )
        else if (likes > 1L && shares > 1L)
            stringResource(
                R.string.likes_and_shares_count,
                Formatter.format(likes),
                Formatter.format(shares)
            )
        else ""
    )
}

@Preview
@Composable
fun FeedContentItemPreview(
    @PreviewParameter(ContentPreviewParameterProvider::class, limit = 1) content: Content
) {
    AppTheme {
        FeedContentItem(content = content)
    }
}

