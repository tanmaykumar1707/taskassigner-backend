package com.assinger.taskusers.config;

import com.assinger.taskusers.entity.UsersEntity;
import com.assinger.taskusers.repository.UsersRepository;
import com.netflix.discovery.converters.Auto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

//    CustomUserDetailsService(UsersRepository usersRepository){
//        this.usersRepository = usersRepository;
//    }
//
//    CustomUserDetailsService(){
//
//    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

                Optional<UsersEntity> usersEntity =  usersRepository.findByEmail(email);

      return   usersEntity.map(CustomUserDetailsEntity::new).orElseThrow(()-> new UsernameNotFoundException("User not found with "+email));

    }
}
