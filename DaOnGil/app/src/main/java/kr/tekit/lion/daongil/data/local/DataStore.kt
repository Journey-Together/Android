package kr.tekit.lion.daongil.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore

val Context.dataStore: DataStore<AppSettings> by dataStore(
    "app-settings.json",
    AppSettingsSerializer
)