package com.assinger.taskusers.controllers;


import com.assinger.taskusers.config.JwtService;
import com.assinger.taskusers.constants.UserServiceConstants;
import com.assinger.taskusers.dto.FetchAllUsersDto;
import com.assinger.taskusers.dto.LoginDto;
import com.assinger.taskusers.dto.ResponseDto;
import com.assinger.taskusers.dto.UserDto;
import com.assinger.taskusers.service.IUsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping(value="/api/users",produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@AllArgsConstructor
@Slf4j
public class UsersController {


    private IUsersService usersService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<FetchAllUsersDto> getAllUsers(){
        List<UserDto> allUser =  usersService.allUsers();
        FetchAllUsersDto responseDto = new FetchAllUsersDto();
        responseDto.setStatusCode("200");
        responseDto.setMessage("ALL Users");
        responseDto.setUsers(allUser);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }

    @PostMapping
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserDto userDto){
        log.info("userDt ========> "+ userDto);
        usersService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(UserServiceConstants.STATUS_201, UserServiceConstants.USER_CREATION_MESSAGE));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable Long id)  {
        log.info("id for delete "+id.toString());
        usersService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK
        ).body(new ResponseDto(UserServiceConstants.STATUS_200, UserServiceConstants.USER_DELETE_MESSAGE));

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserbyId(@PathVariable Long id) throws Exception {
        UserDto userDto = usersService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);

    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginDto loginDto){
        log.info("====>>>details "+loginDto);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        if(authentication.isAuthenticated()){
            log.info("role ==> "+ authentication.getAuthorities());
            String email = authentication.getName();
            log.info("authentication.details() ---> " +  authentication.getDetails()+" email "+email);
            UserDto userDto =  usersService.findUserByEmail(email);
            String jwtToken =  jwtService.generateToken(userDto.getUserId().intValue(),email,userDto.getRole());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(UserServiceConstants.STATUS_200,jwtToken ));
        }else{
            throw new AccessDeniedException("Not Allowed, Access Denied!");
        }


    }

    @PutMapping("/{userId}")
    public  ResponseEntity<ResponseDto> updateUser(@PathVariable Long userId , @Valid @RequestBody UserDto userDto)
    throws Exception {

        Predicate<Long> emptyNullCheck = (id)-> id!=null && id!=0;
       if(!emptyNullCheck.test(userDto.getUserId()))
           throw  new RuntimeException("user id not found ");

       usersService.updateUser(userId,userDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(UserServiceConstants.STATUS_200,UserServiceConstants.USER_UPDATED_MESSAGE));
    }

    @PatchMapping("/status-change/{userId}")
    public ResponseEntity<ResponseDto> enableDisableUser(@PathVariable Long userId)
    throws Exception{

        usersService.updateUserStatus(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(UserServiceConstants.STATUS_200,UserServiceConstants.USER_STATUS_UPDATE_MESSAGE));
    }



    @GetMapping("/enabled")
    public ResponseEntity<FetchAllUsersDto> getEnabledUsers(){
        List<UserDto> allUser =  usersService.enabledUsersList();
        FetchAllUsersDto responseDto = new FetchAllUsersDto();
        responseDto.setStatusCode(UserServiceConstants.STATUS_200);
        responseDto.setMessage("ALL Users");
        responseDto.setUsers(allUser);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }



}
