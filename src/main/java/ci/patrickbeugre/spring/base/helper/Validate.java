/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.patrickbeugre.spring.base.helper;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author patrickbeugre
 */
public class Validate {

	private String			field;
	private boolean			good;
	private static Validate	validate	= new Validate();
	private static Logger	slf4jLogger	= LoggerFactory.getLogger(Validate.class);

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public static Validate getValidate() {
		return validate;
	}

	public static void setValidate(Validate validate) {
		Validate.validate = validate;
	}

	// permet de controler les valeurs envoyées
	public static Validate Value(Object item, Object value, boolean strict) {
		validate.setGood(true);
		try {
			if (value == null) {
				validate.setGood(false);
				validate.setField(item.toString());
				return validate;
			}
			if (value instanceof String) {
				if (value.toString().isEmpty()) {
					validate.setGood(false);
					validate.setField(item.toString());
				}
			} else if (value instanceof Integer) {
				if (strict) {
					if (value.toString().isEmpty() || Integer.parseInt(value.toString()) < 0) {
						validate.setGood(false);
						validate.setField(item.toString());
					}
				} else {
					if (value == null || value.toString().isEmpty() || Integer.parseInt(value.toString()) <= 0) {
						validate.setGood(false);
						validate.setField(item.toString());
					}
				}
			} else {
				if (value == null || value.toString().isEmpty()) {
					validate.setGood(false);
					validate.setField(item.toString());
				}
			}
		} catch (Exception e) {
			slf4jLogger.warn(e.getMessage());
		}
		return validate;
	}

	// permet de controler les valeurs envoyées
	public static Validate Value(Object item, Object value) {
		validate.setGood(true);
		try {
			if (value == null) {
				validate.setGood(false);
				validate.setField(item.toString());
				return validate;
			}
			if (value instanceof String) {
				if (value.toString().isEmpty()) {
					validate.setGood(false);
					validate.setField(item.toString());
				}
			} else if (value instanceof Integer) {
				if (value.toString().isEmpty() || Integer.parseInt(value.toString()) <= 0) {
					validate.setGood(false);
					System.out.println("object4" + value);
					validate.setField(item.toString());
				}
			} else {
				if (value == null || value.toString().isEmpty()) {
					validate.setGood(false);
					validate.setField(item.toString());
				}
			}

		} catch (Exception e) {
			slf4jLogger.warn(e.getMessage());
		}
		return validate;
	}

	public static Validate TypeValue(Object item, Object value, boolean strict) {

		validate.setGood(true);
		try {
			if (value == null) {
				validate.setGood(false);
				validate.setField(item.toString());
				return validate;
			}
			if (value instanceof String) {
				if (Utilities.isInteger(value.toString())) {
					validate.setGood(false);
					validate.setField(item.toString());
				}
			} else if (value instanceof Integer) {
				if (Utilities.isString(((Integer) value))) {
					validate.setGood(false);
					validate.setField(item.toString());
				}
			}
		} catch (Exception e) {
			slf4jLogger.warn(e.getMessage());
		}
		return validate;
	}

	public static Validate RequiredValue(Map<String, Object> listAverifier) {
		for (Map.Entry<String, Object> item : listAverifier.entrySet()) {
			validate.setGood(item.getValue() == null ? false : !item.getValue().toString().isEmpty());
			if (!validate.isGood()) {
				validate.setField(item.getKey());
				break;
			}
		}

		return validate;
	}

	public static Validate CorrectTypedValue(Map<String, Object> listAverifier) {
		for (Map.Entry<String, Object> item : listAverifier.entrySet()) {
			if (item.getValue() instanceof String) {
				if (Utilities.isInteger(item.getValue().toString())) {
					validate.setGood(false);
					validate.setField(item.toString());
					break;
				}
			} else if (item.getValue() instanceof Integer) {
				if (Utilities.isString(((Integer) item.getValue()))) {
					validate.setGood(false);
					validate.setField(item.toString());
					break;
				}
			}
		}
		return validate;
	}

}
