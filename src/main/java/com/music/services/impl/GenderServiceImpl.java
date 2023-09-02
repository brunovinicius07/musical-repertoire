package com.music.services.impl;

import com.music.exception.AlertException;
import com.music.model.dto.request.GenderRequestDto;
import com.music.model.dto.response.GenderResponseDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.Gender;
import com.music.model.mapper.GenderMapper;
import com.music.repositories.GenderRepository;
import com.music.services.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenderServiceImpl implements GenderService {

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private GenderMapper genderMapper;

    @Override
    public GenderResponseDto registerGender(GenderRequestDto genderRequestDto){
        existingGender(genderRequestDto.getNmGender());

        return genderMapper.toGenderResponseDto(genderRepository.save(genderMapper.toGender(genderRequestDto)));
    }

    public List<GenderResponseDto> getAllGender(){
        List<Gender> genderList = validateListGender();

        return genderList.stream().map((x) -> genderMapper.toGenderResponseDto(x)).collect(Collectors.toList());
    }

    public GenderResponseDto getGenderById(Long cdGender){
        var gender = validateGender(cdGender);

        return genderMapper.toGenderResponseDto(gender);
    }

    public GenderResponseDto updateGender(Long cdGender, GenderRequestDto genderRequestDto){
        Gender gender = validateGender(cdGender);
        gender.setNmGender(genderRequestDto.getNmGender() != null ? genderRequestDto.getNmGender() : gender.getNmGender());
        return genderMapper.toGenderResponseDto(genderRepository.save(gender));
    }

    public String deleteGender(Long cdGender){
        Gender gender = validateGender(cdGender);
        genderRepository.delete(gender);
        return "Genêro com ID " + cdGender + " excluído com sucesso!";
    }

    public void existingGender(String nmGender){
        Optional<Gender> optionalGender = genderRepository.findByNmGender(nmGender);

        if (optionalGender.isPresent()){
            throw new AlertException(
                    "warn",
                    String.format("Gênero %S já está cadastrado!", nmGender ),
                    HttpStatus.CONFLICT
            );
        }
    }

    public Gender validateGender(Long cdGender){
        Optional<Gender> optionalGender = genderRepository.findById(cdGender);

        if(optionalGender.isEmpty()){
            throw new AlertException(
                    "warn",
                    String.format("Genênero com id %S não cadastrado!" , cdGender),
                    HttpStatus.NOT_FOUND
            );
        }
        return optionalGender.get();
    }

    public List<Gender> validateListGender(){
        List<Gender>genderList = genderRepository.findAll();

        if (genderList.isEmpty()){
            throw new AlertException(
                    "warn",
                    String.format("Nenhum gênero encontrado!"),
                    HttpStatus.NOT_FOUND
            );
        }
        return genderList;
    }
}
