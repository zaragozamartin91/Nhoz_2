package com.mz.nhoz.dbf.comp;

public class StandardObjectComparator extends ObjectComparator {
	@Override
	public boolean compare(Object first, Object second) {
		try {
			return first.equals(second);
		} catch (Exception e) {
			return false;
		}
	}
}
