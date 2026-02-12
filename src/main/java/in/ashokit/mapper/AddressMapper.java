package in.ashokit.mapper;

import in.ashokit.dto.ShippingAddressDto;
import in.ashokit.entity.ShippingAddressEntity;
import org.modelmapper.ModelMapper;

public class AddressMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ShippingAddressEntity dtoToEntity(ShippingAddressDto shippingAddressDto) {
        return modelMapper.map(shippingAddressDto, ShippingAddressEntity.class);
    }

    public static ShippingAddressDto entityToDto(ShippingAddressEntity shippingAddressEntity) {
        return modelMapper.map(shippingAddressEntity, ShippingAddressDto.class);
    }
}
