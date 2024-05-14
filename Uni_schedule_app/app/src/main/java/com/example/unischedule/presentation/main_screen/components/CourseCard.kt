package com.example.unischedule.presentation.main_screen.components

import android.graphics.Color.rgb
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unischedule.common.darkerColor
import com.example.unischedule.domain.model.Course


@Composable
fun CourseCard(
    modifier: Modifier = Modifier,
    course: Course,
) {
    val darkerColor = course.color.darkerColor()
    val openInfoDialog = remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { openInfoDialog.value = true }
            .border(2.dp,
                darkerColor,
                RoundedCornerShape(12.dp)
            )
            .background(course.color),
        contentAlignment = Alignment.Center
    ) {
        if(course.type.isNotEmpty()){
            Box(modifier = Modifier
                .clip(RoundedCornerShape(0.dp, 12.dp, 0.dp, 12.dp))
                .background(darkerColor)
                .align(Alignment.TopEnd)
                .padding(8.dp)
            ){
                Text(
                    text = course.type
                )
            }
        }
        if(openInfoDialog.value){
            InfoDialogWithCourseDetails(
                course = course,
                onDismissRequest = {openInfoDialog.value = false}
            )
        }
        Text(
            modifier = Modifier.padding(12.dp),
            text = course.name,
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}
@Preview
@Composable
private fun CourseCardNormalSizePreview() {
        CourseCard(course =
        Course(
            name = "Podstawy techniki cyfrowej",
            startTime = 495,
            endTime = 585,
            color = Color(
                rgb(
                    202,
                    255,
                    14
                )
            ),
            type = "wy"),
            modifier = Modifier.height(150.dp)
        )
}

@Preview
@Composable
private fun CourseCardBigSizePreview() {
    CourseCard(course =
    Course("Podstawy techniki cyfrowej", 495, 585, color = Color(
        rgb(
            202,
            255,
            14
        )
    )),
        modifier = Modifier.height(225.dp)
    )
}
@Preview
@Composable
private fun CourseCardLongNamePreview() {
    CourseCard(course =
    Course("Podstawy techniki cyfrowejdsaaaaaaaaaaaaadsadasdsadsadasdasdsaaaaaaaaaaaaaaaadasaaaadsdsa", 495, 585, color = Color(
        rgb(
            202,
            255,
            14
        )
    )),
        modifier = Modifier.height(150.dp)
    )
}