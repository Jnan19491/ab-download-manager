package com.abdownloadmanager.desktop.window.custom.titlebar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abdownloadmanager.desktop.window.custom.TitlePosition
import ir.amirab.util.platform.Platform
import ir.amirab.util.platform.asDesktop

interface TitleBar {
    val systemButtonsFirst: Boolean
    val titleBarHeight: Dp

    @Composable
    fun RenderSystemButtons(
        onRequestClose: () -> Unit,
        onRequestMinimize: (() -> Unit)?,
        onToggleMaximize: (() -> Unit)?,
    )

    @Composable
    fun RenderTitleBarContent(
        title: String,
        titlePosition: TitlePosition,
        modifier: Modifier,
        windowIcon: Painter?,
        start: (@Composable () -> Unit)?,
        end: (@Composable () -> Unit)?,
    )

    @Composable
    fun RenderTitleBar(
        modifier: Modifier,
        titleBar: TitleBar,
        title: String,
        windowIcon: Painter?,
        titlePosition: TitlePosition,
        start: (@Composable () -> Unit)?,
        end: (@Composable () -> Unit)?,
        onRequestClose: () -> Unit,
        onRequestMinimize: (() -> Unit)?,
        onRequestToggleMaximize: (() -> Unit)?,
    )

    companion object {
        val DefaultTitleBarHeigh = 32.dp
        fun getPlatformTitleBar(): TitleBar {
            return when (Platform.asDesktop()) {
                Platform.Desktop.Windows -> WindowsTitleBar
                Platform.Desktop.MacOS -> MacTitleBar
                Platform.Desktop.Linux -> LinuxTitleBar
            }
        }
    }
}
