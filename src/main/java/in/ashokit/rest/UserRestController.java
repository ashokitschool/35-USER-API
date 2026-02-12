package in.ashokit.rest;

import in.ashokit.dto.ApiResponse;
import in.ashokit.dto.ResetPwdDto;
import in.ashokit.dto.ShippingAddressDto;
import in.ashokit.dto.UserDto;
import in.ashokit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody UserDto userDto) {
        UserDto savedUser = userService.saveUser(userDto);
        ApiResponse<UserDto> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("User created successfully");
        response.setData(savedUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDto>> loginUser(@RequestBody UserDto userDto) {
        UserDto loggedInUser = userService.login(userDto.getEmail(), userDto.getPwd());
        ApiResponse<UserDto> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Login successful");
        response.setData(loggedInUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody ResetPwdDto resetPwdDto) {
        userService.resetPassword(resetPwdDto);
        ApiResponse<String> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Password reset successfully");
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<ApiResponse<UserDto>> getUserByEmail(@RequestParam String email) {
        UserDto userDto = userService.getUserByEmail(email);
        ApiResponse<UserDto> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User retrieved successfully");
        response.setData(userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{userId}/shipping-address")
    public ResponseEntity<ApiResponse<ShippingAddressDto>> saveShippingAddress(@PathVariable Integer userId, @RequestBody ShippingAddressDto shippingAddressDto) {
        ShippingAddressDto savedAddress = userService.saveShippingAddress(userId, shippingAddressDto);
        ApiResponse<ShippingAddressDto> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Shipping address saved successfully");
        response.setData(savedAddress);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/shipping-address/{shippingAddressId}")
    public ResponseEntity<ApiResponse<ShippingAddressDto>> deleteShippingAddress(@PathVariable Integer shippingAddressId) {
        ShippingAddressDto deletedAddress = userService.deleteShippingAddress(shippingAddressId);
        ApiResponse<ShippingAddressDto> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Shipping address deleted successfully");
        response.setData(deletedAddress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
