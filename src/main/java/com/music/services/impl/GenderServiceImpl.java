package com.music.services.impl;

import com.music.exception.AlertException;
import com.music.model.dto.request.GenderRequestDto;
import com.music.model.dto.response.GenderResponseDto;
import com.music.model.entity.Gender;
import com.music.model.mapper.GenderMapper;
import com.music.repositories.GenderRepository;
import com.music.services.GenderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenderServiceImpl implements GenderService {

    private final GenderRepository genderRepository;


    private final GenderMapper genderMapper;

    public GenderServiceImpl(GenderRepository genderRepository, GenderMapper genderMapper) {
        this.genderRepository = genderRepository;
        this.genderMapper = genderMapper;
    }

    @Override
    @Transactional(readOnly = false)
    public GenderResponseDto registerGender(GenderRequestDto genderRequestDto) {
        existingGender(genderRequestDto.getNmGender());

        return genderMapper.toGenderResponseDto(genderRepository.save(genderMapper.toGender(genderRequestDto)));
    }

    @Transactional(readOnly = true)
    public void existingGender(String nmGender) {
        Optional<Gender> optionalGender = genderRepository.findByNmGender(nmGender);

        if (optionalGender.isPresent()) {
            throw new AlertException(
                    "warn",
                    String.format("Gênero %S já está cadastrado!", nmGender),
                    HttpStatus.CONFLICT
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenderResponseDto> getAllGender() {
        List<Gender> genderList = validateListGender();

        return genderList.stream().map(genderMapper::toGenderResponseDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Gender> validateListGender() {
        List<Gender> genderList = genderRepository.findAll();

        if (genderList.isEmpty()) {
            throw new AlertException(
                    "warn",
                    "Nenhum gênero encontrado!",
                    HttpStatus.NOT_FOUND
            );
        }
        return genderList;
    }

    @Override
    @Transactional(readOnly = true)
    public GenderResponseDto getGenderById(Long cdGender) {
        var gender = validateGender(cdGender);

        return genderMapper.toGenderResponseDto(gender);
    }

    @Transactional(readOnly = true)
    public Gender validateGender(Long cdGender) {
        Optional<Gender> optionalGender = genderRepository.findById(cdGender);

        if (optionalGender.isEmpty()) {
            throw new AlertException(
                    "warn",
                    String.format("Genêro com id %S não cadastrado!", cdGender),
                    HttpStatus.NOT_FOUND
            );
        }
        return optionalGender.get();
    }

    @Override
    @Transactional(readOnly = false)
    public GenderResponseDto updateGender(Long cdGender, GenderRequestDto genderRequestDto) {
        Gender gender = validateGender(cdGender);
        gender.setNmGender(genderRequestDto.getNmGender() != null ? genderRequestDto.getNmGender() : gender.getNmGender());
        return genderMapper.toGenderResponseDto(genderRepository.save(gender));
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteGender(Long cdGender) {
        Gender gender = validateGender(cdGender);
        genderRepository.delete(gender);
        return "Genêro com ID " + cdGender + " excluído com sucesso!";
    }


}
