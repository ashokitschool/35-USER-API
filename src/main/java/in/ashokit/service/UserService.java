package in.ashokit.service;

import in.ashokit.dto.ResetPwdDto;
import in.ashokit.dto.ShippingAddressDto;
import in.ashokit.dto.UserDto;

public interface UserService {

    public UserDto saveUser(UserDto userDto);

    public UserDto login(String email, String password);

    public UserDto getUserByEmail(String email);

    public ResetPwdDto resetPassword(ResetPwdDto resetPwdDto);

    public ShippingAddressDto saveShippingAddress(Integer userId, ShippingAddressDto shippingAddressDto);

    public ShippingAddressDto deleteShippingAddress(Integer shippingAddressId);

}
