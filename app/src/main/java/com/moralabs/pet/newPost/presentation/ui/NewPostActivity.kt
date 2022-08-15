package com.moralabs.pet.newPost.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moralabs.pet.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
    }
}