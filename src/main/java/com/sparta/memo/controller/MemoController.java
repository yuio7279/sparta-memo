package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto create(@RequestBody MemoRequestDto requestDto){
        //requestDto -> Entity로 수정
        Memo memo = new Memo(requestDto);
        // Memo Max ID 찾기
        Long maxId = memoList.size() > 0? Collections.max(memoList.keySet())+1 : 1;
        memo.setId(maxId);
        // DB 저장
        memoList.put(memo.getId(),memo);

        //Entity > responseDto로 수정
        MemoResponseDto responseDto = new MemoResponseDto(memo);

        return responseDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos(){
/*        List<MemoResponseDto> responseList = new ArrayList<>();
        for (Memo memo : memoList.values()){
            MemoResponseDto responseDto = new MemoResponseDto(memo);
            responseList.add(responseDto);
        }*/

        List<MemoResponseDto> responseList = memoList.values()
                                        .stream()
                                        .map(MemoResponseDto::new)
                                        .toList();
        return responseList;
    }
}
