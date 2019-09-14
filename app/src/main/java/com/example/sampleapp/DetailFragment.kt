package com.example.sampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.example.sampleapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels { ViewModelFactory.get() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = checkNotNull(DataBindingUtil.bind<FragmentDetailBinding>(view))

        binding.button.setOnClickListener { userViewModel.register() }

        userViewModel.state.observe(viewLifecycleOwner) { state ->
            if (state == UserState.REGISTERED) {
                binding.button.isEnabled = false
                binding.button.text = "Registered"
            }
        }
    }
}
