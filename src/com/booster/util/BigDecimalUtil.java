package com.booster.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.SQLException;

public class BigDecimalUtil {

	public static final String ADD = "+";
	public static final String SUBSTRACT = "-";
	public static final String MULTIPLY = "*";
	public static final String DIVIDE = "/";

	private static final MathContext mathContext = MathContext.DECIMAL128;
	private static final RoundingMode roundingMode = RoundingMode.HALF_UP;


	private static BigDecimal add(BigDecimal number1, BigDecimal number2, int decimalPlaces){
		return number1.add(number2,mathContext).setScale(decimalPlaces, roundingMode);	
	}

	private static BigDecimal subtract(BigDecimal number1, BigDecimal number2, int decimalPlaces){
		return number1.subtract(number2,mathContext).setScale(decimalPlaces, roundingMode);		
	}

	private static BigDecimal multiply(BigDecimal number1, BigDecimal number2, int decimalPlaces){
		return number1.multiply(number2,mathContext).setScale(decimalPlaces, roundingMode);		
	}

	private static BigDecimal divide(BigDecimal number1, BigDecimal number2, int decimalPlaces){
		return number1.divide(number2,mathContext).setScale(decimalPlaces, roundingMode);		
	}

	public static BigDecimal calculate(BigDecimal number1, String operator, BigDecimal number2, int decimalPlaces){

		if(number1 == null){
			number1 = BigDecimal.ZERO;
		}

		if(number2 == null){
			number2 = BigDecimal.ZERO;
		}

		switch (operator){
			case ADD: return add(number1, number2, decimalPlaces);
			case SUBSTRACT: return subtract(number1, number2, decimalPlaces);
			case MULTIPLY: return multiply(number1, number2, decimalPlaces);
			case DIVIDE: return divide(number1, number2, decimalPlaces);
			default:return null;
		}

	}

	public static BigDecimal calculate(BigDecimal number1, String operator, Integer number2, int decimalPlaces){ 
		BigDecimal number2Converted = new BigDecimal(Integer.toString(number2));
		return calculate(number1, operator, number2Converted, decimalPlaces);
	}

	public static BigDecimal calculate(BigDecimal number1, String operator, BigDecimal number2){
		int decimalPlaces = 2;
		return calculate(number1,operator,number2,decimalPlaces);
	}

	public static BigDecimal calculate(BigDecimal number1, String operator, Integer number2){
		int decimalPlaces = 2;
		return calculate(number1,operator,number2,decimalPlaces);
	}
}
