

/*
 * Java transformer for entity table user 
 * Created on 23 mai 2017 ( Time 18:24:48 )
 * Generator tool : Telosys Tools Generator ( version 2.1.1 )
 * Copyright 2017 patrickbeugre. All Rights Reserved.
 */

package ci.patrickbeugre.spring.base.helper.dto.transformer;

import java.text.ParseException;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import ci.patrickbeugre.spring.base.dao.entity.User;
import ci.patrickbeugre.spring.base.helper.dto.UserDto;


/**
TRANSFORMER for table "user"
 * 
 * @author patrickbeugre
 *
 */
@Mapper
public interface UserTransformer {

	UserTransformer INSTANCE = Mappers.getMapper(UserTransformer.class);

	@Mappings({
		@Mapping(source="entity.lastLogin", dateFormat="dd/MM/yyyy HH:mm:ss",target="lastLogin"),
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
	})
	UserDto toDto(User entity) throws ParseException;

    List<UserDto> toDtos(List<User> entities) throws ParseException;

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.userName", target="userName"),
		@Mapping(source="dto.password", target="password"),
		@Mapping(source="dto.email", target="email"),
		@Mapping(source="dto.locked", target="locked"),
		@Mapping(source="dto.expired", target="expired"),
		@Mapping(source="dto.lastLogin", dateFormat="dd/MM/yyyy HH:mm:ss",target="lastLogin"),
		@Mapping(source="dto.pwdIsDefaultPwd", target="pwdIsDefaultPwd"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
	})
    User toEntity(UserDto dto) throws ParseException;

    //List<User> toEntities(List<UserDto> dtos) throws ParseException;

}
