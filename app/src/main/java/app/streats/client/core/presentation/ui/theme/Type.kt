package app.streats.client.core.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import app.streats.client.R

// Set of Material typography styles to start with


val primaryFonts = FontFamily(

    Font(R.font.montserrat_extrabold),
    Font(R.font.montserrat_light),
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_bold)
)

val Typography = Typography(
    defaultFontFamily = primaryFonts,
    body1 = TextStyle(
        fontFamily = primaryFonts,
        fontWeight = FontWeight.Normal

    ),
    h6 = TextStyle(
        fontFamily = primaryFonts,
        fontWeight = FontWeight.Bold,

        ),

    h5 = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
        fontWeight = FontWeight.Bold,
        color = LightBlack
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Bold,
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.W200,
//        fontWeight = FontWeight.ExtraBold,
        color = LightBlack
    ),
    button = TextStyle(
        fontFamily = primaryFonts,
        color = CulturedWhite
    ),

    caption = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
        fontWeight = FontWeight.Normal,
        color = DarkGrayBackground.copy(alpha = 0.4f),
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = primaryFonts,
        fontWeight = FontWeight.Bold,
        color = DarkGrayBackground,
        fontSize = 16.sp
    )
)


val ButtonCaption = TextStyle(
    fontFamily = primaryFonts,
    color = DarkGrayBackground.copy(alpha = 0.4f),
    fontSize = 16.sp,
    fontWeight = FontWeight.W100,
    textAlign = TextAlign.Center
)

val SectionHeading = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
    color = LightBlack.copy(alpha = 0.9f),
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Left
)

val Caption = TextStyle(
    fontFamily = primaryFonts,
    fontWeight = FontWeight.Light,
    color = DarkGrayBackground.copy(alpha = 0.4f),
    fontSize = 16.sp
)


val Greeting = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_extrabold)),
    color = LightBlack,
    fontSize = 40.sp
)

val NearbyCardTitle = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
    color = LightBlack,
    fontSize = 20.sp
)

val CardCaption = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
    color = LightGrayText,
    fontSize = 12.sp
)


val ShopName = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
    color = LightBlack,
    fontSize = 35.sp
)

val Price = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
    color = LightBlack,
    fontSize = 14.sp
)

val DishName = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
    color = LightBlack,
    fontSize = 16.sp
)

val CartScreenShopName = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
    color = LightBlack,
    fontSize = 24.sp
)

val CheckoutCardText = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
    color = CulturedWhite,
    fontSize = 16.sp,
    fontWeight = FontWeight.W100
)


val CheckoutTotal = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
    color = LightBlack,
    fontSize = 24.sp,
    fontWeight = FontWeight.W100
)

val BillDetails = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
    color = LightBlack,
    fontSize = 14.sp,
    fontWeight = FontWeight.W100
)

val OrderConfirmationText = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
    color = LightGrayText,
    fontSize = 14.sp,
    fontWeight = FontWeight.W100
)