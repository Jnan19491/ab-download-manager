package com.abdownloadmanager.shared.utils.ui

fun androidx.compose.foundation.v2.ScrollbarAdapter.needScroll(): Boolean {
    return contentSize > viewportSize
}