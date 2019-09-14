package com.example.sampleapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TopFragmentTest {

    @Test
    fun navigateToDeTailFragment() {
        val controller = mockk<NavController>(relaxed = true)
        val scenario = launchFragmentInContainer<TopFragment>()

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), controller)
        }

        onView(withId(R.id.button)).perform(click())

        verify {
            controller.navigate(match<NavDirections> { direction: NavDirections ->
                direction.actionId == R.id.action_top_to_detail
            })
        }
    }
}
