
/*
 * Java transformer for entity table user 
 * Created on 23 mai 2017 ( Time 18:24:48 )
 * Generator tool : Telosys Tools Generator ( version 2.1.1 )
 * Copyright 2017 patrickbeugre. All Rights Reserved.
 */

package ci.patrickbeugre.spring.base.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import ci.patrickbeugre.spring.base.dao.entity.User;
import ci.patrickbeugre.spring.base.dao.repository.UserRepository;
import ci.patrickbeugre.spring.base.helper.ExceptionUtils;
import ci.patrickbeugre.spring.base.helper.FunctionalError;
import ci.patrickbeugre.spring.base.helper.ParamsUtils;
import ci.patrickbeugre.spring.base.helper.SBaseUtils;
import ci.patrickbeugre.spring.base.helper.Status;
import ci.patrickbeugre.spring.base.helper.StatusCode;
import ci.patrickbeugre.spring.base.helper.StatusMessage;
import ci.patrickbeugre.spring.base.helper.TechnicalError;
import ci.patrickbeugre.spring.base.helper.Utilities;
import ci.patrickbeugre.spring.base.helper.Validate;
import ci.patrickbeugre.spring.base.helper.contrat.IBasicBusiness;
import ci.patrickbeugre.spring.base.helper.contrat.Request;
import ci.patrickbeugre.spring.base.helper.contrat.Response;
import ci.patrickbeugre.spring.base.helper.dto.UserDto;
import ci.patrickbeugre.spring.base.helper.dto.transformer.UserTransformer;

/**
BUSINESS for table "user"
 * 
 * @author patrickbeugre
 *
 */
@Component
public class UserBusiness implements IBasicBusiness<Request, Response> {


	private Response response;
	
	private Context context;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FunctionalError functionalError;
	
	@Autowired
	private TechnicalError technicalError;
	
	@Autowired
	private SBaseUtils springBaseUtils;

	@Autowired
	private ExceptionUtils exceptionUtils;
	@Autowired
	private ParamsUtils paramsUtils;
	
	@PersistenceContext
	private EntityManager em;
	
	private Logger slf4jLogger;

	private SimpleDateFormat dateFormat;

	public UserBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		slf4jLogger = LoggerFactory.getLogger(getClass());
	}

	
	/**
	 * create User by using UserDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@SuppressWarnings("unused")
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	@Override
	public Response create(Request request, Locale locale)  {
		slf4jLogger.info("----begin create-----");
		
		response = new Response();
		
		try {
			List<User> items = new ArrayList<User>();
			
			for (UserDto dto : request.getDatasUser()) {
				// Definir les parametres obligatoires
				Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
				fieldsToVerify.put("userName", dto.getUserName());
				fieldsToVerify.put("email", dto.getEmail());
				if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
					response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
					response.setHasError(true);
					return response;
				}

				//vérification de l'adresse email
				if (!SBaseUtils.isValidEmail(dto.getEmail())) {
					response.setStatus(functionalError.INVALID_EMAIL("" + dto.getEmail(), locale));
					response.setHasError(true);
					return response;
				}
				
				//l'email doit être unique en base . 
				User userEmail = null;
				userEmail = userRepository.findByEmail(dto.getEmail(), Boolean.FALSE);
				if (userEmail != null) {
					response.setStatus(functionalError.DATA_EXIST("user -> " + dto.getEmail(), locale));
					response.setHasError(true);
					return response;
				}
				
				// Verify if user to insert do not exist
				//UserName doit être unique en base . 
				User existingEntity = null;
				//TODO: add/replace by the best method
				existingEntity = userRepository.findByUserName( dto.getUserName(), Boolean.FALSE);
				if (existingEntity != null) {
					response.setStatus(functionalError.DATA_EXIST("user -> " + dto.getUserName(), locale));
					response.setHasError(true);
					return response;
				}

				
				User entityToSave = null;
				entityToSave = UserTransformer.INSTANCE.toEntity(dto);
				entityToSave.setIsDeleted(false);
				String password=SBaseUtils.generateAlphanumericCode(8);
				entityToSave.setPassword(SBaseUtils.encrypt(password));
				entityToSave.setCreatedAt(Utilities.getCurrentDate());
				entityToSave.setPwdIsDefaultPwd(Boolean.TRUE);
				entityToSave.setLocked(Boolean.FALSE);
				//set mail to user
				Map<String, String> from=new HashMap<>();
	        	from.put("email", paramsUtils.getSmtpUsername());
	        	from.put("user", "GAGIE");
	        	//recipients
	        	List<Map<String, String>> toRecipients = new ArrayList<Map<String, String>>();
	        	Map<String, String> recipient = new HashMap<String, String>();
	        	recipient = new HashMap<String, String>();
	        	recipient.put("email", dto.getEmail());
	        	recipient.put("user", dto.getUserName());
	        	toRecipients.add(recipient); 
	        	
	        	//subject
	        	context = new Context();
	        	context.setVariable("userName", dto.getUserName());
	        	context.setVariable("password", password);
	        	context.setVariable("date", dateFormat.format(new Date()));
	        	String subject = "Paramètres de connexion !";
	        	String body="";
	        	body+= "";
	        	response = springBaseUtils.sendEmailImproved(from, toRecipients, subject,body,null,context,paramsUtils.getUserCredentials());
				
				items.add(entityToSave);
			}

			if (!items.isEmpty()) {
				List<User> itemsSaved = null;
				// inserer les donnees en base de donnees
				itemsSaved = userRepository.save((Iterable<User>) items);
				if (itemsSaved == null) {
					response.setStatus(functionalError.SAVE_FAIL("user", locale));
					response.setHasError(true);
					return response;
				}
				List<UserDto> itemsDto = new ArrayList<UserDto>();
				for (User entity : itemsSaved) {
					UserDto dto = getFullInfos(entity, locale);
					if (dto == null) continue;
					dto.setPassword(null);
					itemsDto.add(dto);
				}
				response.setItemsUser(itemsDto);
				response.setHasError(false);
			}

			slf4jLogger.info("----end-----");
		} catch (PermissionDeniedDataAccessException e) {
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}
		return response;
	}

	/**
	 * update User by using UserDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@SuppressWarnings("unused")
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	@Override
	public Response update(Request request, Locale locale)  {
		slf4jLogger.info("----begin update-----");
		
		response = new Response();
		
		try {
			List<User> items = new ArrayList<User>();
			
			for (UserDto dto : request.getDatasUser()) {
				// Definir les parametres obligatoires
				Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
				fieldsToVerify.put("id", dto.getId());
				if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
					response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
					response.setHasError(true);
					return response;
				}

				// Verifier si la user existe
				User entityToSave = null;
				entityToSave = userRepository.findById(dto.getId(), false);
				if (entityToSave == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("user -> " + dto.getId(), locale));
					response.setHasError(true);
					return response;
				}

				
				if (dto.getUserName() != null && !dto.getUserName().isEmpty()) {
					User rl = null;
					rl = userRepository.findByUserName(dto.getUserName(),Boolean.FALSE);
					if (rl != null) {
						if (!entityToSave.getId().equals(rl.getId())) {
							response.setStatus(functionalError.DATA_EXIST(dto.getUserName() , locale));
							response.setHasError(Boolean.TRUE);
							return response;
						}
					}
					entityToSave.setUserName(dto.getUserName());
				}
				if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
					entityToSave.setPwdIsDefaultPwd(Boolean.FALSE);
					entityToSave.setPassword(SBaseUtils.encrypt(dto.getPassword()));
				}
				if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
					if (!SBaseUtils.isValidEmail(dto.getEmail())) {
						response.setStatus(functionalError.INVALID_EMAIL("" + dto.getEmail(), locale));
						response.setHasError(true);
						return response;
					}
					User rl = null;
					rl = userRepository.findByEmail(dto.getEmail(),Boolean.FALSE);
					if (rl != null) {
						if (!entityToSave.getId().equals(rl.getId())) {
							response.setStatus(functionalError.DATA_EXIST(dto.getEmail() , locale));
							response.setHasError(Boolean.TRUE);
							return response;
						}
					}
					entityToSave.setEmail(dto.getEmail());
				}
				
				items.add(entityToSave);
			}

			if (!items.isEmpty()) {
				List<User> itemsSaved = null;
				// maj les donnees en base
				itemsSaved = userRepository.save((Iterable<User>) items);
				if (itemsSaved == null) {
					response.setStatus(functionalError.SAVE_FAIL("user", locale));
					response.setHasError(true);
					return response;
				}
				List<UserDto> itemsDto = new ArrayList<UserDto>();
				for (User entity : itemsSaved) {
					UserDto dto = getFullInfos(entity, locale);
					if (dto == null) continue;
					dto.setPassword(null);
					itemsDto.add(dto);
				}
				response.setItemsUser(itemsDto);
				response.setHasError(false);
			}

			slf4jLogger.info("----end-----");
		} catch (PermissionDeniedDataAccessException e) {
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}
		return response;
	}

	/**
	 * delete User by using UserDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@SuppressWarnings("unused")
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	@Override
	public Response delete(Request request, Locale locale)  {
		slf4jLogger.info("----begin delete-----");
		
		response = new Response();
		
		try {
			List<User> items = new ArrayList<User>();
			
			for (UserDto dto : request.getDatasUser()) {
				// Definir les parametres obligatoires
				Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
				fieldsToVerify.put("id", dto.getId());
				if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
					response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
					response.setHasError(true);
					return response;
				}

				// Verifier si la user existe
				User existingEntity = null;
				existingEntity = userRepository.findById(dto.getId(), false);
				if (existingEntity == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("user -> " + dto.getId(), locale));
					response.setHasError(true);
					return response;
				}

				

				existingEntity.setIsDeleted(true);
				items.add(existingEntity);
			}

			if (!items.isEmpty()) {
				// supprimer les donnees en base
				userRepository.save((Iterable<User>) items);

				response.setHasError(false);
			}

			slf4jLogger.info("----end-----");
		} catch (PermissionDeniedDataAccessException e) {
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}
		return response;
	}
	/**
	 * delete User by using UserDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@SuppressWarnings("unused")
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	public Response login(Request request, Locale locale)  {
		slf4jLogger.info("----begin login-----");
		
		response = new Response();
		
		try {
			
				List<User> items = new ArrayList<>();
				UserDto dto = request.getDataUser();
				// Definir les parametres obligatoirs
				Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
				fieldsToVerify.put("username", dto.getUserName());
				fieldsToVerify.put("password", dto.getPassword());
				if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
					response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
					response.setHasError(true);
					return response;
				}

				
					// Verifier si la user existe
					User existingEntity = null;
					existingEntity = userRepository.login(dto.getUserName(),SBaseUtils.encrypt(dto.getPassword()), Boolean.FALSE, Boolean.FALSE);
					if (existingEntity == null) {
						response.setStatus(functionalError.LOGIN_FAIL(locale));
						response.setHasError(true);
						return response;
					}
				
				existingEntity.setLastLogin(new Date());;
				items.add(existingEntity);


			if (!items.isEmpty()) {
				List<User> itemsSaved = null;
				// maj les donnees en base
				itemsSaved = userRepository.save((Iterable<User>) items);
				if (itemsSaved == null) {
					response.setStatus(functionalError.SAVE_FAIL("user", locale));
					response.setHasError(true);
					return response;
				}
				List<UserDto> itemsDto = new ArrayList<UserDto>();
				for (User entity : itemsSaved) {
					UserDto dtoUser = getFullInfos(entity, locale);
					if (dtoUser == null) continue;
					dtoUser.setPassword(null);
					itemsDto.add(dtoUser);
				}
				response.setItemsUser(itemsDto);
				response.setHasError(false);
			}

			slf4jLogger.info("----end-----");
		} catch (PermissionDeniedDataAccessException e) {
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}
		return response;
	}
	
	@Transactional( rollbackFor = { RuntimeException.class, Exception.class })
	public Response resetPassword(Request request,Locale locale) {
		// TODO Auto-generated method stub
		slf4jLogger.info("--------start method resetPassword--------");
		response = new Response();
			try {
				UserDto userDto = request.getDataUser();
				// Definir les paramètres obligatoires
				Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
				fieldsToVerify.put("username", userDto.getUserName());
				fieldsToVerify.put("ancien mot de passe", userDto.getPassword());
				fieldsToVerify.put("nouveau mot de passe", userDto.getNewPassword());
				if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
					response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
					response.setHasError(Boolean.TRUE);
					return response;
				}
				User user = null;
				user = userRepository.login(userDto.getUserName(), SBaseUtils.encrypt(userDto.getPassword()),
						Boolean.FALSE,Boolean.FALSE);
				if (user == null) {
					response.setStatus(functionalError.LOGIN_FAIL(locale));
					response.setHasError(Boolean.TRUE);
					return response;
				}

				user.setPassword(SBaseUtils.encrypt(userDto.getNewPassword()));
				user.setPwdIsDefaultPwd(Boolean.FALSE);
				// inserer les données en base de données
				userRepository.save(user);

				// Renseigner les données à retourner dans la reponse
				response.setHasError(Boolean.FALSE);
				response.setStatus(functionalError.SUCCESS("", locale));

			slf4jLogger.info("--------end method resetPassword--------");
		} catch (PermissionDeniedDataAccessException e) {
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}
		return response;
		
	}
	@Transactional( rollbackFor = { RuntimeException.class, Exception.class })
	public Response forgotPassword(Request request,Locale locale) {
		// TODO Auto-generated method stub
		slf4jLogger.info("--------start method forgotPassword--------");
		response = new Response();
			try {
			
				UserDto userDto = request.getDataUser();
				
				// Definir les paramètres obligatoires
				Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
				fieldsToVerify.put("L'email de l'utilisateur", userDto.getEmail());
				if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
					response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
					response.setHasError(Boolean.TRUE);
					return response;
				}
				User user = null;
				user = userRepository.findByEmail(userDto.getEmail(), Boolean.FALSE);
				if (user == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("user", locale));
					response.setHasError(Boolean.TRUE);
					return response;
				}

		    	Date targetTime = new Date(); //now
		    	targetTime = DateUtils.addHours(targetTime, paramsUtils.getForgotPasswordCodeValidity()); //add 24 hours
		    	String validationCode=SBaseUtils.generateAlphanumericCode(8);
				user.setForgotPasswordCode(validationCode);
				user.setCodeExpiredAt(targetTime);
				user.setIsValidCode(Boolean.TRUE);
				
				//set mail to user
				Map<String, String> from=new HashMap<>();
	        	from.put("email", paramsUtils.getSmtpUsername());
	        	from.put("user", "PATRICK BEUGRE");
	        	//recipients
	        	List<Map<String, String>> toRecipients = new ArrayList<Map<String, String>>();
	        	Map<String, String> recipient = new HashMap<String, String>();
	        	recipient = new HashMap<String, String>();
	        	recipient.put("email", user.getEmail());
	        	recipient.put("user", user.getUserName());
	        	toRecipients.add(recipient); 
	        	
	        	
	        	//subject
	        	String subject = "Changement de mot de passe !";
	        	String message ="Merci de valider le changement de votre mot de passe avec le code suivant ! \n\n";
	        	String body="" + message;
	        	body+= "Code de validation utilisable une seule fois avant la date d'expiration : " + validationCode;
	        	body+="\n\nDate d'expiration : " + dateFormat.format(targetTime);
	        	body+="\n\n\n\n--------------------------------------";
	        	body+="\n\nCe mail est automatique, merci de ne pas y répondre ! ";
	        	response = springBaseUtils.sendEmail(from, toRecipients, subject,body,null);
	        	if(!response.isHasError()){
	        		// inserer les données en base de données
					userRepository.save(user);

					// Renseigner les données à retourner dans la reponse
					response.setStatus(functionalError.SUCCESS("", locale));
	        	}else {
	        		response.setStatus(functionalError.SAVE_FAIL("", locale));
	        		return response;
	        	}
				

			slf4jLogger.info("--------end method forgotPassword--------");
		} catch (PermissionDeniedDataAccessException e) {
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}
		return response;
		
	}
	@Transactional( rollbackFor = { RuntimeException.class, Exception.class })
	public Response forgotPasswordValidation(Request request,Locale locale) {
		// TODO Auto-generated method stub
		slf4jLogger.info("--------start method forgotPasswordValidation--------");
		response = new Response();
			try {
				UserDto userDto = request.getDataUser();
				// Definir les paramètres obligatoires
				Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
				fieldsToVerify.put("username", userDto.getUserName());
				fieldsToVerify.put("Le code de validation", userDto.getValidationCode());
				fieldsToVerify.put("Le nouveau mot de passe", userDto.getNewPassword());
				if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
					response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(),locale));
					response.setHasError(Boolean.TRUE);
					return response;
				}
				User user = null;
				user = userRepository.getByForgot(userDto.getUserName(),userDto.getValidationCode() ,
						Boolean.FALSE,Boolean.FALSE);
				if (user == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("user", locale));
					response.setHasError(Boolean.TRUE);
					return response;
				}
				Date date =new Date();
				if(user.getCodeExpiredAt() != null && date.after(user.getCodeExpiredAt()) || user.getIsValidCode() != null && !user.getIsValidCode()){
					response.setStatus(functionalError.REQUEST_FAIL("code",locale));
					response.setHasError(Boolean.TRUE);
					return response;
				}

				user.setPassword(SBaseUtils.encrypt(userDto.getNewPassword()));
				user.setIsValidCode(Boolean.FALSE);
				// inserer les données en base de données
				userRepository.save(user);

				// Renseigner les données à retourner dans la reponse
				response.setHasError(Boolean.FALSE);
				response.setStatus(functionalError.SUCCESS("", locale));

			slf4jLogger.info("--------end method forgotPasswordValidation--------");
		} catch (PermissionDeniedDataAccessException e) {
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}
		return response;
		
	}


	/**
	 * get User by using UserDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@SuppressWarnings("unused")
	@Override
	public Response getByCriteria(Request request, Locale locale) {
		slf4jLogger.info("----begin-----");
		
		response = new Response();
		
		try {
			List<User> items = null;
			items = userRepository.getByCriteria(request, em);
			if (items != null && !items.isEmpty()) {
				List<UserDto> itemsDto = new ArrayList<UserDto>();
				for (User entity : items) {
					UserDto dto = getFullInfos(entity, locale);
					if (dto == null) continue;
					itemsDto.add(dto);
				}
				response.setItemsUser(itemsDto);
				response.setCount(userRepository.count(request, em));
				response.setHasError(false);
				
			} else {
				response.setStatus(functionalError.DATA_EMPTY("user", locale));
				response.setHasError(false);
				return response;
			}
			response.setStatus(functionalError.SUCCESS("", locale));
			slf4jLogger.info("----end-----");
		} catch (PermissionDeniedDataAccessException e) {
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}
		return response;
	}

	/**
	 * get full UserDto by using User as object.
	 * 
	 * @param entity, locale
	 * @return UserDto
	 * 
	 */
	private UserDto getFullInfos(User entity, Locale locale) throws Exception {
		UserDto dto = UserTransformer.INSTANCE.toDto(entity);
		if (dto == null){
			return null;
		}
		dto.setPassword(null);
		return dto;
	}
}
