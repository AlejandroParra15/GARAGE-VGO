package com.example.garage_vgo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.garage_vgo.databinding.FragmentVehicleBinding

class VehicleFragment : Fragment() {

    private lateinit var binding: FragmentVehicleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVehicleBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = VehicleFragment()
    }
}