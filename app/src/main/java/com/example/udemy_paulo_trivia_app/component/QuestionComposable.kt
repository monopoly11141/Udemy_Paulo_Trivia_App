package com.example.udemy_paulo_trivia_app.component

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.udemy_paulo_trivia_app.model.QuestionItem
import com.example.udemy_paulo_trivia_app.screen.QuestionViewModel
import com.example.udemy_paulo_trivia_app.util.AppColors

@Composable
fun QuestionComposable(questionViewModel: QuestionViewModel) {

    val questionList = questionViewModel.data.value.data?.toMutableList()
    val questionIndex = remember { mutableStateOf(0) }

    if (questionViewModel.data.value.isLoading == true) {
        CircularProgressIndicator()
        Log.d(TAG, "Question: loading")
    } else {
        val question = try {
            questionList?.get(questionIndex.value)
        } catch (e: Exception) {
            null
        }

        if (questionList != null) {
            QuestionDisplay(
                questionItem = question!!,
                questionIndex = questionIndex,
                questionViewModel = questionViewModel
            ) {
                questionIndex.value = questionIndex.value + 1
            }
        }
    }


}


@Composable
fun QuestionDisplay(
    questionItem: QuestionItem,
    questionIndex: MutableState<Int>,
    questionViewModel: QuestionViewModel,
    onNextClicked: (Int) -> Unit = {}
) {
    val choiceStateValueList = remember(questionItem) { questionItem.choices.toMutableList() }
    var answerStateValue by remember(questionItem) { mutableStateOf<Int?>(null) }
    var isCorrectAnswerStateValue by remember(questionItem) { mutableStateOf<Boolean?>(null) }

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    val updateAnswer: (Int) -> Unit = remember(questionItem) {
        {
            answerStateValue = it
            isCorrectAnswerStateValue = (choiceStateValueList[it] == questionItem.answer)
        }
    }



    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = AppColors.mDarkPurple
    ) {

        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            if (questionIndex.value >= 3) {
                ShowProgress(score = questionIndex.value)
            }

            QuestionTracker(
                counter = questionIndex.value,
                maxQuestion = questionViewModel.getTotalQuestionCount()
            )

            DrawDottedLine(pathEffect)

            Column(

            ) {
                Text(
                    text = questionItem.question,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(Alignment.Start)
                        .fillMaxHeight(0.3f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 24.sp,
                    color = AppColors.mOffWhite
                )

                choiceStateValueList.forEachIndexed { index, answerText ->
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .border(
                                width = 4.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(AppColors.mOffDarkPurple, AppColors.mOffDarkPurple)
                                ),
                                shape = RoundedCornerShape(15.dp)

                            )
                            .clip(
                                RoundedCornerShape(
                                    topStartPercent = 50,
                                    topEndPercent = 50,
                                    bottomEndPercent = 50,
                                    bottomStartPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (answerStateValue == index),
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = Modifier
                                .padding(
                                    start = 16.dp
                                ),
                            colors = RadioButtonDefaults.colors(
                                selectedColor =
                                if (isCorrectAnswerStateValue == true && index == answerStateValue) {
                                    Color.Green.copy(alpha = 0.2f)
                                } else {
                                    Color.Red.copy(alpha = 0.2f)
                                }
                            )
                        )

                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    color =
                                    if (isCorrectAnswerStateValue == true && index == answerStateValue) {
                                        Color.Green
                                    } else if (isCorrectAnswerStateValue == false && index == answerStateValue) {
                                        Color.Red
                                    } else {
                                        AppColors.mOffWhite
                                    },
                                    fontSize = 16.sp

                                )
                            ) {
                                append(answerText)
                            }

                        }

                        Text(
                            text = annotatedString,
                            modifier = Modifier
                                .padding(6.dp)
                        )
                    }
                }

                Button(
                    onClick = { onNextClicked(questionIndex.value) },
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(36.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.mLightBlue
                    )
                ) {
                    Text(
                        text = "Next",
                        modifier = Modifier
                            .padding(4.dp),
                        color = AppColors.mOffWhite,
                        fontSize = 16.sp
                    )
                }

            }
        }

    }
}

@Composable
fun DrawDottedLine(pathEffect: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = AppColors.mLightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
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
                    append("Question ${counter + 1}/")
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


@Preview
@Composable
fun ShowProgress(score: Int = 12) {

    val gradient = Brush.linearGradient(
        listOf(
            Color(0xFFF95075),
            Color(0xFFBE6BE5)
        )
    )

    val progressFactorStateValue by remember(score) { mutableStateOf(score * 0.005f) }

    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(45.dp)
            .border(
                width = 4.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        AppColors.mLightPurple,
                        AppColors.mLightPurple
                    )
                ),
                shape = RoundedCornerShape(36.dp)
            )
            .clip(
                RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomStartPercent = 50,
                    bottomEndPercent = 50
                )
            )
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { },
            contentPadding = PaddingValues(1.dp),
            modifier = Modifier
                .fillMaxWidth(progressFactorStateValue)
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {
            Text(
                text = "${score * 10}",
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(24.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(6.dp),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center
            )
        }

    }
}