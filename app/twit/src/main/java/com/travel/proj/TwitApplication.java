package com.travel.proj;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.proj.dto.ResponseDto;
import com.travel.proj.model.Board;
import com.travel.proj.model.Reply;
import com.travel.proj.model.User;
import com.travel.proj.repository.BoardRepository;
import com.travel.proj.repository.UserRepository;
import com.travel.proj.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class TwitApplication {
    public static void main(String[] args) {
        SpringApplication.run(TwitApplication.class,args);
    }
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardService boardService;

    @Bean
    public ApplicationRunner applicationRunner(){
        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) throws Exception {
                Board board = new Board();
                Reply reply = new Reply();
                int randUser = (int)(Math.random()*5)+1;
                int rand_board_no = (int)(Math.random()*6)+1;

                WebClient webClient = WebClient.builder()
                        .baseUrl("http://localhost:8080")
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build();

               Optional<User> user = userRepository.findById(randUser);

               ResponseDto login_result = webClient
                       .post()
                       .uri("/user/login")
                       .contentType(MediaType.APPLICATION_JSON)
                       .bodyValue(user)
                       .retrieve()
                       .bodyToMono(ResponseDto.class).block();

               log.info("result: "+(login_result.getData()));

               ObjectMapper mapper = new ObjectMapper();
               User in = mapper.convertValue(login_result.getData(), new TypeReference<>() {});

             Board b =  boardService.save(board,in);

               log.info("board : "+boardService.save(board,in));

               Reply reply_result = boardService.saveReply(rand_board_no,reply,in);

                log.info(String.valueOf(reply_result));
               if(b != null){
                   ResponseDto logout_result = webClient
                           .get()
                           .uri(uriBuilder -> uriBuilder.path("/logout").queryParam("email",in.getEmail()).build())
                           .retrieve()
                           .bodyToMono(ResponseDto.class).block();
                   log.info(in.getEmail());
                   log.info("logout: "+logout_result);
               }
            }
        };
    }
}
