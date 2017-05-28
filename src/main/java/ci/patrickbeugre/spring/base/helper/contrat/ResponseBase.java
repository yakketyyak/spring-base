/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.patrickbeugre.spring.base.helper.contrat;

import ci.patrickbeugre.spring.base.helper.Status;

/**
 *
 * @author patrickbeugre
 */
public class ResponseBase {

	protected Status	status;
	protected boolean	hasError;
	protected String	sessionUser;
	protected Long		count;

	public ResponseBase() {
		status = new Status();
	}

	public String getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(String sessionUser) {
		this.sessionUser = sessionUser;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
}
