package com.example.garage_vgo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.garage_vgo.databinding.FragmentNotificationsBinding

class FragmentNotifications : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(layoutInflater, container, false)

        binding.notificationTecnico.setOnClickListener { goSoat() }
        binding.notificationAceite.setOnClickListener { goCambioAceite() }
        binding.notificationImpuestos.setOnClickListener { goSoat() }
        binding.notificationSoat.setOnClickListener {goSoat()  }
        binding.notificationPicoyplaca.setOnClickListener { goPicoYPlaca() }

        return binding.root
    }

    // Method that directs the user to the ActivityNotificationPicoYPlaca activity
    private fun goPicoYPlaca(){
        val viewImage = Intent(requireContext(), ActivityNotificationPicoYPlaca::class.java)
        startActivity(viewImage)
    }

    // Method that directs the user to the ActivityNotificationPicoYPlaca activity
    private fun goSoat(){
        val viewImage = Intent(requireContext(), NotificationSoatTecnicoImpue::class.java)
        startActivity(viewImage)
    }

    // Method that directs the user to the ActivityNotificationPicoYPlaca activity
    private fun goCambioAceite(){
        val viewImage = Intent(requireContext(), KilometrajeCambioDeAceite::class.java)
        startActivity(viewImage)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentNotifications()
    }
}