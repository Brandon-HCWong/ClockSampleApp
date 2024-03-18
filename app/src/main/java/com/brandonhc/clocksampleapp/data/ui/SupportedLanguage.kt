package com.brandonhc.clocksampleapp.data.ui

import java.util.Locale

enum class SupportedLanguage(val locale: Locale) {
    ENGLISH(Locale.ENGLISH),
    TRADITIONAL_CHINESE(Locale.TRADITIONAL_CHINESE);

    companion object {
        fun getDefault() = if (Locale.getDefault().language.contains("zh")) {
            TRADITIONAL_CHINESE
        } else {
            ENGLISH
        }

        fun getCharSequenceArray(): Array<CharSequence> = entries.map { it.locale.displayName }.toTypedArray()

        fun fromIndex(index: Int) = entries[index]
    }
}