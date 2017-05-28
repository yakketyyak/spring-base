package ci.patrickbeugre.spring.base.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParamsUtils {
	
	
	
	@Value("${smtp.mail.host}")
	private String smtpHost;
	
	@Value("${smtp.mail.port}")
	private Integer smtpPort;

	@Value("${smtp.mail.username}")
	private String smtpUsername;

	@Value("${smtp.mail.password}")
	private String smtpPassword;
	
	@Value("${forgot.password.code.validity}")
	private Integer forgotPasswordCodeValidity;
	

	public String getSmtpHost() {
		return smtpHost;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public Integer getForgotPasswordCodeValidity() {
		return forgotPasswordCodeValidity;
	}

	
	
}
