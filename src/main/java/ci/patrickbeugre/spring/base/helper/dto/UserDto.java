
/*
 * Java dto for entity table user 
 * Created on 23 mai 2017 ( Time 18:24:48 )
 * Generator tool : Telosys Tools Generator ( version 2.1.1 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package ci.patrickbeugre.spring.base.helper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



/**
 * DTO for table "user"
 * 
 * @author patrickbeugre
 *
 */
@JsonInclude(Include.NON_NULL)
public class UserDto {

    private Integer    id                   ; // Primary Key

    private String     userName             ;
    private String     password             ;
    private String     newPassword             ;
    private String     email                ;
    private Boolean    locked               ;
    private Boolean    expired              ;
	private String     lastLogin            ;
    private Boolean    pwdIsDefaultPwd      ;
    private Boolean    isDeleted            ;
	private String     createdAt            ;
	private String     validationCode;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------



    /**
     * Default constructor
     */
    public UserDto()
    {
        super();
    }
    
    //----------------------------------------------------------------------
    // GETTER(S) & SETTER(S) FOR THE PRIMARY KEY 
    //----------------------------------------------------------------------
    /**
     * Set the "id" field value
     * @param id
     */
    public void setId( Integer id ){
        this.id = id;
    }
    /**
     * Get the "id" field value
     * @return the field value
     */
    public Integer getId(){
        return this.id;
    }


    //----------------------------------------------------------------------
    // GETTER(S) & SETTER(S) FOR DATA FIELDS
    //----------------------------------------------------------------------
    /**
     * Set the "userName" field value
     * @param userName
     */
	public void setUserName( String userName )
    {
        this.userName = userName ;
    }
    /**
     * Get the "userName" field value
     * @return the field value
     */
	public String getUserName()
    {
        return this.userName;
    }

    /**
     * Set the "password" field value
     * @param password
     */
	public void setPassword( String password )
    {
        this.password = password ;
    }
    /**
     * Get the "password" field value
     * @return the field value
     */
	public String getPassword()
    {
        return this.password;
    }

    /**
     * Set the "email" field value
     * @param email
     */
	public void setEmail( String email )
    {
        this.email = email ;
    }
    /**
     * Get the "email" field value
     * @return the field value
     */
	public String getEmail()
    {
        return this.email;
    }

    /**
     * Set the "locked" field value
     * @param locked
     */
	public void setLocked( Boolean locked )
    {
        this.locked = locked ;
    }
    /**
     * Get the "locked" field value
     * @return the field value
     */
	public Boolean getLocked()
    {
        return this.locked;
    }

    /**
     * Set the "expired" field value
     * @param expired
     */
	public void setExpired( Boolean expired )
    {
        this.expired = expired ;
    }
    /**
     * Get the "expired" field value
     * @return the field value
     */
	public Boolean getExpired()
    {
        return this.expired;
    }

    /**
     * Set the "lastLogin" field value
     * @param lastLogin
     */
	public void setLastLogin( String lastLogin )
    {
        this.lastLogin = lastLogin ;
    }
    /**
     * Get the "lastLogin" field value
     * @return the field value
     */
	public String getLastLogin()
    {
        return this.lastLogin;
    }

    /**
     * Set the "pwdIsDefaultPwd" field value
     * @param pwdIsDefaultPwd
     */
	public void setPwdIsDefaultPwd( Boolean pwdIsDefaultPwd )
    {
        this.pwdIsDefaultPwd = pwdIsDefaultPwd ;
    }
    /**
     * Get the "pwdIsDefaultPwd" field value
     * @return the field value
     */
	public Boolean getPwdIsDefaultPwd()
    {
        return this.pwdIsDefaultPwd;
    }

   

    /**
     * Set the "isDeleted" field value
     * @param isDeleted
     */
	public void setIsDeleted( Boolean isDeleted )
    {
        this.isDeleted = isDeleted ;
    }
    /**
     * Get the "isDeleted" field value
     * @return the field value
     */
	public Boolean getIsDeleted()
    {
        return this.isDeleted;
    }

    /**
     * Set the "createdAt" field value
     * @param createdAt
     */
	public void setCreatedAt( String createdAt )
    {
        this.createdAt = createdAt ;
    }
    /**
     * Get the "createdAt" field value
     * @return the field value
     */
	public String getCreatedAt()
    {
        return this.createdAt;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------


	public String getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	//----------------------------------------------------------------------
    // equals METHOD
    //----------------------------------------------------------------------
	public boolean equals(Object obj) { 
		if ( this == obj ) return true ; 
		if ( obj == null ) return false ;
		if ( this.getClass() != obj.getClass() ) return false ; 
		UserDto other = (UserDto) obj; 
		//--- Attribute id
		if ( id == null ) { 
			if ( other.id != null ) 
				return false ; 
		} else if ( ! id.equals(other.id) ) 
			return false ; 
		return true; 
	} 


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() {
		StringBuffer sb = new StringBuffer(); 
		sb.append(id); 
		sb.append("|"); 
		sb.append(userName); 
		sb.append("|"); 
		sb.append(password); 
		sb.append("|"); 
		sb.append(email); 
		sb.append("|"); 
		sb.append(locked); 
		sb.append("|"); 
		sb.append(expired); 
		sb.append("|"); 
		sb.append(lastLogin); 
		sb.append("|"); 
		sb.append(pwdIsDefaultPwd); 
		sb.append("|"); 
		sb.append(isDeleted); 
		sb.append("|"); 
		sb.append(createdAt); 
        return sb.toString();
    }
}
