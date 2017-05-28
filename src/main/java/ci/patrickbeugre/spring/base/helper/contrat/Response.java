
package ci.patrickbeugre.spring.base.helper.contrat;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ci.patrickbeugre.spring.base.helper.dto.UserDto;

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class Response extends ResponseBase {
	
	                
	protected List<UserDto>                      itemsUser                     ;                     

	//----------------------------------------------------------------------
    // GETTER(S) & SETTER(S) FOR DATA FIELDS
    //----------------------------------------------------------------------

	
	/**
     * Get the "itemsUser" field value
     * @return the field value
     */
	public List<UserDto> getItemsUser() {
		return itemsUser;
	}
	/**
     * Set the "itemsUser" field value
     * @param itemsUser
     */
	public void setItemsUser(List<UserDto> itemsUser) {
		this.itemsUser = itemsUser;
	} 


}