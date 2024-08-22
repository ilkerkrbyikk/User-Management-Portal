package com.ilker.usermanagementsystem.service;

import com.ilker.usermanagementsystem.dto.ResReq;
import com.ilker.usermanagementsystem.entity.User;
import com.ilker.usermanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private UserRepository userRepository;
    private JWTUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private Optional<User> user;

    public ResReq register(ResReq registirationRequest){
        ResReq resReq = new ResReq();
         try {
             //? buralar için mapper yazılabilirdi ama package falan açmaya üşendim yazmadım :D
             User user = new User();

             user.setName(registirationRequest.getName());
             user.setEmail(registirationRequest.getEmail());
             user.setCity(registirationRequest.getCity());
             user.setRole(registirationRequest.getRole());
             user.setPassword(passwordEncoder.encode(registirationRequest.getPassword()));

             User createdUser = userRepository.save(user);

             if (createdUser.getId()>0){
                 resReq.setUser(createdUser);
                 resReq.setMessage("Başarıyla kayıt olundu.");
                 resReq.setStatusCode(200); //* Status Code 200 = Request was Succesfull

             }

         } catch (Exception e) {
             resReq.setStatusCode(500);
             resReq.setError(e.getMessage());// * Status Code 500 = Internal Server Error
         }
         return resReq;
    }


    public ResReq login(ResReq loginReq){
        ResReq resReq = new ResReq();

        try {
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            String jwt;
            User user = new User();
            String refreshToken;
            this.user = userRepository.findByEmail(loginReq.getEmail());
            jwt = jwtUtils.generateToken(user);
            refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(),user);
            resReq.setStatusCode(200);
            resReq.setToken(refreshToken);
            resReq.setRefreshToken(refreshToken);
            resReq.setExpirationTime("24Hrs"); // * saat olarakta yazılabiliyormuş.
            resReq.setMessage("Başarıyla giriş yapıldı");


        }
        catch (Exception e) {
            resReq.setStatusCode(500);
            resReq.setError(e.getMessage());
        }
        return resReq;

    }




}
