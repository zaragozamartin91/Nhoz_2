package com.mz.nhoz.dbf.comp;

import com.mz.nhoz.util.NumberUtils;

public class AsIntegersObjectComparator extends ObjectComparator {
	@Override
	public boolean compare(Object first, Object second) {
		try {
			return NumberUtils.tryCompareObjectsAsIntegers(first, second);
		} catch (Exception e) {
			return false;
		}
	}
}
