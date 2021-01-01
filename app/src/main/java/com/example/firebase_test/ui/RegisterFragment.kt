package com.example.firebase_test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firebase_test.MainViewModel
import com.example.firebase_test.databinding.RegisterFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {
    private lateinit var binding: RegisterFragmentBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.register.setOnClickListener {
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            viewModel.onRegister(
                userName = name,
                password = password,
                email = email
            )
        }
    }
}