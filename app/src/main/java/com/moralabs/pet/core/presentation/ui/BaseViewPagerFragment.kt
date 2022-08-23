package com.moralabs.pet.core.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.moralabs.pet.databinding.FragmentBaseViewPagerBinding

class BaseViewPagerFragment : Fragment {

    private var adapter: RecyclerView.Adapter<*>? = null

    constructor(adapter: RecyclerView.Adapter<*>) : super() {
        this.adapter = adapter
    }

    constructor() : super()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentBaseViewPagerBinding.inflate(layoutInflater, container, false)

        binding.recyclerView.adapter = adapter

        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        return binding.root
    }
}