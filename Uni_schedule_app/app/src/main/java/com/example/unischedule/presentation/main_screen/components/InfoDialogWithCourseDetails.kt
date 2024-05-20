package com.example.unischedule.presentation.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.unischedule.R
import com.example.unischedule.common.Constants
import com.example.unischedule.common.darkerColor
import com.example.unischedule.domain.model.Course
import com.example.unischedule.domain.model.formattedTime

@Composable
fun InfoDialogWithCourseDetails(
    onDismissRequest: () -> Unit,
    course: Course
) {
    val darkerColor = course.color.darkerColor()
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier) {
                    Icon(
                        modifier = Modifier.height(20.dp),
                        painter = painterResource(id = R.drawable.leader_icon),
                        contentDescription = "Leader",
                        tint = darkerColor
                    )
                    Text(
                        text = course.leader.ifEmpty { "No leader" },
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    Icon(
                        modifier = Modifier.height(20.dp)
                            .padding(end = 4.dp),
                        painter = painterResource(id = R.drawable.class_icon),
                        contentDescription = "Class",
                        tint = darkerColor
                    )
                    Text(
                        text = course.room.ifEmpty { "No room" },
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    Icon(
                        modifier = Modifier.height(20.dp)
                            .padding(end = 4.dp),
                        painter = painterResource(id = R.drawable.clock_icon),
                        contentDescription = "Clock",
                        tint = darkerColor
                    )
                    Text(
                        text = course.formattedTime().ifEmpty { "No time" },
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun InfoDialogPreview() {
    InfoDialogWithCourseDetails(
        onDismissRequest = {},
        course = Constants.DUMMY_LIST_OF_MONDAY[0]
            .copy(leader = "Sam Hammer", room = "PE290")
    )
}

@Preview
@Composable
private fun InfoDialogLongPreview() {
    InfoDialogWithCourseDetails(onDismissRequest = {},
        course = Constants.DUMMY_LIST_OF_MONDAY[0]
            .copy(
                leader = "dsjddddddddddddddsadsddddddddddddjjjjjjjjjjjjjjjjjjjjj",
                room = "fffffffdddddddsadsddddddddddddjjjjjjjjjdsddsdsa"
            )
    )
}

@Preview
@Composable
private fun InfoDialogNoInfoPreview() {
    InfoDialogWithCourseDetails(onDismissRequest = {},
        course = Constants.DUMMY_LIST_OF_MONDAY[0]
            .copy(leader = "", startTime = 0, endTime = 0)
    )
}
