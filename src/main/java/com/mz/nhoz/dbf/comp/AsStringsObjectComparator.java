package com.mz.nhoz.dbf.comp;

import com.mz.nhoz.util.StringUtils;

public class AsStringsObjectComparator extends ObjectComparator {

	@Override
	public boolean compare(Object first, Object second) {
		try {
			return StringUtils.tryCompareObjectsAsStrings(first, second);
		} catch (Exception e) {
			return false;
		}
	}

}
