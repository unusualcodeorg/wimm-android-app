package com.whereismymotivation.ui.moods

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.config.ChartDefaults
import com.whereismymotivation.R
import com.whereismymotivation.data.local.db.entity.Mood
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun Moods(modifier: Modifier = Modifier, viewModel: MoodsViewModel) {
    MoodsView(
        modifier = modifier,
        selectMood = { viewModel.selectMood(it) },
        chart = {
            HappinessLevel(moodGraph = viewModel.moodGraph)
        }
    )
}

@Composable
private fun MoodsView(
    modifier: Modifier,
    selectMood: (Mood.Code) -> Unit,
    chart: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.CalendarMonth,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Today, 19 January",
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.AccessTime,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Today, 19 January",
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        MoodSelection(selectMood = selectMood)
        chart()
    }

}

@Composable
private fun MoodSelection(selectMood: (Mood.Code) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(6.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.how_your_feeling),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                MoodType(code = Mood.Code.ANGRY, selectMood = selectMood)
                MoodType(code = Mood.Code.SAD, selectMood = selectMood)
                MoodType(code = Mood.Code.NORMAL, selectMood = selectMood)
                MoodType(code = Mood.Code.HAPPY, selectMood = selectMood)
                MoodType(code = Mood.Code.VERY_HAPPY, selectMood = selectMood)
            }
        }
    }
}

@Composable
private fun MoodType(code: Mood.Code, selectMood: (Mood.Code) -> Unit) {

    Column(
        modifier = Modifier
            .sizeIn(minWidth = 56.dp)
            .clickable { selectMood(code) }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                id =
                when (code) {
                    Mood.Code.ANGRY -> R.drawable.ic_angry
                    Mood.Code.SAD -> R.drawable.ic_sad
                    Mood.Code.NORMAL -> R.drawable.ic_normal
                    Mood.Code.HAPPY -> R.drawable.ic_happy
                    Mood.Code.VERY_HAPPY -> R.drawable.ic_very_happy
                }
            ),
            colorFilter = ColorFilter.tint(
                when (code) {
                    Mood.Code.ANGRY -> Color(0xffFF5722)
                    Mood.Code.SAD -> Color(0xff607D8B)
                    Mood.Code.NORMAL -> Color(0xff8D6E63)
                    Mood.Code.HAPPY -> Color(0xff6200EA)
                    Mood.Code.VERY_HAPPY -> Color(0xffD500F9)
                }
            ),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(
                id = when (code) {
                    Mood.Code.ANGRY -> R.string.angry
                    Mood.Code.SAD -> R.string.sad
                    Mood.Code.NORMAL -> R.string.normal
                    Mood.Code.HAPPY -> R.string.happy
                    Mood.Code.VERY_HAPPY -> R.string.delighted
                }
            ),
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun HappinessLevel(moodGraph: List<MoodGraphData>) {
    if (moodGraph.isEmpty()) return
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {

        Text(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.your_happiness_level),
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        BarChart(
            modifier = Modifier.fillMaxHeight(),
            dataCollection = ChartDataCollection(
                moodGraph.map {
                    BarData(
                        it.y,
                        it.x,
                        Color.Blue
                    )
                }
            ),
            axisConfig = ChartDefaults.axisConfigDefaults().copy(minLabelCount = 6)

        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MoodsPreview() {
    AppTheme {
        MoodsView(
            modifier = Modifier,
            selectMood = {},
            chart = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MoodSelectionPreview() {
    AppTheme {
        MoodSelection(selectMood = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun MoodTypePreview() {
    AppTheme {
        MoodType(Mood.Code.HAPPY, selectMood = {})
    }
}