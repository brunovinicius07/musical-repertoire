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

    public List<GenderResponseDto> getAllGender(){
        List<Gender> musicList = genderRepository.findAll();

        if (musicList.isEmpty()){
            throw new AlertException(
                    "warn",
                    String.format("Nenhuma música encontrada!"),
                    HttpStatus.NOT_FOUND
            );
        }
        return musicList.stream().map((x) -> genderMapper.toGenderResponseDto(x)).collect(Collectors.toList());
    }

    public GenderResponseDto getGenderById(Long cdGender){
        Optional<Gender> genderOptional = genderRepository.findById(cdGender);

        if(genderOptional.isEmpty()){
            throw new AlertException(
                    "warn",
                    String.format("Gênero com id %S nào existe!", cdGender),
                    HttpStatus.NOT_FOUND
            );
        }
        return genderMapper.toGenderResponseDto(genderOptional.get());
    }

    public GenderResponseDto updateGender(Long cdGender, GenderRequestDto genderRequestDto){
        Optional<Gender> gender = genderRepository.findById(cdGender);

        if(gender.isEmpty()){
            throw new AlertException(
                    "warn",
                    String.format("Gênero com id %S não existe!", cdGender),
                    HttpStatus.NOT_FOUND
            );
        }
        gender.get().setNmGender(genderRequestDto.getNmGender() != null ? genderRequestDto.getNmGender() : gender.get().getNmGender());
        return genderMapper.toGenderResponseDto(genderRepository.save(gender.get()));
    }

    public String deleteGender(Long cdGender){
        Gender existingGender = genderRepository.findById(cdGender)
                .orElseThrow(()-> new AlertException(
                        "warn",
                        String.format("Gênero com id %S não encontrado!", cdGender),
                        HttpStatus.NOT_FOUND
                ));
        genderRepository.delete(existingGender);
        return "Genêro com ID " + cdGender + " excluído com sucesso!";
    }
}
