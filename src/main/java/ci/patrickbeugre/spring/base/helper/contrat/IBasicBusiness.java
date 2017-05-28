/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.patrickbeugre.spring.base.helper.contrat;

import java.util.Locale;


/**
 *
 * @author
 */

public interface IBasicBusiness<T, K> {

	/**
	 * create Object by using T as object.
	 * 
	 * @param T
	 * @return K
	 * 
	 */
	public abstract K create(T request, Locale locale);

	/**
	 * update Object by using T as object.
	 * 
	 * @param T
	 * @return K
	 * 
	 */
	public abstract K update(T request, Locale locale);

	/**
	 * delete Object by using T as object.
	 * 
	 * @param T
	 * @return K
	 * 
	 */
	public abstract K delete(T request, Locale locale);

	/**
	 * get a List of Object by using T as criteria object.
	 * 
	 * @param T
	 * @return K
	 * 
	 */
	public abstract K getByCriteria(T request, Locale locale);
/*

	void createBilianStock(Integer idStock,Integer Qte);
}*/
}
