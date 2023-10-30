package com.example.udemy_paulo_trivia_app.component

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.udemy_paulo_trivia_app.screen.QuestionViewModel
import com.example.udemy_paulo_trivia_app.util.AppColors

@Composable
fun QuestionComposable(questionViewModel: QuestionViewModel) {

    val question = questionViewModel.data.value.data?.toMutableList()

    if (questionViewModel.data.value.isLoading == true) {
        CircularProgressIndicator()
        Log.d(TAG, "Question: loading")
    } else {
        Log.d(TAG, "Question: loading stopped")
    }


}

@Preview
@Composable
fun QuestionDisplay() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        color = AppColors.mDarkPurple
    ) {

        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            QuestionTracker()
        }

    }
}

@Preview
@Composable
fun QuestionTracker(counter: Int = 10, maxQuestion: Int = 100) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = ParagraphStyle(textIndent = TextIndent.None)
            ) {
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                ) {
                    append("Question $counter/")
                    withStyle(
                        style = SpanStyle(
                            color = AppColors.mLightGray,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp
                        )
                    ) {
                        append("$maxQuestion")
                    }

                }
            }
        },
        modifier = Modifier
            .padding(20.dp)
    ) //Text


}