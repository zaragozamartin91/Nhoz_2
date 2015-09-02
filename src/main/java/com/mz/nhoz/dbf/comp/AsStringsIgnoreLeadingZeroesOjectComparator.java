package com.mz.nhoz.dbf.comp;

import com.mz.nhoz.util.StringUtils;

public class AsStringsIgnoreLeadingZeroesOjectComparator extends ObjectComparator {
	@Override
	public boolean compare(Object first, Object second) {
		try {
			final String s_first = StringUtils.removeLeadingZeroes(first.toString().trim());
			final String s_second = StringUtils.removeLeadingZeroes(second.toString().trim());

			return s_first.contentEquals(s_second);
		} catch (Exception e) {
			return false;
		}
	}
}//AsStringsIgnoreLeadingZeroesOjectComparator
