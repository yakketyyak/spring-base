
package ci.patrickbeugre.spring.base.helper.contrat;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ci.patrickbeugre.spring.base.helper.dto.UserDto;

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class Request extends RequestBase {
	
	              
	protected UserDto                                                dataUser                      ;                     
	protected List<UserDto>                                          datasUser                     ;                     

	//----------------------------------------------------------------------
    // GETTER(S) & SETTER(S) FOR DATA FIELDS
    //----------------------------------------------------------------------

	
	/**
     * Get the "dataUser" field value
     * @return the field value
     */
	public UserDto getDataUser() {
		return dataUser;
	}
	/**
     * Set the "dataUser" field value
     * @param dataUser
     */
	public void setDataUser(UserDto dataUser) {
		this.dataUser = dataUser;
	}
	/**
     * Get the "datasUser" field value
     * @return the field value
     */
	public List<UserDto> getDatasUser() {
		return datasUser;
	}
	/**
     * Set the "datasUser" field value
     * @param datasUser
     */
	public void setDatasUser(List<UserDto> datasUser) {
		this.datasUser = datasUser;
	} 


}