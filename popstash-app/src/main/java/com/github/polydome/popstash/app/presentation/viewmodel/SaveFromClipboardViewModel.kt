package com.github.polydome.popstash.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.polydome.popstash.app.presentation.base.ReactiveViewModel
import com.github.polydome.popstash.app.presentation.common.Clipboard
import com.github.polydome.popstash.app.presentation.common.PatternMatcher
import com.github.polydome.popstash.domain.usecase.SaveResource
import com.github.polydome.popstash.domain.usecase.WatchResourceExists
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SaveFromClipboardViewModel @Inject constructor(
        saveResource: SaveResource,
        watchResourceExists: WatchResourceExists,
        clipboard: Clipboard,
        patternMatcher: PatternMatcher,
) : ReactiveViewModel<SaveFromClipboardViewModel.Command>() {
    enum class Command {
        SAVE
    }

    private val isUrlInClipboard = clipboard.contents()
            .map(patternMatcher::matchUrl)

    private val url = clipboard.contents()
            .filter(patternMatcher::matchUrl)

    private val urlAlreadySaved = url
            .flatMapLatest(watchResourceExists::execute)

    val shouldDisplayDialog: LiveData<Boolean> =
            isUrlInClipboard
                    .combine(urlAlreadySaved) { isUrl, alreadySaved -> isUrl && !alreadySaved }
                    .asLiveData()

    val urlInClipboard: LiveData<String> = url.asLiveData()

    init {
        commands.filter { command -> command == Command.SAVE }
                .map { urlInClipboard.value }
                .filterNotNull()
                .map(saveResource::execute)
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
    }
}