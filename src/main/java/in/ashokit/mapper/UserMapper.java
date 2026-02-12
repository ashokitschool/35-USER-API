package in.ashokit.mapper;

import in.ashokit.dto.UserDto;
import in.ashokit.entity.UserEntity;
import org.modelmapper.ModelMapper;

public class UserMapper {

        public static final ModelMapper mapper = new ModelMapper();

        public static UserDto entityToDto(UserEntity entity) {
                return mapper.map(entity, UserDto.class);
        }

        public static UserEntity dtoToEntity(UserDto dto) {
                return mapper.map(dto, UserEntity.class);
        }
}
