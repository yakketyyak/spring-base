/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.patrickbeugre.spring.base.helper.contrat;

/**
 *
 * @author fossouulrich
 */
public class NumberDto {
	private Long nombre;

	public NumberDto(Long nombre) {
		this.nombre = nombre;
	}

	public NumberDto(Integer nombre) {
		this.nombre = (long) nombre;
	}

	public Long getNombre() {
		return nombre;
	}

	public void setNombre(Long nombre) {
		this.nombre = nombre;
	}
}
