package com.example.sampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sampleapp.databinding.FragmentTopBinding


class TopFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = checkNotNull(DataBindingUtil.bind<FragmentTopBinding>(view))

        binding.button.setOnClickListener {
            findNavController().navigate(TopFragmentDirections.actionTopToDetail())
        }
    }
}
