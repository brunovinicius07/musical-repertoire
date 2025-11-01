package com.music.services.impl;

import com.music.model.dto.request.BlockMusicRequestDto;
import com.music.model.dto.request.MusicToBlockRequest;
import com.music.model.dto.response.BlockMusicResponseDto;
import com.music.model.dto.response.MusicResponseDto;
import com.music.model.entity.BlockMusic;
import com.music.model.entity.Music;
import com.music.model.exceptions.BlockMusic.BlockMusicIsPresentException;
import com.music.model.exceptions.BlockMusic.BlockMusicNotFoundException;
import com.music.model.exceptions.Music.MusicNotFoundException;
import com.music.model.mapper.BlockMusicMapper;
import com.music.model.mapper.MusicMapper;
import com.music.repositories.BlockMusicRepository;
import com.music.repositories.MusicRepository;
import com.music.services.BlockMusicService;
import com.music.services.RepertoireService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BlockMusicServiceImpl implements BlockMusicService {

    private final BlockMusicRepository blockMusicRepository;

    private final BlockMusicMapper blockMusicMapper;

    private final RepertoireService repertoireService;

    private final MusicRepository musicRepository;

    private final MusicMapper musicMapper;

    @Override
    @Transactional(readOnly = false)
    public BlockMusicResponseDto registerBlockMusic(BlockMusicRequestDto blockMusicRequestDto) {
        existingBlockMusic(blockMusicRequestDto.getNameBlockMusic(),blockMusicRequestDto.getIdRepertoire());
        BlockMusic blockMusic = blockMusicMapper.toBlockMusic(blockMusicRequestDto);
        var repertoire = repertoireService.validateRepertoire(blockMusicRequestDto.getIdRepertoire());
        blockMusic.setRepertoire(repertoire);

        return blockMusicMapper.toBlockMusicResponseDto(blockMusicRepository.save(blockMusic));
    }

    @Override
    @Transactional(readOnly = true)
    public BlockMusicResponseDto getBlockMusicByIdBlockMusic(Long idBlockMusic) {
        BlockMusic blockMusic = validateBlockMusic(idBlockMusic);

        return blockMusicMapper.toBlockMusicResponseDto(blockMusic);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockMusicResponseDto> getAllBlockMusic(Long idUser) {
        List<BlockMusic> blockMusicList = blockMusicRepository.findAllBlockMusicByUserIdUser(idUser);
        if (blockMusicList.isEmpty()) throw new BlockMusicNotFoundException();

        return blockMusicMapper.toListBlockMusicResponseDto(blockMusicList);
    }

    @Override
    @Transactional(readOnly = false)
    public BlockMusicResponseDto updateBlockMusic(Long idBlockMusic, BlockMusicRequestDto blockMusicRequestDto) {
        existingBlockMusic(blockMusicRequestDto.getNameBlockMusic(),blockMusicRequestDto.getIdRepertoire());
        BlockMusic blockMusic = validateBlockMusic(idBlockMusic);
        blockMusic.setNameBlockMusic(blockMusicRequestDto.getNameBlockMusic());

        return blockMusicMapper.toBlockMusicResponseDto(blockMusicRepository.save(blockMusic));
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteBlockMusic(Long idBlockMusic) {
        BlockMusic blockMusic = validateBlockMusic(idBlockMusic);
        blockMusicRepository.delete(blockMusic);
        
        return "Bloco com ID " + idBlockMusic + " excluído com sucesso!";
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockMusic> getBlockMusicByIdBlockMusics(List<Long> idBlockMusics) {
        List<BlockMusic> blockMusics = new ArrayList<>();
        for (Long item : idBlockMusics) {
            Optional<BlockMusic> blockMusic = blockMusicRepository.findById(item);
            blockMusic.ifPresent(blockMusics::add);
        }
        return blockMusics;
    }

    @Override
    @Transactional(readOnly = false)
    public MusicResponseDto linkMusicToBLock(Long idMusic, MusicToBlockRequest musicToBlockRequest) {
        Music music = musicRepository.findById(musicToBlockRequest.getIdMusic()).orElseThrow(MusicNotFoundException::new);

        List<BlockMusic> blockMusicList = getBlockMusicsByIdsBlocMusic(musicToBlockRequest.getIdsBlockMusic());

        BlockMusic blockMusic = validateBlockMusic(idMusic);

        if (!blockMusic.getMusics().contains(music)) {
            blockMusic.getMusics().add(music);
        }

        blockMusicRepository.save(blockMusic);

        music.getBlockMusics().addAll(blockMusicList);

        return musicMapper.toMusicResponseDto(musicRepository.save(music));
    }

    @Transactional(readOnly = true)
    public List<BlockMusic> getBlockMusicsByIdsBlocMusic(List<Long> idsBlocMusic) {
        List<BlockMusic> blockMusicList = new ArrayList<>();
        for (Long item : idsBlocMusic) {
            Optional<BlockMusic> blockMusic = blockMusicRepository.findById(item);
            blockMusic.ifPresent(blockMusicList::add);
        }
        return blockMusicList;
    }

    @Override
    @Transactional(readOnly = true)
    public void existingBlockMusic(String nameBlockMusic, Long idUser) {
        blockMusicRepository.findBlockMusicByNameBlockMusicAndRepertoireIdRepertoire(nameBlockMusic, idUser)
                .ifPresent(blockMusic -> {throw new BlockMusicIsPresentException();
                });
    }

    @Override
    @Transactional(readOnly = true)
    public BlockMusic validateBlockMusic(Long idBlockMusic) {
        return blockMusicRepository.findById(idBlockMusic).orElseThrow(BlockMusicNotFoundException::new);
    }

}
