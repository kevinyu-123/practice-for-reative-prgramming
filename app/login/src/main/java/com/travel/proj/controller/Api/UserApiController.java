package com.travel.proj.controller.Api;

import com.travel.proj.config.sessionConfig.SessionConfig;
import com.travel.proj.dto.ResponseDto;
import com.travel.proj.model.User;
import com.travel.proj.repository.UserRepository;
import com.travel.proj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserApiController {

    private final UserService service;
    private final UserRepository userRepository;

    public UserApiController(UserService service,UserRepository userRepository){
        this.service = service;
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public ResponseDto<Integer> register(@RequestBody User user){
         service.register(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @PostMapping(value = "/check/{email}",produces="application/json; charset=utf-8")
    public ResponseDto<Integer> checkEMail(@PathVariable String email){
         service.checkEmail(email);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);

    }

    @PostMapping("/email")
    public boolean sendMail(@RequestBody Map<String, String> info, HttpServletRequest request){
        boolean result = service.sendMail(info,request);
        return result;
    }

    @Transactional
    @PostMapping("/login")
    public ResponseDto<Integer> login(@RequestBody User user,HttpServletRequest request){
       User info =  service.login(user);

       if(info != null){
       String removed_email = SessionConfig.getSessionidCheck("userInfo",info.getEmail());
            if(removed_email != null && !removed_email.isEmpty() ){
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                info.setLogoutTime(timestamp);
                userRepository.save(info);
            }

           HttpSession session = request.getSession();
           session.setAttribute("userInfo",info);

           Timestamp timestamp = new Timestamp(System.currentTimeMillis());
           info.setLoginTime(timestamp);
           userRepository.save(info);

           return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
       }else {
           return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(), 0);
       }
    }

}
