package ci.patrickbeugre.spring.base.helper.contrat;

import java.util.ArrayList;
import java.util.List;

public class RestResponse<T> extends ResponseBase {

	List<T> items;

	public RestResponse() {
		items = new ArrayList<T>();
	}

	/**
	 * @return the items
	 */
	public List<T> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<T> items) {
		this.items = items;
	}

}
