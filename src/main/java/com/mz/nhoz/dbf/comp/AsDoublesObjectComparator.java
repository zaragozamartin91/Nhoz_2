package com.mz.nhoz.dbf.comp;

import com.mz.nhoz.util.NumberUtils;

public class AsDoublesObjectComparator implements ObjectComparator {

	@Override
	public boolean compare(Object first, Object second) {
		try {
			return NumberUtils.tryCompareObjectsAsDoubles(first, second);
		} catch (Exception e) {
			return false;
		}
	}

}
