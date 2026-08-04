package cz.kutner.comicsdb.abstracts

import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.ui.components.ViewState
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

private data class TestItem(val id: Int) : Item

private class TestPagedViewModel : AbstractPagedViewModel<TestItem>() {
    val requestedOffsets = mutableListOf<Int>()
    var gate: CompletableDeferred<Unit>? = null
    var shouldFail = false

    override suspend fun getJob(): List<TestItem> {
        requestedOffsets += start * count
        gate?.await()
        if (shouldFail) throw RuntimeException("síť nedostupná")
        return List(count) { TestItem(start * count + it) }
    }
}

class AbstractPagedViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun contentOf(viewModel: TestPagedViewModel): List<TestItem> =
        (viewModel.state.value as ViewState.Content).data

    // First page must render as Content — the list screens have no other data source.
    @Test
    fun `first load requests offset zero and shows content`() = runTest(dispatcher) {
        val viewModel = TestPagedViewModel()
        viewModel.loadData()
        advanceUntilIdle()

        assertEquals(listOf(0), viewModel.requestedOffsets)
        assertEquals(20, contentOf(viewModel).size)
    }

    // Infinite scroll relies on offset = page * count; a wrong offset silently
    // duplicates or skips rows, which no UI state would reveal.
    @Test
    fun `next load appends following page`() = runTest(dispatcher) {
        val viewModel = TestPagedViewModel()
        viewModel.loadData()
        advanceUntilIdle()
        viewModel.loadData()
        advanceUntilIdle()

        assertEquals(listOf(0, 20), viewModel.requestedOffsets)
        val items = contentOf(viewModel)
        assertEquals(40, items.size)
        assertEquals(List(40) { it }, items.map { it.id })
    }

    // Scroll events fire repeatedly near the list end; without the guard the
    // same page would be fetched and appended twice.
    @Test
    fun `load while previous load in flight is ignored`() = runTest(dispatcher) {
        val viewModel = TestPagedViewModel()
        val gate = CompletableDeferred<Unit>()
        viewModel.gate = gate

        viewModel.loadData()
        runCurrent()
        viewModel.loadData()
        gate.complete(Unit)
        advanceUntilIdle()

        assertEquals(1, viewModel.requestedOffsets.size)
        assertEquals(20, contentOf(viewModel).size)
    }

    // With nothing on screen yet, failure must surface the retry UI.
    @Test
    fun `failure without content shows error state`() = runTest(dispatcher) {
        val viewModel = TestPagedViewModel()
        viewModel.shouldFail = true
        viewModel.loadData()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is ViewState.Error)
        assertEquals("síť nedostupná", (state as ViewState.Error).message)
    }

    // A failed page load must never wipe the list the user is reading;
    // it surfaces via pageLoadFailed (snackbar) instead.
    @Test
    fun `failure with content keeps list and flags page load failure`() = runTest(dispatcher) {
        val viewModel = TestPagedViewModel()
        viewModel.loadData()
        advanceUntilIdle()

        viewModel.shouldFail = true
        viewModel.loadData()
        advanceUntilIdle()

        assertEquals(20, contentOf(viewModel).size)
        assertTrue(viewModel.pageLoadFailed.value)

        // Retry succeeds: flag clears and the page is finally appended.
        viewModel.shouldFail = false
        viewModel.loadData()
        advanceUntilIdle()

        assertFalse(viewModel.pageLoadFailed.value)
        assertEquals(40, contentOf(viewModel).size)
    }

    // Changing filter or search restarts the list from the first page —
    // otherwise the new query would continue from the old page counter.
    @Test
    fun `loadNewData restarts from first page`() = runTest(dispatcher) {
        val viewModel = TestPagedViewModel()
        viewModel.loadData()
        advanceUntilIdle()
        viewModel.loadData()
        advanceUntilIdle()

        viewModel.loadNewData()
        advanceUntilIdle()

        assertEquals(listOf(0, 20, 0), viewModel.requestedOffsets)
        assertEquals(20, contentOf(viewModel).size)
    }
}
