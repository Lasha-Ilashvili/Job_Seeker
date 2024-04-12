package com.example.job_seeker.presentation.screen.bottomNavFragment

import com.example.job_seeker.databinding.FragmentBottomNavBinding
import com.example.job_seeker.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BottomNavFragment :
    BaseFragment<FragmentBottomNavBinding>(FragmentBottomNavBinding::inflate) {


    override fun setUp() {
//        setBottomNavBar()
    }


//    private fun setBottomNavBar() {
//        with(binding.bottomNav) {
//            val nestedNavHostFragment = childFragmentManager.findFragmentById(
//                R.id.nested_nav_host_fragment
//            ) as NavHostFragment
//
//            setupWithNavController(
//                nestedNavHostFragment.navController
//            )
//
//            nestedNavHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
//                val destinationsToHideBottomNav =
//                    setOf(
//                        R.id.profileFragment,
//                        R.id.walletFragment,
//                        R.id.startParkingFragment,
//                        R.id.addVehicleFragment,
//                        R.id.balanceFragment,
//                        R.id.buyLicenseFragment,
//                        R.id.parkingIsStartedFragment,
//                        R.id.activeLicensesFragment
//                    )
//
//                if (destination.id in destinationsToHideBottomNav) {
//                    this.visibility = View.GONE
//                } else {
//                    this.visibility = View.VISIBLE
//                }
//            }
//        }
//    }
}

