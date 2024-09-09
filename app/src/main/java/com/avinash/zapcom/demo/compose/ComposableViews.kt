package com.avinash.zapcom.demo.compose

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.avinash.zapcom.demo.R
import com.avinash.zapcom.demo.model.ProductItem

@Composable
fun HorizontalSpacer() {
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(12.dp))
}

@Composable
fun BannerImage(
    modifier: Modifier = Modifier,
    item: ProductItem
) {
    val configuration = LocalConfiguration.current
    Box(
        modifier = modifier
            .width(configuration.screenWidthDp.dp)
            .height(240.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.imageURL)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .border(4.dp, Color.White, RectangleShape)
        )
    }
}

@Composable
fun SplitBannerImage(
    modifier: Modifier = Modifier,
    item: ProductItem
) {
    val configuration = LocalConfiguration.current
    Box(
        modifier = modifier
            .width((configuration.screenWidthDp * 0.5).dp)
            .height(240.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.imageURL)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .border(4.dp, Color.White, RectangleShape)
        )
    }
}

@Composable
fun TileImage(
    modifier: Modifier = Modifier,
    item: ProductItem
) {
    Box(
        modifier = modifier
            .width(124.dp)
            .height(124.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.imageURL)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .border(4.dp, Color.White, RectangleShape)
        )
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    message: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .testTag(GenericViewTags.Error.ERROR_VIEW.name)
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .background(colorResource(id = R.color.white))
    ) {
        Column {
            AppText(
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .testTag(GenericViewTags.Error.ERROR_TITLE.name)
                    .padding(bottom = 8.dp),
                value = message,
                colorValue = R.color.black,
                dimenValue = 18,
                fontWeight = FontWeight.Bold,
                alignment = TextAlign.Center
            )

            Button(
                onClick = onClick,
                modifier = Modifier.testTag(GenericViewTags.Error.ERROR_BUTTON.name).align(Alignment.CenterHorizontally)
            ) {
                AppText(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .testTag(GenericViewTags.Error.ERROR_BUTTON_TEXT.name),
                    value = stringResource(id = R.string.retry_button),
                    colorValue = R.color.white,
                    dimenValue = 12,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

@Composable
fun AppText(
    modifier: Modifier,
    value: String,
    @ColorRes colorValue: Int,
    dimenValue: Int,
    fontWeight: FontWeight,
    alignment: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = value,
        modifier = modifier,
        style = TextStyle(
            color = colorResource(id = colorValue),
            fontSize = dimenValue.sp
        ),
        fontWeight = fontWeight,
        textAlign = alignment,
        maxLines = maxLines,
        minLines = minLines,
        overflow = overflow
    )
}

@Composable
fun FullScreenLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .background(colorResource(id = R.color.white))
            .testTag(GenericViewTags.Progress.PROGRESS_VIEW.name)
    ) {
        CircularProgressIndicator(color = colorResource(id = R.color.teal_700))
    }
}

object GenericViewTags {
    enum class Error {
        ERROR_VIEW,
        ERROR_TITLE,
        ERROR_BUTTON,
        ERROR_BUTTON_TEXT
    }

    enum class Progress{
        PROGRESS_VIEW
    }
}