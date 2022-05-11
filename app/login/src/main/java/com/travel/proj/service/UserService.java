package com.travel.proj.service;

import com.travel.proj.dto.ResponseDto;
import com.travel.proj.model.User;
import com.travel.proj.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import java.sql.Timestamp;
import java.util.Map;
import java.util.Random;


@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    
    @Autowired
    JavaMailSender mailSender;
    



    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    @Transactional
    public void register(User user){
        user.setAuth("true");
        userRepository.save(user);
    }

    @Transactional
    public User checkEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->{
           return new IllegalArgumentException("중복 데이터 없음");
        });
    }

    public boolean sendMail(Map<String, String> info, HttpServletRequest request){
        int result;
        String name = info.get("nickname");
        String email = info.get("email");
        HttpSession session = request.getSession();
        String userKey = rand();
        session.setAttribute("emailChk", userKey);
        String body = "<h3>" + name + "님</h3>" + "<p>다음 url을 통하여 인증해주세요  <b>" + "http://localhost:8080/email-auth?authValue="+userKey
                + "</b>   감사합니다.</p>";
        result = sendEmail(email, "이메일인증입니다.", body);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }


    //전송할 이메일 세팅
    private int sendEmail(String to, String subject, String body) {
        int result = 0;
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //authCode에 들어갈 값 생성
    private String rand() {
        Random ran = new Random();
        String str = "";
        int num;
        while (str.length() != 20) {
            num = ran.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                str += (char) num;
            } else {
                continue;
            }
        }
        return str;
    }

    public int checkAuthCode(String authValue, HttpSession session) {
        int result = 0;
        if(session.getAttribute("emailChk").equals(authValue)){
                result = 1;
            }
        return result;
    }

    @Transactional(readOnly = true)
    public User login(User user) {
        return userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());

    }

    @Transactional
    public void logout(HttpSession session, String email) {
        session.invalidate();
        User user = userRepository.findByEmail(email).orElseThrow(()->{
            return new IllegalArgumentException("회원 없음");
        });
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setLogoutTime(timestamp);
    }
}





