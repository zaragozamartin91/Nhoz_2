package com.mz.nhoz.dbf.util;

import java.util.Date;

import com.mz.nhoz.dbf.util.exception.ValueParserException;

import nl.knaw.dans.common.dbflib.BooleanValue;
import nl.knaw.dans.common.dbflib.DateValue;
import nl.knaw.dans.common.dbflib.NumberValue;
import nl.knaw.dans.common.dbflib.StringValue;
import nl.knaw.dans.common.dbflib.Value;

/**
 * Parsea un tipo de dato Java a un Value Dbf.
 * 
 * @author martin.zaragoza
 *
 */
public class ValueSerializer {
	/**
	 * Envuelve un tipo de dato Java en un Value Dbf. Si el dato pasado es
	 * instancia de {@link nl.knaw.dans.common.dbflib.Value}, entonces el metodo
	 * devuelve la misma instancia.
	 * 
	 * @param rawVal
	 *            - dato a parsear.
	 * @return Objeto de tipo Value que envuelve a rawVal. Si rawVal es
	 *         instancia de {@link nl.knaw.dans.common.dbflib.Value}, entonces
	 *         el metodo devuelve la misma instancia.
	 * @throws ValueParserException
	 */
	public Value parse(Object rawVal) throws ValueParserException {
		try {
			Class<? extends Object> class__ = rawVal.getClass();
			Class<?> superclass = class__.getSuperclass();

			if (superclass.equals(Value.class)) {
				return (Value) rawVal;
			}

			if (superclass.equals(Number.class)) {
				return new NumberValue((Number) rawVal);
			}

			if (class__.equals(String.class)) {
				return new StringValue((String) rawVal);
			}

			if (class__.equals(Date.class)) {
				return new DateValue((Date) rawVal);
			}

			if (class__.equals(Boolean.class)) {
				return new BooleanValue((Boolean) rawVal);
			}
		} catch (Exception e) {
			throw new ValueParserException(e);
		}

		throw new ValueParserException("Imposible parsear " + rawVal.toString());
	}
}// ValueParser
