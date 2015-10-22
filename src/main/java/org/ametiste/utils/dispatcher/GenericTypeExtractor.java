package org.ametiste.utils.dispatcher;

import org.springframework.aop.framework.Advised;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Generic type extractor, extracts parameterized type of target object, simple or advised with aop
 * Passed object has some limitations - it should be parameterized and should be parameterized with one
 * interface only.
 * Created by Daria on 13.06.2014.
 */
public class GenericTypeExtractor {


    /**
     * Extracts parameterized type of target object
     * @param parameterizedObject target object. Should have been parameterized, and have only one
     *                            parameter. Valid: MyObject&lt;Param&gt;
     *                            Not valid: MyObject&lt;Param, AnotherParam&gt;
     *                            Not valid: MyObject
     * @return class of parameterized type
     */
    public Class<?> extract(Object parameterizedObject) {

		if (parameterizedObject.getClass().getGenericInterfaces().length == 1) {
			return this.extractReal(parameterizedObject.getClass());
		} else {

			for (Type typeParam : parameterizedObject.getClass().getGenericInterfaces()) {
				if (typeParam.equals(Advised.class)) {
					Advised advised = (Advised) parameterizedObject;
					Class<?> cls = advised.getTargetSource().getTargetClass();
					return this.extractReal(cls);
				}
			}
		}
        throw new IllegalArgumentException("Cant be extracted, no generic interface or several of them");
    }

	private Class<?> extractReal(Class<?> parameterizedType) {

		for (Type typeParam : parameterizedType.getGenericInterfaces()) {
			ParameterizedType type = (ParameterizedType) typeParam;
				if (type.getActualTypeArguments().length == 1) {
					for (Type typeP : type.getActualTypeArguments()) {
						return Class.class.cast(typeP);
					}
				}
		}
		throw new IllegalArgumentException("Cant be extracted, no generic interface or several of them");
	}
}
