package com.moralabs.pet.profile.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.moralabs.pet.databinding.FragmentProfileViewPagerBinding

class ProfileViewPagerFragment : Fragment {

    private var adapter:  RecyclerView.Adapter<*>? = null

    constructor(adapter: RecyclerView.Adapter<*>) : super(){
        this.adapter = adapter
    }

    constructor() : super()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentProfileViewPagerBinding.inflate(layoutInflater, container, false)

        binding?.recyclerView?.adapter = adapter

        (binding?.recyclerView?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        return binding?.root
    }
}