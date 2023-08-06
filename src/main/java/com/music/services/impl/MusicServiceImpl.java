package com.music.services.impl;

import com.music.model.mapper.MusicMapper;
import com.music.repositories.MusicRepository;
import com.music.services.MusicService;
import org.springframework.stereotype.Service;

@Service
public class MusicServiceImpl extends MusicService {

    private final MusicMapper musicMapper;

    private final MusicRepository musicRepository;


    public MusicServiceImpl(MusicMapper musicMapper, MusicRepository musicRepository) {
        this.musicMapper = musicMapper;
        this.musicRepository = musicRepository;
    }

    
}
