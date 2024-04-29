package com.example.unischedule.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unischedule.domain.model.Course

@Composable
fun CourseCard(
    modifier: Modifier = Modifier,
    course: Course,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(course.color),
        contentAlignment = Alignment.Center
    ) {
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

//@Preview
//@Composable
//private fun CourseCardNormalSizePreview() {
//    CourseCard(course =
//        Course("Podstawy techniki cyfrowej, wy", 495, 585, color = Color(
//            android.graphics.Color.rgb(
//                202,
//                255,
//                14
//            )
//        )),
//        modifier = Modifier.height(150.dp)
//    )
//}
//
//@Preview
//@Composable
//private fun CourseCardBigSizePreview() {
//    CourseCard(course =
//    Course("Podstawy techniki cyfrowej, wy", 495, 585, color = Color(
//        android.graphics.Color.rgb(
//            202,
//            255,
//            14
//        )
//    )),
//        modifier = Modifier.height(225.dp)
//    )
//}
//@Preview
//@Composable
//private fun CourseCardLongNamePreview() {
//    CourseCard(course =
//    Course("Podstawy techniki cyfrowejdsaaaaaaaaaaaaadsadasdsadsadasdasdsaaaaaaaaaaaaaaaadasaaaadsdsa, wy", 495, 585, color = Color(
//        android.graphics.Color.rgb(
//            202,
//            255,
//            14
//        )
//    )),
//        modifier = Modifier.height(150.dp)
//    )
//}