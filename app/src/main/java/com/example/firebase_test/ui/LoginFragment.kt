package com.example.firebase_test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firebase_test.MainActivity
import com.example.firebase_test.MainViewModel
import com.example.firebase_test.databinding.LoginFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            user?.let {
                // already signed in
            } ?: run {
                // sign in or register
            }
        }

        binding.login.setOnClickListener {
            val emailValue = binding.email.text.toString()
            val passwordValue = binding.password.text.toString()

            viewModel.onSignIn(
                email = emailValue,
                password = passwordValue
            )
        }

        binding.register.setOnClickListener {
            val activity = requireActivity()
            if (activity is MainActivity) {
                activity.addFragment(RegisterFragment(), "Register")
            }
        }
    }
}