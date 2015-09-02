package com.mz.nhoz.dbf;

import java.util.HashMap;
import java.util.Map;

import com.mz.nhoz.dbf.comp.AsDoublesObjectComparator;
import com.mz.nhoz.dbf.comp.AsIntegersObjectComparator;
import com.mz.nhoz.dbf.comp.AsStringsIgnoreLeadingZeroesOjectComparator;
import com.mz.nhoz.dbf.comp.AsStringsObjectComparator;
import com.mz.nhoz.dbf.comp.StandardObjectComparator;

public class ValueEqualsLenientRecordPredicateBuilder {
	private Map<String, Object> keyValues = new HashMap<String, Object>();

	public ValueEqualsLenientRecordPredicateBuilder(Map<String, Object> keyValues) {
		super();
		this.keyValues = keyValues;
	}

	public ValueEqualsLenientRecordPredicateBuilder(String key, Object value) {
		keyValues.put(key, value);
	}

	public ValueEqualsLenientRecordPredicate buildStandard() {
		ValueEqualsLenientRecordPredicate predicate = new ValueEqualsLenientRecordPredicate(keyValues);
		predicate.addComparator(new StandardObjectComparator());
		predicate.addComparator(new AsDoublesObjectComparator());
		predicate.addComparator(new AsIntegersObjectComparator());
		predicate.addComparator(new AsStringsObjectComparator());
		predicate.addComparator(new AsStringsIgnoreLeadingZeroesOjectComparator());

		return predicate;
	}

	public ValueEqualsLenientRecordPredicate buildEmpty() {
		ValueEqualsLenientRecordPredicate predicate = new ValueEqualsLenientRecordPredicate(keyValues);

		return predicate;
	}
}
