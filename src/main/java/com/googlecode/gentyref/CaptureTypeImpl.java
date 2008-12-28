package com.googlecode.gentyref;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;

class CaptureTypeImpl implements CaptureType {
	private final WildcardType wildcard;
	private final TypeVariable<?> variable;
	private final Type[] lowerBounds;
	private Type[] upperBounds;
	
	/**
	 * Creates an uninitialized CaptureTypeImpl. Before using this type, {@link #init(VarMap)} must be called.
	 * @param wildcard The wildcard this is a capture of
	 * @param variable The type variable where the wildcard is a parameter for.
	 */
	public CaptureTypeImpl(WildcardType wildcard, TypeVariable<?> variable) {
		this.wildcard = wildcard;
		this.variable = variable;
		this.lowerBounds = wildcard.getLowerBounds();
	}
	
	void init(VarMap varMap) {
		ArrayList<Type> upperBoundsList = new ArrayList<Type>();
		upperBoundsList.addAll(Arrays.asList(varMap.map(variable.getBounds())));
		upperBoundsList.addAll(Arrays.asList(wildcard.getUpperBounds()));
		upperBounds = new Type[upperBoundsList.size()]; 
		upperBoundsList.toArray(upperBounds);
	}

	/*
	 * @see com.googlecode.gentyref.CaptureType#getLowerBounds()
	 */
	public Type[] getLowerBounds() {
		return lowerBounds.clone();
	}

	/*
	 * @see com.googlecode.gentyref.CaptureType#getUpperBounds()
	 */
	public Type[] getUpperBounds() {
		assert upperBounds != null;
		return upperBounds.clone();
	}
}