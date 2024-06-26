package com.example.uxmapp2.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.uxmapp2.R
import com.example.uxmapp2.data.MarvelApi
import com.example.uxmapp2.data.remote.ApiClient
import com.example.uxmapp2.databinding.FragmentSettingsBinding
import com.example.uxmapp2.source.Constants
import com.example.uxmapp2.source.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Load existing keys if available
        binding.publicKeyEditText.setText(sharedPreferences.getString("PUBLIC_KEY", ""))
        binding.privateKeyEditText.setText(sharedPreferences.getString("PRIVATE_KEY", ""))

        binding.saveButton.setOnClickListener {
            val publicKey = binding.publicKeyEditText.text.toString()
            val privateKey = binding.privateKeyEditText.text.toString()

            sharedPreferences.edit().putString("PUBLIC_KEY", publicKey).apply()
            sharedPreferences.edit().putString("PRIVATE_KEY", privateKey).apply()

            Constants.PUBLIC_KEY = publicKey
            Constants.PRIVATE_KEY = privateKey

            verifyApiCredentials(publicKey, privateKey)
        }

        binding.exitButton.setOnClickListener {
            showExitConfirmationDialog()
        }

        binding.showPrivateKeyCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.privateKeyEditText.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.privateKeyEditText.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

        return binding.root
    }

    private fun verifyApiCredentials(publicKey: String, privateKey: String) {
        lifecycleScope.launch {
            val state = withContext(Dispatchers.IO) { testApiCredentials(publicKey, privateKey) }
            when (state) {
                is State.Success -> {
                    Toast.makeText(requireContext(), "API Credentials Saved", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.navigation_heroes)
                }
                is State.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Unexpected Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun testApiCredentials(publicKey: String, privateKey: String): State<Boolean> {
        return try {
            val api = ApiClient.getClient().create(MarvelApi::class.java)
            val response = api.getAllCharacters(apikey = publicKey, ts = Constants.timeStamp, hash = Constants.hash(), search = "test")
            if (response.code == 200) {
                State.Success(true)
            } else {
                State.Error("Invalid API Credentials")
            }
        } catch (e: HttpException) {
            State.Error(e.message ?: "An unknown error occurred")
        }
    }

    private fun showExitConfirmationDialog() {
        android.app.AlertDialog.Builder(requireContext())
            .setMessage("Are you sure you want to exit the app, Hero?")
            .setPositiveButton("Yes") { _, _ ->
                requireActivity().finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}































