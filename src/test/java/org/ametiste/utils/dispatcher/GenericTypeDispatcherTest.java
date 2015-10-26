package org.ametiste.utils.dispatcher;

import org.ametiste.utils.dispatcher.stubs.StubGenericIntClass;
import org.ametiste.utils.dispatcher.stubs.StubGenericStringClass;
import org.ametiste.utils.dispatcher.stubs.StubGenericInterface;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ametiste on 10/26/15.
 */
public class GenericTypeDispatcherTest {

    private GenericTypeDispatcher<StubGenericInterface> dispatcher;

    private StubGenericStringClass object1 = new StubGenericStringClass();
    private StubGenericIntClass object2 = new StubGenericIntClass();
    private StubGenericIntClass object3 = new StubGenericIntClass();

    @Before
    public void setUp() throws Exception {
        dispatcher = new GenericTypeDispatcher<>(Arrays.asList(object1, object2, object3));

    }

    @Test
    public void testHasDispatchersFor() throws Exception {
        assertTrue(dispatcher.hasDispatchersFor(String.class));
    }

    @Test
    public void testHasNotDispatchersFor() throws Exception {
        assertFalse(dispatcher.hasDispatchersFor(Long.class));
    }

    @Test
    public void testGetDispatchersFor() throws Exception {
        List<StubGenericInterface> dispatchers = dispatcher.getDispatchersFor(String.class);
        assertEquals(1, dispatchers.size());
        assertEquals(object1, dispatchers.get(0));
    }

    @Test
    public void testGetDispatchersForMultiple() throws Exception {
        List<StubGenericInterface> dispatchers = dispatcher.getDispatchersFor(Integer.class);
        assertEquals(2, dispatchers.size());
        assertArrayEquals(Arrays.asList(object2, object3).toArray(), dispatchers.toArray());
    }
}