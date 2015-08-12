package com.mz.nhoz;

import com.mz.nhoz.exception.ValueParserException;

import nl.knaw.dans.common.dbflib.NumberValue;
import nl.knaw.dans.common.dbflib.StringValue;
import nl.knaw.dans.common.dbflib.Value;

public class ValueParser {
	public Value parse(Object rawVal) throws ValueParserException {
		try {
			Class<? extends Object> class__ = rawVal.getClass();
			Class<?> superclass = class__.getSuperclass();

			if (superclass.equals(Number.class)) {
				return new NumberValue((Number) rawVal);
			}
			
			if(class__.equals(String.class)){
				return new StringValue((String) rawVal);
			}
		} catch (Exception e) {
			throw new ValueParserException(e);
		}

		throw new ValueParserException("Imposible parsear " + rawVal.toString());
	}
}// ValueParser
