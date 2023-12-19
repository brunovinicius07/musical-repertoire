//package com.music.services.impl;
//
//import com.music.authentication.config.exceptionHandler.AlertException;
//import com.music.model.dto.request.BlockMusicRequestDto;
//import com.music.model.entity.BlockMusic;
//import com.music.model.entity.Music;
//import com.music.model.entity.User;
//import com.music.model.mapper.BlockMusicMapper;
//import com.music.repositories.BlockMusicRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.mockito.MockitoAnnotations.openMocks;
//
//@SpringBootTest
//class BlockMusicServiceImplTest {
//
//    public static final long CD_GENDER = 1L;
//    public static final String NM_GENDER = "MPB";
//
//    public static final List<Music> MUSICS = new ArrayList<>();
//
//    public static final long CD_USER = 1L;
//
//    @Autowired
//    private BlockMusicServiceImpl blockMusicServiceImpl;
//
//    @MockBean
//    private BlockMusicRequestDto blockMusicRequestDto;
//
//    @MockBean
//    private BlockMusicRepository blockMusicRepository;
//
//    @MockBean
//    private BlockMusicMapper blockMusicMapper;
//
//    private GenderResponseDto genderResponseDto;
//
//    private Optional<BlockMusic> genderOptional;
//    private BlockMusic blockMusic;
//
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        openMocks(this);
//        startGender();
//    }
//
//    @Test
//    void registerGender_success() {
//        when(blockMusicRepository.save(any())).thenReturn(blockMusic);
//        when(blockMusicMapper.toBlockMusicResponseDto(any())).thenReturn(genderResponseDto);
//
//        GenderResponseDto response = blockMusicServiceImpl.registerBlockMusic(blockMusicRequestDto);
//
//        verify(blockMusicRepository, times(1)).save(any());
//        verify(blockMusicMapper, times(1)).toBlockMusicResponseDto(any());
//        verify(blockMusicMapper, times(1)).toBlockMusicResponseDto(any(BlockMusic.class));
//        verify(blockMusicMapper, times(1)).toBlockMusic(any());
//        verify(blockMusicMapper, times(1)).toBlockMusicResponseDto(any());
//        verify(blockMusicMapper).toBlockMusicResponseDto(blockMusic);
//        verifyNoMoreInteractions(blockMusicMapper);
//
//        assertNotNull(response);
//        assertEquals(genderResponseDto, response);
//        assertEquals(CD_GENDER, response.getCdGender());
//        assertEquals(NM_GENDER, response.getNmGender());
//        assertEquals(MUSICS, response.getMusics());
//    }
//
//    @Test
//    void registerGender_failure() {
//        when(blockMusicRepository.findByNmGenderAndUserCdUser(anyString(), anyLong())).thenReturn(genderOptional);
//        when(blockMusicMapper.toBlockMusicResponseDto(any())).thenReturn(genderResponseDto);
//
//        BlockMusicRequestDto resquest = new BlockMusicRequestDto(CD_USER, NM_GENDER);
//
//        AlertException exception = assertThrows(AlertException.class, () -> {
//            blockMusicServiceImpl.registerBlockMusic(resquest);
//        });
//
//        verify(blockMusicRepository, times(1)).findByNmGenderAndUserCdUser(NM_GENDER, CD_USER);
//        verify(blockMusicMapper, never()).toBlockMusicResponseDto(any());
//
//        assertNotNull(exception.getMessage());
//        assertEquals("Gênero MPB já está cadastrado!", exception.getMessage());
//        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
//        assertEquals("warn", exception.getErrorCode());
//    }
//
//    @Test
//    void existingGender_success() {
//        when(blockMusicRepository.findByNmGenderAndUserCdUser(anyString(), anyLong())).thenReturn(Optional.empty());
//        when(blockMusicMapper.toBlockMusicResponseDto(any())).thenReturn(genderResponseDto);
//
//        blockMusicServiceImpl.existingBlockMusic(NM_GENDER, CD_USER);
//
//        verify(blockMusicRepository, times(1)).findByNmGenderAndUserCdUser(NM_GENDER, CD_USER);
//        verifyNoMoreInteractions(blockMusicRepository, blockMusicMapper);
//        verify(blockMusicRepository, times(1)).findByNmGenderAndUserCdUser(NM_GENDER, CD_USER);
//        verify(blockMusicMapper, never()).toBlockMusicResponseDto(any());
//        verifyNoMoreInteractions(blockMusicRepository);
//        verifyNoMoreInteractions(blockMusicMapper);
//    }
//
//    @Test
//    void existingGender_failure() {
//        when(blockMusicRepository.findByNmGenderAndUserCdUser(NM_GENDER, CD_USER)).thenReturn(genderOptional);
//        when(blockMusicMapper.toBlockMusicResponseDto(any())).thenReturn(genderResponseDto);
//
//        AlertException exception = assertThrows(AlertException.class, () -> {
//            blockMusicServiceImpl.existingBlockMusic(NM_GENDER, CD_USER);
//        });
//        verify(blockMusicRepository, times(1)).findByNmGenderAndUserCdUser(NM_GENDER, CD_USER);
//        verifyNoMoreInteractions(blockMusicRepository, blockMusicMapper);
//        verify(blockMusicRepository, times(1)).findByNmGenderAndUserCdUser(NM_GENDER, CD_USER);
//        verify(blockMusicMapper, never()).toBlockMusicResponseDto(any());
//        verifyNoMoreInteractions(blockMusicRepository);
//        verifyNoMoreInteractions(blockMusicMapper);
//
//        assertNotNull(exception.getMessage());
//        assertEquals("Gênero MPB já está cadastrado!", exception.getMessage());
//        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
//        assertEquals("warn", exception.getErrorCode());
//        assertThrows(AlertException.class, () -> blockMusicServiceImpl.existingBlockMusic(NM_GENDER, CD_USER));
//    }
//
//    @Test
//    void getAllGender_success() {
//        when(blockMusicRepository.findAll()).thenReturn(List.of(blockMusic));
//        when(blockMusicMapper.toBlockMusicResponseDto(any())).thenReturn(genderResponseDto);
//
//        List<GenderResponseDto> response = blockMusicServiceImpl.getAllBlockMusic(CD_USER);
//
//        verify(blockMusicRepository, times(1)).findAll();
//        verify(blockMusicMapper, times(1)).toBlockMusicResponseDto(any());
//        verifyNoMoreInteractions(blockMusicRepository, blockMusicMapper);
//
//        assertNotNull(response);
//        assertEquals(CD_GENDER, response.get(0).getCdGender());
//        assertEquals(NM_GENDER, response.get(0).getNmGender());
//        assertEquals(MUSICS, response.get(0).getMusics());
//    }
//
//    @Test
//    void getAllGender_failure() {
//        when(blockMusicRepository.findAll()).thenReturn(List.of());
//        when(blockMusicMapper.toBlockMusicResponseDto(any())).thenReturn(genderResponseDto);
//
//
//        AlertException exception = assertThrows(AlertException.class, () -> {
//            blockMusicServiceImpl.getAllBlockMusic(CD_USER);
//        });
//
//        verify(blockMusicRepository, times(1)).findAll();
//        verify(blockMusicMapper, never()).toBlockMusicResponseDto(any());
//        verify(blockMusicRepository).findAll();
//        verify(blockMusicMapper, never()).toBlockMusicResponseDto(any());
//
//        assertNotNull(exception.getMessage());
//        assertEquals("Nenhum gênero encontrado!", exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//        assertEquals("warn", exception.getErrorCode());
//        assertThrows(AlertException.class, () -> blockMusicServiceImpl.getAllBlockMusic(CD_USER));
//    }
//
//    @Test
//    void validateListGender_success() {
//        when(blockMusicRepository.findAll()).thenReturn(List.of(blockMusic));
//        when(blockMusicMapper.toBlockMusicResponseDto(any())).thenReturn(genderResponseDto);
//
//        List<BlockMusic> response = blockMusicServiceImpl.validateListBlockMusic(CD_USER);
//
//        verify(blockMusicRepository, times(1)).findAll();
//        verifyNoMoreInteractions(blockMusicRepository, blockMusicMapper);
//
//        assertNotNull(response);
//        assertEquals(CD_GENDER, response.get(0).getCdGender());
//        assertEquals(NM_GENDER, response.get(0).getNmGender());
//        assertEquals(MUSICS, response.get(0).getMusics());
//    }
//
//    @Test
//    void validateListGender_failure() {
//        when(blockMusicRepository.findAll()).thenReturn(List.of());
//        when(blockMusicMapper.toBlockMusicResponseDto(any())).thenReturn(genderResponseDto);
//
//        AlertException exception = assertThrows(AlertException.class, () -> {
//            blockMusicServiceImpl.validateListBlockMusic(CD_USER);
//        });
//
//        verify(blockMusicRepository, times(1)).findAll();
//        verify(blockMusicMapper, never()).toBlockMusicResponseDto(any());
//        verify(blockMusicRepository).findAll();
//        verify(blockMusicMapper, never()).toBlockMusicResponseDto(any());
//        assertNotNull(exception.getMessage());
//        assertEquals("Nenhum gênero encontrado!", exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//        assertEquals("warn", exception.getErrorCode());
//        assertThrows(AlertException.class, () -> blockMusicServiceImpl.getAllBlockMusic(CD_USER));
//    }
//
//    @Test
//    void findByIdGender_success() {
//        when(blockMusicRepository.findById(anyLong())).thenReturn(genderOptional);
//        when(blockMusicMapper.toBlockMusicResponseDto(any())).thenReturn(genderResponseDto);
//        when(blockMusicMapper.toBlockMusic(any())).thenReturn(blockMusic);
//
//        GenderResponseDto result = blockMusicServiceImpl.getBlockMusicByCdGender(CD_GENDER);
//
//        verify(blockMusicRepository, times(1)).findById(CD_GENDER);
//        verify(blockMusicMapper, times(1)).toBlockMusicResponseDto(blockMusic);
//        verifyNoMoreInteractions(blockMusicRepository, blockMusicMapper);
//        verifyNoMoreInteractions(blockMusicMapper);
//        verify(blockMusicRepository).findById(CD_GENDER);
//        verify(blockMusicMapper).toBlockMusicResponseDto(blockMusic);
//
//        assertNotNull(result);
//        assertEquals(genderResponseDto, result);
//        assertEquals(GenderResponseDto.class, result.getClass());
//        assertEquals(CD_GENDER, result.getCdGender());
//        assertEquals(NM_GENDER, result.getNmGender());
//        assertEquals(MUSICS, result.getMusics());
//        assertDoesNotThrow(() -> blockMusicServiceImpl.getBlockMusicByCdGender(CD_GENDER));
//    }
//
//    @Test
//    void findByIdGender_failure() {
//        when(blockMusicRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        AlertException exception = assertThrows(AlertException.class, () -> {
//            blockMusicServiceImpl.getBlockMusicByCdGender(CD_GENDER);
//        });
//
//        verify(blockMusicRepository, times(1)).findById(CD_GENDER);
//        verify(blockMusicMapper, never()).toBlockMusicResponseDto(any());
//        verify(blockMusicRepository).findById(CD_GENDER);
//        verify(blockMusicMapper, never()).toBlockMusicResponseDto(any());
//
//        assertNotNull(exception.getMessage());
//        assertEquals("Gênero com id 1 não cadastrado!", exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//        assertEquals("warn", exception.getErrorCode());
//        assertThrows(AlertException.class, () -> blockMusicServiceImpl.getBlockMusicByCdGender(CD_GENDER));
//    }
//
//    @Test
//    void validateGender_success() {
//        when(blockMusicRepository.findById(anyLong())).thenReturn(genderOptional);
//        when(blockMusicMapper.toBlockMusicResponseDto(any())).thenReturn(genderResponseDto);
//        when(blockMusicMapper.toBlockMusic(any())).thenReturn(blockMusic);
//
//        BlockMusic result = blockMusicServiceImpl.validateBlockMusic(CD_GENDER);
//
//        verify(blockMusicRepository, times(1)).findById(CD_GENDER);
//        verifyNoMoreInteractions(blockMusicRepository, blockMusicMapper);
//        verify(blockMusicMapper, never()).toBlockMusicResponseDto(any());
//        verify(blockMusicRepository).findById(CD_GENDER);
//
//        assertNotNull(result);
//        assertEquals(blockMusic, result);
//        assertEquals(CD_GENDER, result.getCdGender());
//        assertEquals(NM_GENDER, result.getNmGender());
//        assertEquals(MUSICS, result.getMusics());
//        assertDoesNotThrow(() -> blockMusicServiceImpl.getBlockMusicByCdGender(CD_GENDER));
//        assertDoesNotThrow(() -> blockMusicServiceImpl.validateBlockMusic(CD_GENDER));
//    }
//
//    @Test
//    void validateGender_failure() {
//        when(blockMusicRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        AlertException exception = assertThrows(AlertException.class, () -> {
//            blockMusicServiceImpl.validateBlockMusic(CD_GENDER);
//        });
//
//        verify(blockMusicRepository, times(1)).findById(CD_GENDER);
//        verify(blockMusicMapper, never()).toBlockMusicResponseDto(any());
//        verify(blockMusicRepository).findById(CD_GENDER);
//        verify(blockMusicMapper, never()).toBlockMusicResponseDto(any());
//
//        assertNotNull(exception.getMessage());
//        assertEquals("Gênero com id 1 não cadastrado!", exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//        assertEquals("warn", exception.getErrorCode());
//        assertThrows(AlertException.class, () -> blockMusicServiceImpl.validateBlockMusic(CD_GENDER));
//    }
//
//    @Test
//    void updateGender_success() {
//        when(blockMusicRepository.save(any())).thenReturn(blockMusic);
//        when(blockMusicMapper.toBlockMusicResponseDto(any())).thenReturn(genderResponseDto);
//        when(blockMusicRepository.findById(anyLong())).thenReturn(genderOptional);
//
//        GenderResponseDto result = blockMusicServiceImpl.updateBlockMusic(CD_GENDER, blockMusicRequestDto);
//
//        verify(blockMusicRepository, times(1)).findById(CD_GENDER);
//        verify(blockMusicRepository, times(1)).save(any());
//        verify(blockMusicMapper, times(1)).toBlockMusicResponseDto(any(BlockMusic.class));
//        verifyNoMoreInteractions(blockMusicRepository, blockMusicMapper);
//
//        assertNotNull(result);
//        assertEquals(NM_GENDER, result.getNmGender());
//    }
//
//    @Test
//    void updateGender_failure() {
//        when(blockMusicRepository.findById(anyLong())).thenReturn(Optional.empty());
//        when(blockMusicMapper.toBlockMusicResponseDto(any())).thenReturn(genderResponseDto);
//
//        AlertException exception = assertThrows(AlertException.class, () -> {
//            blockMusicServiceImpl.updateBlockMusic(CD_GENDER, blockMusicRequestDto);
//        });
//
//        verify(blockMusicRepository, times(1)).findById(CD_GENDER);
//        verify(blockMusicMapper, never()).toBlockMusicResponseDto(any(BlockMusic.class));
//        verifyNoMoreInteractions(blockMusicRepository, blockMusicMapper);
//
//        assertNotNull(exception.getMessage());
//        assertEquals("Gênero com id 1 não cadastrado!", exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//        assertEquals("warn", exception.getErrorCode());
//        assertThrows(AlertException.class, () -> blockMusicServiceImpl.updateBlockMusic(CD_GENDER, blockMusicRequestDto));
//    }
//
//    @Test
//    void deleteGender_success() {
//        when(blockMusicRepository.findById(anyLong())).thenReturn(genderOptional);
//
//        String result = blockMusicServiceImpl.deleteBlockMusic(CD_GENDER);
//
//        assertNotNull(result);
//        assertEquals("Genêro com ID 1 excluído com sucesso!", result);
//        assertEquals(String.class, result.getClass());
//    }
//
//    @Test
//    void deleteGender_failure() {
//        when(blockMusicRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        AlertException exception = assertThrows(AlertException.class, () -> {
//            blockMusicServiceImpl.deleteBlockMusic(CD_GENDER);
//        });
//
//        assertNotNull(exception.getMessage());
//        assertEquals("Gênero com id 1 não cadastrado!", exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//        assertEquals("warn", exception.getErrorCode());
//        assertThrows(AlertException.class, () -> blockMusicServiceImpl.validateBlockMusic(CD_GENDER));
//    }
//
//    private void startGender() {
//        blockMusic = new BlockMusic(CD_GENDER, NM_GENDER, MUSICS, user);
//        genderResponseDto = new GenderResponseDto(CD_GENDER,CD_USER, NM_GENDER, MUSICS );
//        genderOptional = Optional.of(new BlockMusic(CD_GENDER, NM_GENDER, MUSICS, user));
//    }
//}
