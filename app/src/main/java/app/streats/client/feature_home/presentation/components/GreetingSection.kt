package app.streats.client.feature_home.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.streats.client.core.presentation.ui.theme.Greeting
import app.streats.client.core.presentation.ui.theme.Typography

@Composable
fun GreetingSection(username: String) {

    val fullNameList = username.split(' ')
    val firstname = fullNameList[0]
    val greeting = "Hey $firstname!"
    val caption = "Let's get you something spicy 😋"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(text = greeting, style = Greeting)
        Text(text = caption, style = Typography.caption)
        Spacer(modifier = Modifier.height(30.dp))

    }
}