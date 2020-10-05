package com.oleg.androidmvp.add

import com.oleg.androidmvp.BaseTest
import com.oleg.androidmvp.LocalDatabase
import com.oleg.androidmvp.model.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddMoviePresenterTests : BaseTest() {

    @Mock
    private lateinit var mockActivity: AddMovieContract.ViewInterface

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private lateinit var mockDataSource: LocalDatabase

    @Captor
    private lateinit var movieArgumentCaptor: ArgumentCaptor<Movie>

    private lateinit var addMoviePresenter: AddMoviePresenter

    @Before
    fun setUp() {
        addMoviePresenter =
            AddMoviePresenter(viewInterface = mockActivity, localDataSource = mockDataSource)
    }

    @Test
    fun testAddMovieNoTitle() {
        addMoviePresenter.addMovie("", "", "")

        Mockito.verify(mockActivity).displayError("Movie title cannot be empty")
    }

    @Test
    fun testAddMovieWithTitle() = runBlocking {
        addMoviePresenter.addMovie("The Lion King", "1994-05-07", "/path")
        delay(500) // let the thread finish
        Mockito.verify(mockDataSource.movieDao()).insert(captureArg(movieArgumentCaptor))
        Assert.assertEquals("The Lion King", movieArgumentCaptor.value.title)
        Mockito.verify(mockActivity).returnToMain()
    }
}