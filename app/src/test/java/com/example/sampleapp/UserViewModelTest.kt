package com.example.sampleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    @ExperimentalCoroutinesApi
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun register_whenSuccess() {
        val repository = mockk<UserRepository>()
        val userObserver = mockk<Observer<User>>(relaxed = true)
        val stateObserver = mockk<Observer<UserState>>(relaxed = true)
        val viewModel = UserViewModel(repository)

        viewModel.user.observeForever(userObserver)
        viewModel.state.observeForever(stateObserver)

        coEvery { repository.register() } returns User(name = "test-user")

        viewModel.register()

        verifyOrder {
            stateObserver.onChanged(match { result -> result == UserState.REGISTERING })
            userObserver.onChanged(match { result -> result.name == "test-user" })
            stateObserver.onChanged(match { result -> result == UserState.REGISTERED })
        }

        assertThat(viewModel.user.value).isEqualTo(User("test-user"))
        assertThat(viewModel.state.value).isEqualTo(UserState.REGISTERED)
    }

    @Test
    fun register_whenFailure() {
        val repository = mockk<UserRepository>()
        val userObserver = mockk<Observer<User>>(relaxed = true)
        val stateObserver = mockk<Observer<UserState>>(relaxed = true)
        val viewModel = UserViewModel(repository)

        viewModel.user.observeForever(userObserver)
        viewModel.state.observeForever(stateObserver)

        coEvery { repository.register() } throws RuntimeException("failed to register")

        viewModel.register()

        verifyOrder {
            stateObserver.onChanged(match { result -> result == UserState.REGISTERING })
            stateObserver.onChanged(match { result -> result == UserState.UNREGISTERED })
        }

        assertThat(viewModel.user.value).isNull()
        assertThat(viewModel.state.value).isEqualTo(UserState.UNREGISTERED)
    }

}