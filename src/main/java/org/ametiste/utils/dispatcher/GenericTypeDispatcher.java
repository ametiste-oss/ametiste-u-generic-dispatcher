package org.ametiste.utils.dispatcher;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Extracts parameterized types and provides client code with only objects supporting defined type
 * Parameterized with type of object under dispatching. Object should be parameterized.
 * Created by ametiste on 10/22/15.
 */
public class GenericTypeDispatcher<T> {

    private Map<Class<?>, List<T>> map;

    /**
     * Accepts collection of target objects for further dispatching. Objects are limited to
     * parameterized ones with only one parameter.
     * @param objects list of target parameterized objects to dispatch. Several objects with same
     *                parameterized type are allowed.
     */
    public GenericTypeDispatcher(List<T> objects) {

        GenericTypeExtractor extractor = new GenericTypeExtractor();

        map = new HashMap<>();
        for(T object: objects) {
            Class<?> type = extractor.extract(object);
            List<T> typedObjects;
            if(map.containsKey(type)) {
                typedObjects = map.get(type);
            }
            else {
                typedObjects  = new ArrayList<>();
            }
            typedObjects.add(object);
            map.put(type, typedObjects);
        }
    }

    /**
     * Defines whether dispatcher contains objects supporting class
     * @param aClass class that objects are required for
     * @return true if dispatcher has object/objects supporting class aClass
     */
    public boolean hasDispatchersFor(Class<?> aClass) {
        return map.containsKey(aClass);
    }

    /**
     * Provides a collection of objects for class
     * @param aClass class that parameterized objects support as a generic type
     * @return collection of objects supporting class aClass
     */
    public List<T> getDispatchersFor(Class<?> aClass) {
        return map.get(aClass);
    }
}
