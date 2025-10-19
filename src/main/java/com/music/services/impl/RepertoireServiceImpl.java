package com.music.services.impl;

import com.music.authentication.auth.AuthenticationService;
import com.music.model.dto.request.RepertoireRequestDto;
import com.music.model.dto.response.RepertoireResponseDto;
import com.music.model.entity.Repertoire;
import com.music.model.exceptions.Repertoire.RepertoireIsPresentException;
import com.music.model.exceptions.Repertoire.RepertoireNotFoundException;
import com.music.model.mapper.RepertoireMapper;
import com.music.repositories.RepertoireRepository;
import com.music.services.RepertoireService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RepertoireServiceImpl implements RepertoireService {

    private final RepertoireRepository repertoireRepository;

    private final RepertoireMapper repertoireMapper;

    private final AuthenticationService authenticationService;

    @Override
    @Transactional(readOnly = false)
    public RepertoireResponseDto registerRepertoire(RepertoireRequestDto repertoireRequestDto) {
        existingRepertoire(repertoireRequestDto.getNmRepertoire(), repertoireRequestDto.getCdUser());
        Repertoire repertoire = repertoireMapper.toRepertoire(repertoireRequestDto);
        var user = authenticationService.validateUserById(repertoireRequestDto.getCdUser());
        repertoire.setUser(user);

        return repertoireMapper.toRepertoireResponseDto(repertoireRepository.save(repertoire));
    }

    @Override
    @Transactional(readOnly = true)
    public void existingRepertoire(String nmRepertoire, Long cdUser) {
        repertoireRepository.findRepertoireByNmRepertoireAndUserCdUser(nmRepertoire, cdUser).ifPresent(repertoire -> {
            throw new RepertoireIsPresentException();
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<RepertoireResponseDto> getAllRepertoireByCdUser(Long cdUser) {
        List<Repertoire> repertoireList = repertoireRepository.findAllRepertoireByUserCdUser(cdUser);
        if (repertoireList.isEmpty()) throw new RepertoireNotFoundException();

        return repertoireMapper.toListRepertoireResponseDto(repertoireList);
    }

    @Override
    @Transactional(readOnly = true)
    public RepertoireResponseDto getRepertoireByCdRepertoire(Long cdRepertoire) {
        Repertoire repertoire = validateRepertoire(cdRepertoire);

        return repertoireMapper.toRepertoireResponseDto(repertoire);
    }

    @Override
    @Transactional(readOnly = true)
    public Repertoire validateRepertoire(Long cdRepertoire) {
        return repertoireRepository.findById(cdRepertoire).orElseThrow(RepertoireNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = false)
    public RepertoireResponseDto updateRepertoire(Long cdRepertoire, RepertoireRequestDto repertoireRequestDto) {
        existingRepertoire(repertoireRequestDto.getNmRepertoire(), repertoireRequestDto.getCdUser());
        Repertoire repertoire = validateRepertoire(cdRepertoire);
        repertoire.setNmRepertoire(repertoireRequestDto.getNmRepertoire());

        return repertoireMapper.toRepertoireResponseDto(repertoireRepository.save(repertoire));
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteRepertoire(Long cdRepertoire) {
        Repertoire repertoire = validateRepertoire(cdRepertoire);

        repertoire.getBlockMusics().forEach(blockMusic -> blockMusic.setRepertoire(null));

        repertoireRepository.delete(repertoire);

        return "Repertorio com id " + cdRepertoire + "excluído com sucesso";
    }
}
