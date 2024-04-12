package com.example.job_seeker.presentation.screen.bottomNavFragment

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.job_seeker.R
import com.example.job_seeker.databinding.FragmentBottomNavBinding
import com.example.job_seeker.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BottomNavFragment :
    BaseFragment<FragmentBottomNavBinding>(FragmentBottomNavBinding::inflate) {

    override fun setUp() {
        setBottomNavBar()
    }

    private fun setBottomNavBar() {
        with(binding.bottomNav) {
            val nestedNavHostFragment = childFragmentManager.findFragmentById(
                R.id.nested_nav_host_fragment
            ) as NavHostFragment

            setupWithNavController(
                nestedNavHostFragment.navController
            )

            nestedNavHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
                val destinationsToHideBottomNav =
                    setOf(
                        R.id.jobFragment
                    )

                if (destination.id in destinationsToHideBottomNav) {
                    this.visibility = GONE
                } else {
                    this.visibility = VISIBLE
                }
            }
        }
    }
}

