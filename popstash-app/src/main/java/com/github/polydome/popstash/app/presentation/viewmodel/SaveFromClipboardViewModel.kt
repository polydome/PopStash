package com.github.polydome.popstash.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.polydome.popstash.app.presentation.service.Clipboard
import com.github.polydome.popstash.app.presentation.service.PatternMatcher
import com.github.polydome.popstash.domain.usecase.CheckResourceExists
import com.github.polydome.popstash.domain.usecase.SaveResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveFromClipboardViewModel @Inject constructor(
        private val saveResource: SaveResource,
        private val checkResourceExists: CheckResourceExists,
        private val clipboard: Clipboard,
        private val patternMatcher: PatternMatcher
) : ViewModel() {
    private val _isUrlInClipboard = MutableLiveData<Boolean>()
    private val _urlInClipboard = MutableLiveData(clipboard.getText())

    val isUrlInClipboard: LiveData<Boolean> = _isUrlInClipboard
    val urlInClipboard = _urlInClipboard

    fun saveUrlFromClipboard() {
        viewModelScope.launch {
            val url = clipboard.getText()

            withContext(Dispatchers.IO) {
                saveResource.execute(url)
            }
        }
    }

    fun checkClipboardForUrl() {
        val clipboardContent = clipboard.getText()
        val isUrlInClipboard = patternMatcher.matchUrl(clipboardContent)

        urlInClipboard.postValue(clipboardContent)

        if (isUrlInClipboard) {
            viewModelScope.launch {
                val urlAlreadySaved = checkResourceExists.execute(clipboardContent)

                withContext(Dispatchers.Main) {
                    _isUrlInClipboard.postValue(!urlAlreadySaved)
                }
            }
        } else {
            _isUrlInClipboard.postValue(false)
        }
    }
}