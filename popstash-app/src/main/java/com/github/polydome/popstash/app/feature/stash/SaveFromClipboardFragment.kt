package com.github.polydome.popstash.app.feature.stash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.polydome.popstash.app.databinding.FragmentSaveFromClipboardBinding
import com.github.polydome.popstash.app.di.scope.BoundViewModel
import com.github.polydome.popstash.app.presentation.viewmodel.SaveFromClipboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SaveFromClipboardFragment : Fragment() {
    @Inject
    @BoundViewModel
    lateinit var viewModel: SaveFromClipboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return createBinding(inflater, container).root
    }

    private fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSaveFromClipboardBinding {
        return FragmentSaveFromClipboardBinding.inflate(inflater, container, false)
                .also {
                    it.lifecycleOwner = this
                    it.viewModel = viewModel
                }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkClipboardForUrl()
    }
}