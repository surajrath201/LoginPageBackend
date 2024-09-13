package com.smartShoppe.login.Services;

import com.smartShoppe.login.Dto.UserDto;
import com.smartShoppe.login.Entity.UserEntity;
import com.smartShoppe.login.Repository.UserRepository;
import com.smartShoppe.login.Util.ValidationResult;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LoginService {

    final ModelMapper modelMapper;

    final UserRepository userRepository;

    public LoginService(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public UserDto authenticateUser(UserDto userDto){

        if (Objects.isNull(userDto.getEmail()) && Objects.isNull(userDto.getMobileNumber()))
            throw new IllegalArgumentException("UserName is Null");

        UserEntity userEntity = userRepository.getByEmailOrMobileNumber(userDto.getEmail(), userDto.getMobileNumber())
                                .orElseThrow(() -> new IllegalArgumentException("user doesnot exist"));
        if (!Objects.equals(userEntity.getPassword(), userDto.getPassword()))
            throw new IllegalArgumentException("Username or Password Doesn't Match");
        userEntity.setPassword(null);
        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserDto addUser(UserDto userDto){
        if (Objects.isNull(userDto))
            throw new IllegalArgumentException("no User to add");
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        ValidationResult<UserEntity> validationResult = userEntity.validate();

        if (!validationResult.getValid())
            throw new RuntimeException(validationResult.getErrors().toString());
        try {
            userEntity = validationResult.getData();
            UserEntity savedUserDetailsEntity = userRepository.save(userEntity);
            savedUserDetailsEntity.setPassword(null);
            return modelMapper.map(savedUserDetailsEntity, UserDto.class);
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Boolean changeUserPassword(UserDto userDto){

        if (Objects.isNull(userDto) || Objects.isNull(userDto.getPassword()))
            throw new IllegalArgumentException("Username of Password is Null");

        UserEntity userEntity = userRepository.getByEmailOrMobileNumber(userDto.getEmail(), userDto.getMobileNumber())
                .orElseThrow(() -> new IllegalArgumentException("user doesnot exist"));
        try {
            userEntity.setPassword(userDto.getPassword());  // Ensure the password is hashed before setting it
            userRepository.save(userEntity);
            return Boolean.TRUE;
        } catch ( Exception e ){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
