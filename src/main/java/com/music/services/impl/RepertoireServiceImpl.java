package com.music.services.impl;

import com.music.infra.auth.AuthenticationService;
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
        existingRepertoire(repertoireRequestDto.getNameRepertoire(), repertoireRequestDto.getIdUser());
        Repertoire repertoire = repertoireMapper.toRepertoire(repertoireRequestDto);
        var user = authenticationService.validateUserById(repertoireRequestDto.getIdUser());
        repertoire.setUser(user);

        return repertoireMapper.toRepertoireResponseDto(repertoireRepository.save(repertoire));
    }

    @Override
    @Transactional(readOnly = true)
    public void existingRepertoire(String nameRepertoire, Long idUser) {
        repertoireRepository.findRepertoireByNameRepertoireAndUserIdUser(nameRepertoire, idUser).ifPresent(repertoire -> {
            throw new RepertoireIsPresentException();
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<RepertoireResponseDto> getAllRepertoireByIdUser(Long idUser) {
        List<Repertoire> repertoireList = repertoireRepository.findAllRepertoireByUserIdUser(idUser);
        if (repertoireList.isEmpty()) throw new RepertoireNotFoundException();

        return repertoireMapper.toListRepertoireResponseDto(repertoireList);
    }

    @Override
    @Transactional(readOnly = true)
    public RepertoireResponseDto getRepertoireByIdRepertoire(Long idRepertoire) {
        Repertoire repertoire = validateRepertoire(idRepertoire);

        return repertoireMapper.toRepertoireResponseDto(repertoire);
    }

    @Override
    @Transactional(readOnly = true)
    public Repertoire validateRepertoire(Long idRepertoire) {
        return repertoireRepository.findById(idRepertoire).orElseThrow(RepertoireNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = false)
    public RepertoireResponseDto updateRepertoire(Long idRepertoire, RepertoireRequestDto repertoireRequestDto) {
        existingRepertoire(repertoireRequestDto.getNameRepertoire(), repertoireRequestDto.getIdUser());
        Repertoire repertoire = validateRepertoire(idRepertoire);
        repertoire.setNameRepertoire(repertoireRequestDto.getNameRepertoire());

        return repertoireMapper.toRepertoireResponseDto(repertoireRepository.save(repertoire));
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteRepertoire(Long idRepertoire) {
        Repertoire repertoire = validateRepertoire(idRepertoire);

        repertoire.getBlockMusics().forEach(blockMusic -> blockMusic.setRepertoire(null));

        repertoireRepository.delete(repertoire);

        return "Repertorio com id " + idRepertoire + "excluído com sucesso";
    }
}
