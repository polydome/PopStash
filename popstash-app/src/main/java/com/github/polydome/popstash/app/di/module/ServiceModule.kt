package com.github.polydome.popstash.app.di.module

import com.github.polydome.popstash.app.presentation.common.InternetBrowser
import com.github.polydome.popstash.app.platform.MainActivity
import com.github.polydome.popstash.app.platform.event.WindowEventBus
import com.github.polydome.popstash.app.platform.event.WindowEventEmitter
import com.github.polydome.popstash.app.platform.event.WindowEventListener
import com.github.polydome.popstash.app.platform.service.*
import com.github.polydome.popstash.app.platform.settings.Settings
import com.github.polydome.popstash.app.platform.settings.SettingsManager
import com.github.polydome.popstash.app.platform.settings.ThemeProvider
import com.github.polydome.popstash.app.presentation.common.Clipboard
import com.github.polydome.popstash.app.presentation.common.PatternMatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class ServiceModule {
    @Binds
    @ActivityScoped
    abstract fun clipboard(androidClipboard: AndroidClipboard): Clipboard

    @Binds
    @ActivityScoped
    abstract fun patternMatcher(androidPatternMatcher: AndroidPatternMatcher): PatternMatcher

    @Binds
    @ActivityScoped
    abstract fun windowEventEmitter(windowEventBus: WindowEventBus): WindowEventEmitter

    @Binds
    @ActivityScoped
    abstract fun windowEventListener(windowEventBus: WindowEventBus): WindowEventListener

    @Binds
    @ActivityScoped
    abstract fun internetBrowser(activity: MainActivity): InternetBrowser

    @Binds
    @ActivityScoped
    abstract fun settings(settingsManager: SettingsManager): Settings

    @Binds
    @ActivityScoped
    abstract fun themeProvider(settingsManager: SettingsManager): ThemeProvider
}