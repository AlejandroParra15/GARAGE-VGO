package com.example.garage_vgo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.garage_vgo.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container,false)
        sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("EMAIL",null)
        val name = sharedPreferences.getString("NAME",null)

        binding.fullNameProfilefragment.text = name
        binding.bioProfilefragment.text = email

        binding.imagesGridViewBtn.setOnClickListener{goViewImage()}
        binding.imagesTagBtn.setOnClickListener {goViewImage()}

        return binding.root
    }

    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    // Method that directs the user to the home activity
    private fun goViewImage(){
        val viewImage = Intent(requireContext(), ViewImgActivity::class.java)
        startActivity(viewImage)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()

    }
}