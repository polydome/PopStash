package com.github.polydome.popstash.app.feature.stash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.github.polydome.popstash.app.databinding.RowResourceBinding
import com.github.polydome.popstash.app.di.scope.BoundViewModel
import com.github.polydome.popstash.app.presentation.viewmodel.ResourceViewModel
import javax.inject.Inject
import javax.inject.Provider

class ResourceViewHolder(itemView: View, private val viewModel: ResourceViewModel) : RecyclerView.ViewHolder(itemView) {
    fun showUrl(url: String) {
        viewModel.showUrl(url)
    }

    class Factory @Inject constructor(private val layoutInflater: LayoutInflater,
                                      private val lifecycleOwner: LifecycleOwner,
                                      @BoundViewModel private val viewModelProvider: Provider<ResourceViewModel>) {
        fun create(parentView: ViewGroup): ResourceViewHolder {
            val viewModel = viewModelProvider.get()
            val binding = RowResourceBinding
                    .inflate(layoutInflater, parentView, false)
                    .apply {
                        lifecycleOwner = this@Factory.lifecycleOwner
                        viewmodel = viewModel
                    }
            return ResourceViewHolder(binding.root, viewModel)
        }
    }
}