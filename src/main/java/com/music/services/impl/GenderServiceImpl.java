package com.music.services.impl;

import com.music.exception.AlertException;
import com.music.model.dto.request.GenderRequestDto;
import com.music.model.dto.response.GenderResponseDto;
import com.music.model.entity.Gender;
import com.music.model.mapper.GenderMapper;
import com.music.repositories.GenderRepository;
import com.music.services.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenderServiceImpl implements GenderService {

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private GenderMapper genderMapper;

    @Override
    public GenderResponseDto registerGender(GenderRequestDto genderRequestDto){

        Optional<Gender> genderOptional = genderRepository.findByNmGender(genderRequestDto.getNmGender());

        if (genderOptional.isPresent()){
            throw new AlertException(
                    "warn",
                    String.format("Gênero com o nome %S já existe", genderRequestDto.getNmGender()),
                    HttpStatus.CONFLICT
            );
        }
        return genderMapper.toGenderResponseDto(genderRepository.save(genderMapper.toGender(genderRequestDto)));
    }
}
