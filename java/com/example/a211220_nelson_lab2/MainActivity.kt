package com.example.a211220_nelson_lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a211220_nelson_lab2.ui.theme.A211220_Nelson_Lab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            A211220_Nelson_Lab1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFF1F7)
                ) {
                    MindMateScreen()
                }
            }
        }
    }
}

data class MoodEntry(
    val emotion: String = "",
    val journal: String = ""
)

@Composable
fun MindMateScreen() {
    var selectedDay by remember { mutableStateOf(1) }
    var selectedEmotion by remember { mutableStateOf("") }
    var moodNote by remember { mutableStateOf("") }
    var savedMessage by remember { mutableStateOf("") }

    val moodMap = remember { mutableStateMapOf<Int, MoodEntry>() }

    fun loadEntryForDay(day: Int) {
        val entry = moodMap[day] ?: MoodEntry()
        selectedEmotion = entry.emotion
        moodNote = entry.journal
        savedMessage = ""
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFFFF1F7),
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF1F7))
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            ) {
                if (savedMessage.isNotEmpty()) {
                    Text(
                        text = savedMessage,
                        fontSize = 14.sp,
                        color = Color(0xFF5C3A46),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        moodMap[selectedDay] = MoodEntry(
                            emotion = selectedEmotion,
                            journal = moodNote
                        )
                        savedMessage = "Entry saved for $selectedDay Apr 2026"
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE57AA6)
                    )
                ) {
                    Text(
                        text = "Save Entry",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    ) { innerPadding ->
        MindMateUI(
            modifier = Modifier.padding(innerPadding),
            selectedDay = selectedDay,
            selectedEmotion = selectedEmotion,
            moodNote = moodNote,
            moodMap = moodMap,
            onDayClick = { day ->
                selectedDay = day
                loadEntryForDay(day)
            },
            onEmotionClick = { emotion ->
                selectedEmotion = emotion

                // terus update bulatan tarikh bila emoji dipilih
                val oldJournal = moodMap[selectedDay]?.journal ?: moodNote
                moodMap[selectedDay] = MoodEntry(
                    emotion = emotion,
                    journal = oldJournal
                )
            },
            onMoodNoteChange = { newText ->
                moodNote = newText
            }
        )
    }
}

@Composable
fun MindMateUI(
    modifier: Modifier = Modifier,
    selectedDay: Int,
    selectedEmotion: String,
    moodNote: String,
    moodMap: Map<Int, MoodEntry>,
    onDayClick: (Int) -> Unit,
    onEmotionClick: (String) -> Unit,
    onMoodNoteChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF1F7))
            .padding(horizontal = 18.dp, vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(width = 64.dp, height = 38.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFF7CADB)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Logo",
                    tint = Color(0xFFE57AA6),
                    modifier = Modifier.size(20.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color(0xFFE57AA6),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color(0xFF6B4B57),
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "MindMate",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5C3A46)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Apr 2026 ˅",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF5C3A46)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach {
                Text(
                    text = it,
                    fontSize = 12.sp,
                    color = Color(0xFF8B6B77),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        val weeks = listOf(
            listOf(0, 0, 0, 1, 2, 3, 4),
            listOf(5, 6, 7, 8, 9, 10, 11),
            listOf(12, 13, 14, 15, 16, 17, 18),
            listOf(19, 20, 21, 22, 23, 24, 25),
            listOf(26, 27, 28, 29, 30, 0, 0)
        )

        weeks.forEach { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                week.forEach { day ->
                    if (day == 0) {
                        Spacer(modifier = Modifier.size(36.dp))
                    } else {
                        val currentEntry = moodMap[day] ?: MoodEntry()

                        CalendarCircle(
                            day = day,
                            isSelected = day == selectedDay,
                            emotion = currentEntry.emotion,
                            onClick = { onDayClick(day) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Selected date: $selectedDay Apr 2026",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF5C3A46),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Choose your emotion",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF5C3A46),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val emotionList = listOf("😊", "😢", "😡", "😴", "😍")

            emotionList.forEach { emotion ->
                EmotionCircle(
                    emoji = emotion,
                    isSelected = selectedEmotion == emotion,
                    onClick = { onEmotionClick(emotion) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "How do you feel today?",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF5C3A46),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value = moodNote,
            onValueChange = onMoodNoteChange,
            label = {
                Text(
                    text = "Write your note here",
                    color = Color(0xFF9C7C88)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            maxLines = 2,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFF8FB),
                unfocusedContainerColor = Color(0xFFFFF8FB),
                focusedIndicatorColor = Color(0xFFE57AA6),
                unfocusedIndicatorColor = Color(0xFFF3C6D8),
                focusedTextColor = Color(0xFF5C3A46),
                unfocusedTextColor = Color(0xFF5C3A46),
                focusedLabelColor = Color(0xFFE57AA6),
                unfocusedLabelColor = Color(0xFF9C7C88),
                cursorColor = Color(0xFFE57AA6)
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        val selectedEntry = moodMap[selectedDay] ?: MoodEntry()

        if (selectedEntry.emotion.isNotEmpty() || selectedEntry.journal.isNotEmpty()) {
            Text(
                text = "Saved emotion: ${if (selectedEntry.emotion.isNotEmpty()) selectedEntry.emotion else "-"}",
                fontSize = 14.sp,
                color = Color(0xFF5C3A46),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Saved journal: ${if (selectedEntry.journal.isNotEmpty()) selectedEntry.journal else "-"}",
                fontSize = 14.sp,
                color = Color(0xFF5C3A46),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color(0xFFF8D9E5))
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.CalendarToday,
                contentDescription = "Calendar",
                tint = Color(0xFFE57AA6),
                modifier = Modifier.size(24.dp)
            )

            Icon(
                Icons.AutoMirrored.Filled.ShowChart,
                contentDescription = "Insights",
                tint = Color(0xFF9C7C88),
                modifier = Modifier.size(24.dp)
            )

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFC1D6)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Face,
                    contentDescription = "Mood",
                    tint = Color(0xFF6B4B57),
                    modifier = Modifier.size(26.dp)
                )
            }

            Icon(
                Icons.Default.ShoppingCart,
                contentDescription = "Shop",
                tint = Color(0xFF9C7C88),
                modifier = Modifier.size(24.dp)
            )

            Icon(
                Icons.Default.Person,
                contentDescription = "Profile",
                tint = Color(0xFF9C7C88),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun CalendarCircle(
    day: Int,
    isSelected: Boolean,
    emotion: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(
                    when {
                        isSelected && emotion.isNotEmpty() -> Color(0xFFFFAFCB)
                        isSelected -> Color(0xFFFFD6E5)
                        emotion.isNotEmpty() -> Color(0xFFFFD6E5)
                        else -> Color(0xFFF3C6D8)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (emotion.isNotEmpty()) {
                Text(
                    text = emotion,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = day.toString(),
            fontSize = 12.sp,
            color = if (isSelected) Color(0xFFE57AA6) else Color(0xFF6B4B57),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun EmotionCircle(
    emoji: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) Color(0xFFFFAFCB) else Color(0xFFF3C6D8)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MindMatePreview() {
    A211220_Nelson_Lab1Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFFFF1F7)
        ) {
            MindMateScreen()
        }
    }
}