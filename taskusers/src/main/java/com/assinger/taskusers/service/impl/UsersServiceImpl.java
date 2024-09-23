package com.assinger.taskusers.service.impl;

import com.assinger.taskusers.dto.UserDto;
import com.assinger.taskusers.dto.UserMqDto;
import com.assinger.taskusers.entity.UsersEntity;
import com.assinger.taskusers.exception.UserNotFoundException;
import com.assinger.taskusers.mapper.UsersMapper;
import com.assinger.taskusers.repository.UsersRepository;
import com.assinger.taskusers.service.IUsersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UsersServiceImpl implements IUsersService {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    private StreamBridge streamBridge;


//    UsersServiceImpl(UsersRepository usersRepository){

    @Override
    public UserDto updateUser(Long userId,UserDto userDto)  {
      UsersEntity saveUserEntity =   usersRepository.findById(userId).map(usersEntity ->{

                    usersEntity.setName(userDto.getName());
                    usersEntity.setEmail(userDto.getEmail());
                    usersEntity.setRole(userDto.getRole());
                    usersEntity.setMobileNumner(userDto.getMobileNumber());
                    return usersRepository.save(usersEntity);
                     }).orElseThrow(()-> new UserNotFoundException("User not found"));


        return null;
    }
//        this.usersRepository  = usersRepository;
//    }





    @Override
    public void createUser(UserDto userDto) {
        UsersEntity usersEntity = new UsersEntity();
        UsersMapper.mapToUsersEntity(usersEntity,userDto);
        log.info("inside the create user and userDto mapped to Entity "+ usersEntity);
        usersEntity.setPassword(passwordEncoder.encode(usersEntity.getPassword()));
    UsersEntity saveUserEntity=usersRepository.save(usersEntity);
        var userMqDto =
                new UserMqDto(saveUserEntity.getEmail(),saveUserEntity.getMobileNumner()
                        ,saveUserEntity.getRole().toString());
        sendCommunication(userMqDto);

    }


    private void sendCommunication(UserMqDto userMqDto){

        log.info("Sending Communication request for the details: {}", userMqDto);
        var result = streamBridge.send("sendCommunication-out-0", userMqDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
    }



        @Override
        public List<UserDto> allUsers() {
           List<UsersEntity> usersEntities =  usersRepository.findAll();
           List<UserDto> userDtos = new ArrayList<>();
            usersEntities.forEach(usersEntity-> {
                UserDto userDto = new UserDto();
                UsersMapper.mapToUsersDto(userDto,usersEntity);
                userDtos.add(userDto);
            });
            return userDtos;
        }

    @Override
    public void deleteUser(Long id)  {
        usersRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with id "+ id));
        usersRepository.deleteById(id);
    }

    @Override
    public UserDto findUserById(Long id)  {
     UsersEntity usersEntity = usersRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with id "+ id));
     UserDto userDto = new UserDto();
     UsersMapper.mapToUsersDto(userDto,usersEntity);
     return userDto;

    }

    @Override
    public UserDto findUserByEmail(String email) {

        UsersEntity usersEntity = usersRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("User not found with "+email));

        UserDto userDto = new UserDto();
        UsersMapper.mapToUsersDto(userDto,usersEntity);
        return userDto;
    }

    @Override
    public void updateUserStatus(Long userId) {

            log.info("user id for status change "+ userId);
            usersRepository.findById(userId).map(usersEntity -> {
                usersEntity.setEnabled(!usersEntity.isEnabled());
                return usersRepository.save(usersEntity);
            }).orElseThrow(()->new UserNotFoundException("User Not found"));


    }

    @Override
    public List<UserDto> enabledUsersList() {
       List<UsersEntity> usersEntityList =
               usersRepository.findAllByEnabledTrue().get();
       List<UserDto> userDtoList = new ArrayList<>();
        usersEntityList.forEach(usersEntity -> {
            UserDto userDto = new UserDto();
            UsersMapper.mapToUsersDto(userDto,usersEntity);
            userDtoList.add(userDto);
        });

        return userDtoList;



    }
}
