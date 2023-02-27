package com.example.appwithretrofit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.appwithretrofit.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FragmentMain : Fragment() {

    private lateinit var binding: FragmentMainBinding

    val logging = HttpLoggingInterceptor()
    val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

    val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val dogApiService = retrofit.create(DogApiService::class.java)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonDog.setOnClickListener {
            logging.level = HttpLoggingInterceptor.Level.BODY
            retrieveDog() }


    }

    fun retrieveDog() {
        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val randomDog = dogApiService.getRandomDogImage()
                Glide.with(binding.fragmentMain).load(randomDog.message).into(binding.dogImageView)

            } catch (e: Exception) {

                Log.e("FragmentMain", "Error retrieving dogs")
                Snackbar.make(
                    binding.fragmentMain,
                    "Error connection", Snackbar.LENGTH_INDEFINITE
                ).setAction("Retry") { retrieveDog() }.show()

            }
        }
        /* Cercando su internet come dare ad una ImageView un'immagine presa da internet ho
           trovato la liberia Glide che permette di farlo più facilmente */
    }

}