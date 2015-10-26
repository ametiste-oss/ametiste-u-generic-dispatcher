package org.ametiste.utils.dispatcher;

import org.ametiste.utils.dispatcher.stubs.StubGenericStringClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by ametiste on 10/26/15.
 */
public class GenericTypeExtractorTest {

    private GenericTypeExtractor extractor;


    @Before
    public void setUp() throws Exception {
        extractor = new GenericTypeExtractor();
    }

    @Test
    public void testExtract() throws Exception {
        Object o = new StubGenericStringClass();
        Class<?> extracted = extractor.extract(o);
        assertEquals(String.class, extracted);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExtractNoGeneric() throws Exception {
        Object o = new Object();
        extractor.extract(o);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExtractMultipleInterfaces() throws Exception {
        Object o = new ArrayList<>();
        extractor.extract(o);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExtractMultipleGeneric() throws Exception {
        Object o = new HashMap<String, String>();
        extractor.extract(o);
    }
}