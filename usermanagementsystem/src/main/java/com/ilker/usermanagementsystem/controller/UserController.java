package com.ilker.usermanagementsystem.controller;

import com.ilker.usermanagementsystem.dto.ResReq;
import com.ilker.usermanagementsystem.repository.UserRepository;
import com.ilker.usermanagementsystem.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserManagementService userManagementService;

    @PostMapping("/auth/register")
    public ResponseEntity<ResReq> register(@RequestBody ResReq reg){
        return ResponseEntity.ok(userManagementService.register(reg));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResReq> login(@RequestBody ResReq login){
        return ResponseEntity.ok(userManagementService.login(login));
    }

    // * UserManagementService delete-update-getAll methotları yazıldıktan buraya işlenebilir.


}
