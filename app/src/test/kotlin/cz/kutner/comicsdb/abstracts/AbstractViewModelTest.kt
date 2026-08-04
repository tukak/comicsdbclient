package cz.kutner.comicsdb.abstracts

import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.ui.components.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

private data class TestDetail(val id: Int) : Item

private class TestDetailViewModel : AbstractViewModel<TestDetail>() {
    var shouldFail = false

    override suspend fun getJob(id: Int): TestDetail {
        if (shouldFail) throw RuntimeException("detail nenalezen")
        return TestDetail(id)
    }
}

class AbstractViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Detail screens render purely from this state — success must carry the loaded item.
    @Test
    fun `successful load shows content for requested id`() = runTest(dispatcher) {
        val viewModel = TestDetailViewModel()
        viewModel.loadData(42)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is ViewState.Content)
        assertEquals(42, (state as ViewState.Content).data.id)
    }

    // Failure must produce the Error state so the retry UI appears instead of
    // an endless loading spinner.
    @Test
    fun `failed load shows error with message`() = runTest(dispatcher) {
        val viewModel = TestDetailViewModel()
        viewModel.shouldFail = true
        viewModel.loadData(42)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is ViewState.Error)
        assertEquals("detail nenalezen", (state as ViewState.Error).message)
    }
}
