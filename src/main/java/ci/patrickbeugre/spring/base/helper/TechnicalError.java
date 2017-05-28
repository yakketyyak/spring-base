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
public class TechnicalError {
	private String			code;
	private String			message;
	private static Status	status	= new Status();

	// private Locale locale;

	@Autowired
	private MessageSource	messageSource;

	/*
	 * public TechnicalError(Locale locale) { this.locale=locale; }
	 */

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
	 * 
	 * public void setLocale(Locale locale) { this.locale = locale; }
	 */

	public Status DB_NOT_CONNECT(String message, Locale locale) {
		status.setCode(StatusCode.TECH_DB_NOT_CONNECT);
		status.setMessage(messageSource.getMessage("StatusMessage.TECH_DB_NOT_CONNECT", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status DB_FAIL(String message, Locale locale) {
		status.setCode(StatusCode.TECH_DB_FAIL);
		status.setMessage(messageSource.getMessage("StatusMessage.TECH_DB_FAIL", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status INTERN_ERROR(String message, Locale locale) {
		status.setCode(StatusCode.TECH_INTERN_ERROR);
		String msg = messageSource.getMessage("StatusMessage.TECH_INTERN_ERROR", new Object[] {}, locale);
		status.setMessage(msg + ": " + message);
		return status;
	}

	public Status DB_PERMISSION_DENIED(String message, Locale locale) {
		status.setCode(StatusCode.TECH_DB_PERMISSION_DENIED);
		status.setMessage(messageSource.getMessage("StatusMessage.TECH_DB_PERMISSION_DENIED", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status DB_QUERY_REFUSED(String message, Locale locale) {
		status.setCode(StatusCode.TECH_DB_QUERY_REFUSED);
		status.setMessage(messageSource.getMessage("StatusMessage.TECH_DB_QUERY_REFUSED", new Object[] {}, locale) + ": " + message);
		return status;
	}

	public Status ERROR(String message, Locale locale) {
		String[] msgTab = message.split(";");
		if (msgTab != null && msgTab.length > 1) {
			status.setCode(msgTab[0]);
			status.setMessage(msgTab[1]);
		} else {
			status.setCode(StatusCode.FUNC_FAIL);
			status.setMessage(message);
		}
		return status;
	}
}
