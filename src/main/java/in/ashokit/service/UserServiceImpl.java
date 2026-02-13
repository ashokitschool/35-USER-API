package in.ashokit.service;

import in.ashokit.dto.ResetPwdDto;
import in.ashokit.dto.ShippingAddressDto;
import in.ashokit.dto.UserDto;
import in.ashokit.entity.RoleEntity;
import in.ashokit.entity.ShippingAddressEntity;
import in.ashokit.entity.UserEntity;
import in.ashokit.mapper.AddressMapper;
import in.ashokit.mapper.UserMapper;
import in.ashokit.repo.RoleRepository;
import in.ashokit.repo.ShippingAddressRepository;
import in.ashokit.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressrepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDto saveUser(UserDto userDto) {

        String randomPwd = generateRandomPwd(5);

        userDto.setPwd(randomPwd);
        userDto.setPwdUpdated("NO");

        UserEntity userEntity = UserMapper.dtoToEntity(userDto);

        Set<RoleEntity> roleSet = new HashSet<>();

        if (userDto.getRoleName() != null) {
            RoleEntity role = roleRepository.findByName(userDto.getRoleName());
            if (role != null) {
                roleSet.add(role);
            } else {
                throw new RuntimeException("Role not found: " + userDto.getRoleName());
            }
        }

        userEntity.setRoles(roleSet);
        UserEntity savedEntity = userRepository.save(userEntity);

        if (savedEntity != null) {
            String emailBody = "Your account has been created. Your temporary password is: " + randomPwd;
            emailService.sendEmail(savedEntity.getEmail(), "Account Created", emailBody);
        }

        return UserMapper.entityToDto(savedEntity);
    }

    @Override
    public UserDto login(String email, String pwd) {

        UserEntity userEntity = userRepository.findByEmailAndPwd(email, pwd);

        if (userEntity != null) {
            String roleName = userEntity.getRoles().stream().findFirst().map(RoleEntity::getName).orElse(null);
            UserDto userDto = UserMapper.entityToDto(userEntity);
            userDto.setRoleName(roleName);
            return userDto;
        }

        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity != null) {
            return UserMapper.entityToDto(userEntity);
        }

        return null;
    }

    @Override
    public ResetPwdDto resetPassword(ResetPwdDto resetPwdDto) {

        if (resetPwdDto == null || resetPwdDto.getEmail() == null) {
            return null;
        }

        UserEntity userEntity = userRepository.findByEmail(resetPwdDto.getEmail());

        if (userEntity != null) {
            if (resetPwdDto.getNewPwd() != null && resetPwdDto.getNewPwd().equals(resetPwdDto.getConfirmPwd())) {
                userEntity.setPwd(resetPwdDto.getNewPwd());
                userEntity.setPwdUpdated("YES");
                userRepository.save(userEntity);
                return resetPwdDto;
            }
        }

        return null;
    }

    @Override
    public ShippingAddressDto saveShippingAddress(Integer userId, ShippingAddressDto shippingAddressDto) {

        ShippingAddressEntity addressEntity = AddressMapper.dtoToEntity(shippingAddressDto);

        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            addressEntity.setUser(userEntity);
            ShippingAddressEntity savedAddrEntity = shippingAddressrepository.save(addressEntity);
            return AddressMapper.entityToDto(savedAddrEntity);
        }

        return null;
    }

    @Override
    public ShippingAddressDto deleteShippingAddress(Integer shippingAddressId) {

        Optional<ShippingAddressEntity> address = shippingAddressrepository.findById(shippingAddressId);
        if (address.isPresent()) {
            ShippingAddressEntity addressEntity = address.get();
            shippingAddressrepository.delete(addressEntity);
            return AddressMapper.entityToDto(addressEntity);
        }
        return null;
    }


    private String generateRandomPwd(int pwdLength) {
        Random random = new Random();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        StringBuilder buffer = new StringBuilder(pwdLength);
        for (int i = 0; i < pwdLength; i++) {
            int randomIndex = random.nextInt(chars.length());
            char charAt = chars.charAt(randomIndex);
            buffer.append(charAt);
        }
        return buffer.toString();
    }

}
