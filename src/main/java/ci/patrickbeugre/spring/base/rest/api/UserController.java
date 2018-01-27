
/*
 * Java transformer for entity table user 
 * Created on 23 mai 2017 ( Time 18:24:48 )
 * Generator tool : Telosys Tools Generator ( version 2.1.1 )
 * Copyright 2017 patrickbeugre. All Rights Reserved.
 */

package ci.patrickbeugre.spring.base.rest.api;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ci.patrickbeugre.spring.base.business.UserBusiness;
import ci.patrickbeugre.spring.base.helper.ExceptionUtils;
import ci.patrickbeugre.spring.base.helper.FunctionalError;
import ci.patrickbeugre.spring.base.helper.StatusCode;
import ci.patrickbeugre.spring.base.helper.StatusMessage;
import ci.patrickbeugre.spring.base.helper.TechnicalError;
import ci.patrickbeugre.spring.base.helper.contrat.Request;
import ci.patrickbeugre.spring.base.helper.contrat.Response;


/**
Controller for table "user"
 * 
 * @author patrickbeugre
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping(value="/user")
public class UserController {

	private Response response;

	@Autowired
	private UserBusiness userBusiness;


	@Autowired
	private FunctionalError functionalError;
	
	@Autowired
	private TechnicalError technicalError;

	@Autowired
	private ExceptionUtils			exceptionUtils;

	private Logger slf4jLogger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private HttpServletRequest requestBasic;
	
	public Response validateObject(Request request, Locale locale) throws Exception {
        boolean ostate = true;
        response.setStatus(functionalError.REQUEST_FAIL("", locale));
        if (request == null) {
            response.setHasError(ostate);
            response.setStatus(functionalError.REQUEST_FAIL("la requete est vide", locale));
            return response;
        }
        if (request.getDataUser() == null) {
            response.setHasError(ostate);
            response.setStatus(functionalError.REQUEST_FAIL("", locale));
            return response;
        }
        
        ostate = false;
        response.setHasError(ostate);
        return response;
    }

    public Response validateList(Request request, Locale locale) throws Exception {
        boolean ostate = true;
        response.setStatus(functionalError.REQUEST_FAIL("", locale));
        if (request == null) {
            response.setHasError(ostate);
            response.setStatus(functionalError.REQUEST_FAIL("la requete est vide", locale));
            return response;
        }
        if (request.getDatasUser() == null) {
            response.setHasError(ostate);
            response.setStatus(functionalError.REQUEST_FAIL("", locale));
            return response;
        }
        
        if (request.getDatasUser().isEmpty()) {
            response.setHasError(ostate);
            response.setStatus(functionalError.REQUEST_FAIL("la liste fournie est vide", locale));
            return response;
        }
        
        ostate = false;
        response.setHasError(ostate);
        return response;
    }

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response create(@RequestBody Request request) {
    	slf4jLogger.info("start method create");
        response = new Response();
        
        String languageID = (String)requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale(languageID, "");

        try {
									                                                
        	response = validateList(request, locale);
        	if(!response.isHasError()){
               response = userBusiness.create(request, locale);
        	}else{
        	   slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
        	   return response;
        	}
        	
        	if(!response.isHasError()){
				response.setStatus(functionalError.SUCCESS("", locale));
        	    slf4jLogger.info("end method create");
          	    slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);  
            }else{
             	slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
            }
 
        } catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response update(@RequestBody Request request) {
    	slf4jLogger.info("start method update");
        response = new Response();

        String languageID = (String)requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale(languageID, "");
        
		try {
									                                                
        	response = validateList(request, locale);
        	if(!response.isHasError()){
               response = userBusiness.update(request, locale);
        	}else{
        	   slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
        	   return response;
        	}
        	
        	if(!response.isHasError()){
				response.setStatus(functionalError.SUCCESS("", locale));
        	  	slf4jLogger.info("end method update");
          	  	slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);  
            }else{
              	slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
            }
 
        } catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response delete(@RequestBody Request request) {
    	slf4jLogger.info("start method delete");
        response = new Response();

        String languageID = (String)requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale(languageID, "");

        try {
									                                                
        	response = validateList(request, locale);
        	if(!response.isHasError()){
               response = userBusiness.delete(request, locale);
        	}else{
        	   slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
        	   return response;
        	}
        	
        	if(!response.isHasError()){
				response.setStatus(functionalError.SUCCESS("", locale));
        	  	slf4jLogger.info("end method delete");
          	  	slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);  
            }else{
              slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
            }
 
        } catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public Response getByCriteria(@RequestBody Request request) {
    	slf4jLogger.info("start method getByCriteria");
        response = new Response();

        String languageID = (String)requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale(languageID, "");

        try {
									                                                
        	response = validateObject(request, locale);
        	if(!response.isHasError()){
               response = userBusiness.getByCriteria(request, locale);
        	}else{
        	   slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
        	   return response;
        	}
        	
        	if(!response.isHasError()){
        	  	slf4jLogger.info("end method getByCriteria");
          	  	slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);  
            }else{
              	slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
            }
 
        } catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
        return response;
    }
	@RequestMapping(value="/login",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response login(@RequestBody Request request) {
    	slf4jLogger.info("start method login");
        response = new Response();

        String languageID = (String)requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale locale = new Locale(languageID, "");

        try {
									                                                
        	response = validateObject(request, locale);
        	if(!response.isHasError()){
               response = userBusiness.login(request, locale);
        	}else{
        	   slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
        	   return response;
        	}
        	
        	if(!response.isHasError()){
        	  	slf4jLogger.info("end method getByCriteria");
          	  	slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);  
            }else{
              	slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
            }
 
        } catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
        return response;
    }
	
	@RequestMapping(value="/resetPassword",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response resetPassword(@RequestBody Request request) {
    	slf4jLogger.info("start method resetPassword");
        response = new Response();
        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
    	Locale locale = new Locale(languageID, "");
        try {
        	response=validateObject(request,locale);
        	if(!response.isHasError()){
               response = userBusiness.resetPassword(request,locale);
        	}else{
        	   slf4jLogger.info("Erreur| code: {} -  message: {}",response.getStatus().getCode(),response.getStatus().getMessage());
        	   return response;
        	}
        	
        	if(!response.isHasError()){
        	  slf4jLogger.info("end method resetPassword");
          	  slf4jLogger.info("code: {} -  message: {}",StatusCode.SUCCESS,StatusMessage.SUCCESS);  
            }else{
              slf4jLogger.info("Erreur| code: {} -  message: {}",response.getStatus().getCode(),response.getStatus().getMessage());
            }
 
        } catch (CannotCreateTransactionException e) {
    		exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
    	} catch (TransactionSystemException e) {
    		exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
    	} catch (RuntimeException e) {
    		exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
    	} catch (Exception e) {
    		exceptionUtils.EXCEPTION(response, locale, e);
    	}
        return response;
    }
    @RequestMapping(value="/forgotPassword",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response forgotPassword(@RequestBody Request request) {
    	slf4jLogger.info("start method forgotPassword");
        response = new Response();
        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
    	Locale locale = new Locale(languageID, "");
        try {
        	response=validateObject(request,locale);
        	if(!response.isHasError()){
               response = userBusiness.forgotPassword(request,locale);
        	}else{
        	   slf4jLogger.info("Erreur| code: {} -  message: {}",response.getStatus().getCode(),response.getStatus().getMessage());
        	   return response;
        	}
        	
        	if(!response.isHasError()){
        	  slf4jLogger.info("end method forgotPassword");
          	  slf4jLogger.info("code: {} -  message: {}",StatusCode.SUCCESS,StatusMessage.SUCCESS);  
            }else{
              slf4jLogger.info("Erreur| code: {} -  message: {}",response.getStatus().getCode(),response.getStatus().getMessage());
            }
 
        } catch (CannotCreateTransactionException e) {
    		exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
    	} catch (TransactionSystemException e) {
    		exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
    	} catch (RuntimeException e) {
    		exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
    	} catch (Exception e) {
    		exceptionUtils.EXCEPTION(response, locale, e);
    	}
        return response;
    }
    
    @RequestMapping(value="/forgotPasswordValidation",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response forgotPasswordValidation(@RequestBody Request request) {
    	slf4jLogger.info("start method forgotPasswordValidation");
        response = new Response();
        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
    	Locale locale = new Locale(languageID, "");
        try {
        	response=validateObject(request,locale);
        	if(!response.isHasError()){
               response = userBusiness.forgotPasswordValidation(request,locale);
        	}else{
        	   slf4jLogger.info("Erreur| code: {} -  message: {}",response.getStatus().getCode(),response.getStatus().getMessage());
        	   return response;
        	}
        	
        	if(!response.isHasError()){
        	  slf4jLogger.info("end method forgotPasswordValidation");
          	  slf4jLogger.info("code: {} -  message: {}",StatusCode.SUCCESS,StatusMessage.SUCCESS);  
            }else{
              slf4jLogger.info("Erreur| code: {} -  message: {}",response.getStatus().getCode(),response.getStatus().getMessage());
            }
 
        } catch (CannotCreateTransactionException e) {
    		exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
    	} catch (TransactionSystemException e) {
    		exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
    	} catch (RuntimeException e) {
    		exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
    	} catch (Exception e) {
    		exceptionUtils.EXCEPTION(response, locale, e);
    	}
        return response;
    }
    
    @RequestMapping(value="/ok",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response ok(@RequestBody Request request) {
    	slf4jLogger.info("start method ok");
        response = new Response();
        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
    	Locale locale = new Locale(languageID, "");
        try {
        	response=validateObject(request,locale);
        	if(!response.isHasError()){
               response = userBusiness.ok(request,locale);
        	}else{
        	   slf4jLogger.info("Erreur| code: {} -  message: {}",response.getStatus().getCode(),response.getStatus().getMessage());
        	   return response;
        	}
        	
        	if(!response.isHasError()){
        	  slf4jLogger.info("end method ok");
          	  slf4jLogger.info("code: {} -  message: {}",StatusCode.SUCCESS,StatusMessage.SUCCESS);  
            }else{
              slf4jLogger.info("Erreur| code: {} -  message: {}",response.getStatus().getCode(),response.getStatus().getMessage());
            }
 
        } catch (CannotCreateTransactionException e) {
    		exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
    	} catch (TransactionSystemException e) {
    		exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
    	} catch (RuntimeException e) {
    		exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
    	} catch (Exception e) {
    		exceptionUtils.EXCEPTION(response, locale, e);
    	}
        return response;
    }
    
    
}
