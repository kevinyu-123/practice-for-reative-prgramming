package com.travel.proj.service;

import com.travel.proj.model.Board;
import com.travel.proj.model.Reply;
import com.travel.proj.model.User;
import com.travel.proj.repository.BoardRepository;
import com.travel.proj.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

    public BoardService(BoardRepository boardRepository,ReplyRepository replyRepository){
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }
    @Transactional
    public Board save(Board board, User user) {

        int num = (int) (Math.random()*20)+1;

        board.setTitle(randWord(num));
        board.setContent(randWord(num));
        board.setUser(user);
        return boardRepository.save(board);
    }

    @Transactional
    public Reply saveReply(int boardId, Reply reply, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->{
            return new IllegalArgumentException("해당 게시글이 없습니다.");
        });

        reply.setContent(String.valueOf(board.getContent().hashCode()));
        reply.setBoard(board);
        reply.setUser(user);

      return replyRepository.save(reply);
    }

    private String randWord(int rand) {
        Random random = new Random();
        String str = "";
        int num;
        while (str.length() != rand) {
            num = random.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                str += (char) num;
            } else {
                continue;
            }
        }
        return str;
    }

}
