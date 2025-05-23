package com.abdownloadmanager.desktop.window.custom.titlebar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abdownloadmanager.desktop.window.custom.TitlePosition
import com.abdownloadmanager.shared.utils.ui.WithContentAlpha
import ir.amirab.util.compose.layout.RelativeAlignment
import ir.amirab.util.ifThen
import kotlin.math.roundToInt

object MacTitleBar : TitleBar {
    override val systemButtonsFirst: Boolean = true
    override val titleBarHeight: Dp = TitleBar.DefaultTitleBarHeigh

    @Composable
    override fun RenderSystemButtons(
        onRequestClose: () -> Unit,
        onRequestMinimize: (() -> Unit)?,
        onToggleMaximize: (() -> Unit)?
    ) {
        MacOSSystemButtons(
            onRequestClose = onRequestClose,
            onRequestMinimize = onRequestMinimize,
            onToggleMaximize = onToggleMaximize,
        )
    }

    @Composable
    override fun RenderTitleBarContent(
        title: String,
        titlePosition: TitlePosition,
        modifier: Modifier,
        windowIcon: Painter?,
        start: @Composable (() -> Unit)?,
        end: @Composable (() -> Unit)?
    ) {
        MacTitleBarContent(
            modifier = modifier,
            title = title,
            windowIcon = windowIcon,
            titlePosition = titlePosition,
            systemButtonsWidth = 60.dp,
            start = start,
            end = end,
        )
    }

    @Composable
    override fun RenderTitleBar(
        modifier: Modifier,
        titleBar: TitleBar,
        title: String,
        windowIcon: Painter?,
        titlePosition: TitlePosition,
        start: @Composable (() -> Unit)?,
        end: @Composable (() -> Unit)?,
        onRequestClose: () -> Unit,
        onRequestMinimize: (() -> Unit)?,
        onRequestToggleMaximize: (() -> Unit)?
    ) {
        CommonRenderTitleBar(
            modifier = modifier,
            titleBar = titleBar,
            title = title,
            windowIcon = windowIcon,
            titlePosition = titlePosition,
            start = start,
            end = end,
            onRequestClose = onRequestClose,
            onRequestMinimize = onRequestMinimize,
            onRequestToggleMaximize = onRequestToggleMaximize,
        )
    }
}

@Composable
private fun MacTitleBarContent(
    modifier: Modifier,
    title: String,
    windowIcon: Painter?,
    titlePosition: TitlePosition,
    systemButtonsWidth: Dp,
    start: @Composable (() -> Unit)?,
    end: @Composable (() -> Unit)?
) {
    val density = LocalDensity.current
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val titleShouldBeCentered = titlePosition.centered || start == null
        val afterStart = titlePosition.afterStart
        if (!afterStart) {
            Row(
                Modifier
                    .ifThen(titleShouldBeCentered) {
                        weight(1f)
                            .wrapContentWidth(
                                RelativeAlignment.Horizontal(
                                    mainAlignment = Alignment.CenterHorizontally,
                                    relative = -(density.run {
                                        systemButtonsWidth.toPx()
                                    }.roundToInt())
                                )
                            )
                    }
                    .padding(titlePosition.padding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(Modifier.width(8.dp))
                windowIcon?.let {
                    WithContentAlpha(1f) {
                        Image(it, null, Modifier.size(16.dp))
                    }
                    Spacer(Modifier.width(8.dp))
                }
                Title(
                    modifier = Modifier,
                    title = title
                )
            }
        }
        start?.let {
            Row(
                Modifier
            ) {
                start()
                Spacer(Modifier.width(8.dp))
            }
        }
        if (afterStart) {
            Row(
                Modifier
                    .weight(1f)
                    .ifThen(titleShouldBeCentered) {
                        wrapContentWidth()
                    }
                    .padding(titlePosition.padding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(Modifier.width(8.dp))
                windowIcon?.let {
                    WithContentAlpha(1f) {
                        Image(it, null, Modifier.size(16.dp))
                    }
                    Spacer(Modifier.width(8.dp))
                }
                Title(
                    modifier = Modifier,
                    title = title
                )
            }
        }
        if (!titleShouldBeCentered && !titlePosition.afterStart) {
            Spacer(Modifier.weight(1f))
        }
        end?.let {
            Row(
                Modifier
            ) {
                end()
                Spacer(Modifier.width(4.dp))
            }
        }
    }
}
