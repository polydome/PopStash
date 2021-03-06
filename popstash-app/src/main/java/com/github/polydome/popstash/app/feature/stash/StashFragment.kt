package com.github.polydome.popstash.app.feature.stash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.polydome.popstash.app.databinding.FragmentStashBinding
import com.github.polydome.popstash.app.di.qualifier.BoundViewModel
import com.github.polydome.popstash.app.presentation.viewmodel.StashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class StashFragment @Inject constructor(
        private val stashAdapter: StashAdapter,
        private val linearLayoutManager: LinearLayoutManager,
        @BoundViewModel private val stashViewModel: StashViewModel,
        private val itemTouchHelper: ItemTouchHelper,
) : Fragment() {
    lateinit var binding: FragmentStashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding(inflater, container)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupStashList()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        cleanupStashList()
    }

    private fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentStashBinding.inflate(inflater, container, false)
                .also {
                    it.viewModel = stashViewModel
                    it.lifecycleOwner = this
                }
    }

    private fun setupStashList() {
        binding.stashRecyclerView.apply {
            adapter = stashAdapter
            layoutManager = linearLayoutManager.apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun cleanupStashList() {
        binding.stashRecyclerView.layoutManager = null
    }
}