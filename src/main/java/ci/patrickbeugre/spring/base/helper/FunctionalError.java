/* 
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates 
 * and open the template in the editor. 
 */
package ci.patrickbeugre.spring.base.helper;

import java.util.Locale;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * 
 * @author patrickbeugre
 */
@XmlRootElement
@Component
public class FunctionalError {
	private String			code;
	private String			message;
	private static Status	status	= new Status();

	// private Locale locale;

	@Autowired
	private MessageSource	messageSource;

	/*
	 * public FunctionalError(Locale locale) { this.locale=locale; }
	 */

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * public Locale getLocale() { return locale; }
	 * 
	 * public void setLocale(Locale locale) { this.locale = locale; }
	 */

	public Status SUCCESS(String message, Locale locale) {
		status.setCode(StatusCode.SUCCESS);
		status.setMessage(messageSource.getMessage("StatusMessage.SUCCESS", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status AUTH_FAIL(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_AUTH_FAIL);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_AUTH_FAIL", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status DATA_NOT_EXIST(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_DATA_NOT_EXIST);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_DATA_NOT_EXIST", new Object[] {}, locale) + ": " + message);
		return status;
	}
	
	// METHOD FOR DATA TOO LONG 
	public Status DATA_TOO_LONG(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_DATA_TOO_LONG);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_DATA_TOO_LONG", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status DATA_EMPTY(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_DATA_EMPTY);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_DATA_EMPTY", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status DATA_EXIST(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_DATA_EXIST);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_DATA_EXIST", new Object[] {}, locale) + ": " + message);
		return status;
	}
	
	public Status INVALID_EMAIL(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_INVALID_EMAIL);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_INVALID_EMAIL", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status INVALID_CODE_LANGUAGE(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_INVALID_CODE_LANGUAGE);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_INVALID_CODE_LANGUAGE", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status FIELD_EMPTY(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_FIELD_EMPTY);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_FIELD_EMPTY", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status USER_ALREADY_CONNECTED(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_USER_ALREADY_CONNECTED);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_USER_ALREADY_CONNECTED", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status REQUEST_FAIL(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_REQUEST_FAIL);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_REQUEST_FAIL", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status SAVE_FAIL(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_SAVE_FAIL);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_SAVE_FAIL", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status TYPE_NOT_CORRECT(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_TYPE_NOT_CORRECT);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_TYPE_NOT_CORRECT", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status DATE_FORMAT_NOT_CORRECT(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_DATE_FORMAT_NOT_CORRECT);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_DATE_FORMAT_NOT_CORRECT", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status INVALID_DATE_PERIOD(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_INVALID_DATE_PERIOD);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_INVALID_DATE_PERIOD", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status INVALID_FORMAT(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_INVALID_FORMAT);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_INVALID_FORMAT", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status INVALID_ENTITY_NAME(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_INVALID_ENTITY_NAME);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_INVALID_ENTITY_NAME", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status IMEI_INCORRECT(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_IMEI_INCORRECT);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_IMEI_INCORRECT", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status FUNC_ONE_MUST_BE_FIELD_EMPTY(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_ONE_MUST_BE_FIELD_EMPTY);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_ONE_MUST_BE_FIELD_EMPTY", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status FUNC_PERCENT_VALUE(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_MUST_BE_INFERIOR_TO_100);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_MUST_BE_INFERIOR_TO_100", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status FILE_GENERATION_ERROR(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_FILE_GENERATION_ERROR);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_FILE_GENERATION_ERROR", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status LOGIN_FAIL(Locale locale) {
		status.setCode(StatusCode.FUNC_LOGIN_FAIL);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_LOGIN_FAIL", new Object[] {}, locale));
		return status;
	}

	public Status DISALLOWED_OPERATION(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_FILE_GENERATION_ERROR);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_DISALLOWED_OPERATION", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status DATA_NOT_DELETABLE(String message, Locale locale) {
		status.setCode(StatusCode.FUNC_DATA_NOT_DELETABLE);
		status.setMessage(messageSource.getMessage("StatusMessage.FUNC_DATA_NOT_DELETABLE", new Object[] {}, locale) + ": " + message);
		return status;
	}
}