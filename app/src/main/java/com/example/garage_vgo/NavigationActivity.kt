package com.example.garage_vgo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.garage_vgo.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity(){

    private lateinit var binding: ActivityNavigationBinding
    private lateinit var profileFragmentFragment : ProfileFragment
    private lateinit var notifyFragment : FragmentNotifications
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("EMAIL",null)
        val name = sharedPreferences.getString("NAME",null)
        binding.profileFragment.text = name

        profileFragmentFragment = ProfileFragment.newInstance()
        notifyFragment = FragmentNotifications.newInstance()
        //postFragment = PostFragment.newInstance()

        showFragment(profileFragmentFragment)
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem->
            if(menuItem.itemId == R.id.profile){
                showFragment(profileFragmentFragment)
            }else if(menuItem.itemId == R.id.notify){
                showFragment(notifyFragment)
            }else{
                showFragment(profileFragmentFragment)
            }
            true
        }

        val toolbar = binding.profileToolbar
        setSupportActionBar(toolbar)

    }

    fun showFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.itemOne)
            Toast.makeText(this,"ITEM ONE", Toast.LENGTH_LONG).show()
        return super.onOptionsItemSelected(item)
    }

}