package ci.patrickbeugre.spring.base.dao.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ci.patrickbeugre.spring.base.dao.entity.User;
import ci.patrickbeugre.spring.base.helper.SBaseUtils;
import ci.patrickbeugre.spring.base.helper.contrat.NumberDto;
import ci.patrickbeugre.spring.base.helper.contrat.Request;
import ci.patrickbeugre.spring.base.helper.dto.UserDto;

/**
 * Repository : User.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	/**
	 * Finds User by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object User whose id is equals to the given id. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.id = :id and e.isDeleted = :isDeleted")
	User findById(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds User by using userName as a search criteria.
	 * 
	 * @param userName
	 * @return An Object User whose userName is equals to the given userName. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.userName = :userName and e.isDeleted = :isDeleted")
	User findByUserName(@Param("userName")String userName, @Param("isDeleted")Boolean isDeleted);
	
	@Query("select u from User u where u.userName = :userName and u.forgotPasswordCode= :forgotPasswordCode and e.locked = :locked and u.isDeleted = :isDeleted")
	User getByForgot(@Param("userName") String userName,@Param("forgotPasswordCode") String forgotPasswordCode,@Param("locked")Boolean locked, @Param("isDeleted") Boolean isDeleted);
	
	@Query("select e from User e where e.userName = :userName and e.password = :password and e.locked = :locked and e.isDeleted = :isDeleted")
	User login(@Param("userName")String userName,@Param("password")String password,@Param("locked")Boolean locked, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using password as a search criteria.
	 * 
	 * @param password
	 * @return An Object User whose password is equals to the given password. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.password = :password and e.isDeleted = :isDeleted")
	User findByPassword(@Param("password")String password, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using email as a search criteria.
	 * 
	 * @param email
	 * @return An Object User whose email is equals to the given email. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.email = :email and e.isDeleted = :isDeleted")
	User findByEmail(@Param("email")String email, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using locked as a search criteria.
	 * 
	 * @param locked
	 * @return An Object User whose locked is equals to the given locked. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.locked = :locked and e.isDeleted = :isDeleted")
	User findByLocked(@Param("locked")Boolean locked, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using expired as a search criteria.
	 * 
	 * @param expired
	 * @return An Object User whose expired is equals to the given expired. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.expired = :expired and e.isDeleted = :isDeleted")
	User findByExpired(@Param("expired")Boolean expired, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using lastLogin as a search criteria.
	 * 
	 * @param lastLogin
	 * @return An Object User whose lastLogin is equals to the given lastLogin. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.lastLogin = :lastLogin and e.isDeleted = :isDeleted")
	User findByLastLogin(@Param("lastLogin")Date lastLogin, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using pwdIsDefaultPwd as a search criteria.
	 * 
	 * @param pwdIsDefaultPwd
	 * @return An Object User whose pwdIsDefaultPwd is equals to the given pwdIsDefaultPwd. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.pwdIsDefaultPwd = :pwdIsDefaultPwd and e.isDeleted = :isDeleted")
	User findByPwdIsDefaultPwd(@Param("pwdIsDefaultPwd")Boolean pwdIsDefaultPwd, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object User whose isDeleted is equals to the given isDeleted. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.isDeleted = :isDeleted")
	User findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds User by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object User whose createdAt is equals to the given createdAt. If
	 *         no User is found, this method returns null.
	 */
	@Query("select e from User e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	User findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);

	

	/**
	 * Finds List of User by using userDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of User
	 * @throws DataAccessException,ParseException
	 */
	public default List<User> getByCriteria(Request request, EntityManager em) throws DataAccessException, ParseException {
		String req = "select e from User e where e IS NOT NULL";
		HashMap<String, Object> param = new HashMap<>();

		UserDto dto = request.getDataUser();
		
		dto.setIsDeleted(false);
		req = generateCriteria(dto, req, param);
		req += " order by  e.id desc";

		TypedQuery<User> query = em.createQuery(req, User.class);

		for (Map.Entry<String, Object> entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		if (request.getIndex() != null && request.getSize() != null) {
			query.setFirstResult(request.getIndex() * request.getSize());
			query.setMaxResults(request.getSize());
		}

		return query.getResultList();
	}

	/**
	 * Finds count of User by using userDto as a search criteria.
	 * 
	 * @param request,em
	 * @return Number of User
	 * 
	 */
	public default Long count(Request request, EntityManager em) throws DataAccessException, ParseException  {
		String req = "select new ci.patrickbeugre.spring.base.helper.contrat.NumberDto(count(e.id)) from User e where e IS NOT NULL";
		HashMap<String, Object> param = new HashMap<>();

		UserDto dto = request.getDataUser();

		dto.setIsDeleted(false);
		req = generateCriteria(dto, req, param);

		javax.persistence.Query query = em.createQuery(req);

		for (Map.Entry<String, Object> entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		NumberDto number = (NumberDto) query.getResultList().get(0);
		return number.getNombre();
	}

	default String generateCriteria(UserDto dto, String req, HashMap<String, Object> param) throws ParseException{
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				req += " AND e.id = :id";
				param.put("id", dto.getId());
			}
			if (dto.getUserName()!= null && !dto.getUserName().isEmpty()) {
				req += " AND e.userName = :userName";
				param.put("userName", dto.getUserName());
			}
			if (dto.getPassword()!= null && !dto.getPassword().isEmpty()) {
				
				try {
					req += " AND e.password = :password";
					param.put("password", SBaseUtils.encrypt(dto.getPassword()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (dto.getEmail()!= null && !dto.getEmail().isEmpty()) {
				req += " AND e.email = :email";
				param.put("email", dto.getEmail());
			}
			if (dto.getLocked()!= null) {
				req += " AND e.locked = :locked";
				param.put("locked", dto.getLocked());
			}
			if (dto.getExpired()!= null) {
				req += " AND e.expired = :expired";
				param.put("expired", dto.getExpired());
			}
			if (dto.getLastLogin()!= null && !dto.getLastLogin().isEmpty()) {
				req += " AND FUNCTION('DAY', e.lastLogin) = FUNCTION('DAY', :lastLogin) AND FUNCTION('MONTH', e.lastLogin) = FUNCTION('MONTH', :lastLogin) AND FUNCTION('YEAR', e.lastLogin) = FUNCTION('YEAR', :lastLogin) ";
				param.put("lastLogin",  new SimpleDateFormat("dd/MM/yyyy").parse(dto.getLastLogin()));
			}
			if (dto.getPwdIsDefaultPwd()!= null) {
				req += " AND e.pwdIsDefaultPwd = :pwdIsDefaultPwd";
				param.put("pwdIsDefaultPwd", dto.getPwdIsDefaultPwd());
			}
			if (dto.getIsDeleted()!= null) {
				req += " AND e.isDeleted = :isDeleted";
				param.put("isDeleted", dto.getIsDeleted());
			}
			if (dto.getCreatedAt()!= null && !dto.getCreatedAt().isEmpty()) {
				req += " AND FUNCTION('DAY', e.createdAt) = FUNCTION('DAY', :createdAt) AND FUNCTION('MONTH', e.createdAt) = FUNCTION('MONTH', :createdAt) AND FUNCTION('YEAR', e.createdAt) = FUNCTION('YEAR', :createdAt) ";
				param.put("createdAt",  new SimpleDateFormat("dd/MM/yyyy").parse(dto.getCreatedAt()));
			}
		}
		
		return req;
	}
}
