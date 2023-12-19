package com.music.services.impl;

import com.music.authentication.auth.AuthenticationService;
import com.music.model.dto.request.BlockMusicRequestDto;
import com.music.model.dto.response.BlockMusicResponseDto;
import com.music.model.entity.BlockMusic;
import com.music.model.exceptions.BlockMusic.BlockMusicIsPresentException;
import com.music.model.exceptions.BlockMusic.BlockMusicNotFoundException;
import com.music.model.mapper.BlockMusicMapper;
import com.music.repositories.BlockMusicRepository;
import com.music.services.BlockMusicService;
import com.music.services.RepertoireService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlockMusicServiceImpl implements BlockMusicService {

    private final BlockMusicRepository blockMusicRepository;

    private final BlockMusicMapper blockMusicMapper;

    private final AuthenticationService authenticationService;

    private final RepertoireService repertoireService;

    public BlockMusicServiceImpl(BlockMusicRepository blockMusicRepository, BlockMusicMapper blockMusicMapper, AuthenticationService authenticationService, RepertoireService repertoireService) {
        this.blockMusicRepository = blockMusicRepository;
        this.blockMusicMapper = blockMusicMapper;
        this.authenticationService = authenticationService;
        this.repertoireService = repertoireService;
    }

    @Override
    @Transactional(readOnly = false)
    public BlockMusicResponseDto registerBlockMusic(BlockMusicRequestDto blockMusicRequestDto) {
        existingBlockMusic(blockMusicRequestDto.getNmBlockMusic(),blockMusicRequestDto.getCdUser());
        BlockMusic blockMusic = blockMusicMapper.toBlockMusic(blockMusicRequestDto);
        var repertoire = repertoireService.validateRepertoire(blockMusicRequestDto.getCdRepertoire());
        blockMusic.setRepertoire(repertoire);

        return blockMusicMapper.toBlockMusicResponseDto(blockMusicRepository.save(blockMusic));
    }

    @Override
    @Transactional(readOnly = true)
    public void existingBlockMusic(String nmBlockMusic, Long cdRepertoire) {
        blockMusicRepository.findBlockMusicByNmBlockMusicAndRepertoireCdRepertoire(nmBlockMusic, cdRepertoire)
                .ifPresent(blockMusic -> {throw new BlockMusicIsPresentException();
                });
    }

    @Override
    @Transactional(readOnly = true)
    public BlockMusicResponseDto getBlockMusicByCdBlockMusic(Long cdBlockMusic) {
        BlockMusic blockMusic = validateBlockMusic(cdBlockMusic);

        return blockMusicMapper.toBlockMusicResponseDto(blockMusic);
    }

    @Override
    @Transactional(readOnly = true)
    public BlockMusic validateBlockMusic(Long cdBlockMusic) {
        return blockMusicRepository.findById(cdBlockMusic).orElseThrow(BlockMusicNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockMusicResponseDto> getAllBlockMusic(Long cdUser) {
        List<BlockMusic> blockMusicList = blockMusicRepository.findAllBlockMusicByUserCdUser(cdUser);
        if (blockMusicList.isEmpty()) throw new BlockMusicNotFoundException();

        return blockMusicMapper.toListBlockMusicResponseDto(blockMusicList);
    }

    @Override
    @Transactional(readOnly = false)
    public BlockMusicResponseDto updateBlockMusic(Long cdBlockMusic, BlockMusicRequestDto blockMusicRequestDto) {
        BlockMusic blockMusic = validateBlockMusic(cdBlockMusic);
        blockMusic.setNmBlockMusic(blockMusicRequestDto.getNmBlockMusic());
        return blockMusicMapper.toBlockMusicResponseDto(blockMusicRepository.save(blockMusic));
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteBlockMusic(Long cdBlockMusic) {
        BlockMusic blockMusic = validateBlockMusic(cdBlockMusic);
        blockMusicRepository.delete(blockMusic);
        return "Bloco com ID " + cdBlockMusic + " excluído com sucesso!";
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockMusic> getBlockMusicByCdBlockMusics(List<Long> cdBlockMusics) {
        List<BlockMusic> blockMusics = new ArrayList<>();
        for (Long item : cdBlockMusics) {
            Optional<BlockMusic> blockMusic = blockMusicRepository.findById(item);
            blockMusic.ifPresent(blockMusics::add);
        }
        return blockMusics;
    }

}
