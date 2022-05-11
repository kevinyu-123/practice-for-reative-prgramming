package com.travel.proj.controller.Api;

import com.travel.proj.dto.ResponseDto;
import com.travel.proj.model.Board;
import com.travel.proj.model.Reply;
import com.travel.proj.model.User;
import com.travel.proj.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class BoardApiController {

    private final BoardService boardService;

    public BoardApiController(BoardService boardService){
        this.boardService = boardService;
    }

    @PostMapping("/board")
    public ResponseDto<Integer> save(@RequestBody Board board, HttpSession session){
        User user = (User) session.getAttribute("userInfo");
        boardService.save(board,user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/board/{boardId}/reply")
    public ResponseDto<Integer> saveReply(@PathVariable int boardId, @RequestBody Reply reply, HttpSession session){
        User user = (User) session.getAttribute("userInfo");
        boardService.saveReply(boardId,reply,user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
