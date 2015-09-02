package com.mz.nhoz.dbf.comp;

public abstract class ObjectComparator {
	public abstract boolean compare(Object first, Object second);

	@Override
	public String toString() {
		return this.getClass().getName().toString();
	}
}
