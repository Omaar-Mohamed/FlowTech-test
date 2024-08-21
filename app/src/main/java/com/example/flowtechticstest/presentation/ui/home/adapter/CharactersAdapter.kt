package com.example.flowtechticstest.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flowtechticstest.data.model.characters_response.CharacterResult
import com.example.flowtechticstest.databinding.CharacterItemBinding

class CharacterAdapter :
    ListAdapter<CharacterResult, CharacterAdapter.CharacterViewHolder>(CharacterDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        // Use ViewBinding to inflate the layout
        val binding = CharacterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    class CharacterViewHolder(private val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterResult) {
            // Bind data to views using ViewBinding
            binding.characterName.text = character.name
            binding.characterStatus.text = "Status: ${character.status}"

            // Assuming you're using Glide or a similar library to load images
            Glide.with(binding.characterImage.context)
                .load(character.image)
                .into(binding.characterImage)
        }
    }

    class CharacterDiffCallback : DiffUtil.ItemCallback<CharacterResult>() {
        override fun areItemsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
            return oldItem == newItem
        }
    }
}
