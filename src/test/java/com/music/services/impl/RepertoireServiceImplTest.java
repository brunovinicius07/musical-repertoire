package com.music.services.impl;

import com.music.model.dto.request.RepertoireRequestDto;
import com.music.model.dto.response.RepertoireResponseDto;
import com.music.model.entity.BlockMusic;
import com.music.model.entity.Repertoire;
import com.music.model.entity.User;
import com.music.model.exceptions.repertoire.RepertoireIsPresentException;
import com.music.model.exceptions.repertoire.RepertoireNotFoundException;
import com.music.model.mapper.RepertoireMapper;
import com.music.repositories.BlockMusicRepository;
import com.music.repositories.RepertoireRepository;
import com.music.role.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RepertoireServiceImplTest {

    @Mock
    private RepertoireRepository repertoireRepository;

    @Mock
    private RepertoireMapper repertoireMapper;

    @Mock
    private AuthenticationServiceImpl authenticationServiceImpl;

    @Mock
    private BlockMusicRepository blockMusicRepository;

    @InjectMocks
    private RepertoireServiceImpl repertoireService;

    private RepertoireRequestDto requestDto;
    private Repertoire repertoire;
    private RepertoireResponseDto responseDto;
    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .idUser(1L)
                .nameUser("User71")
                .email("user71@email.com")
                .password("12345678")
                .role(UserRole.ADMIN)
                .build();

        repertoire = Repertoire.builder()
                .idRepertoire(1L)
                .nameRepertoire("Repertório Casamento")
                .user(user)
                .build();

        requestDto = new RepertoireRequestDto();
        requestDto.setNameRepertoire("Repertório Casamento");
        requestDto.setIdUser(1L);

        responseDto = new RepertoireResponseDto(1L, "Repertório Casamento",
                List.of(), 1L);
    }

    @Test
    void shouldCreateRepertoireSuccessfully() {
        when(repertoireRepository.findRepertoireByNameRepertoireAndUserIdUser(anyString(), anyLong()))
                .thenReturn(Optional.empty());
        when(repertoireMapper.toRepertoire(any())).thenReturn(repertoire);
        when(authenticationServiceImpl.validateUserById(1L)).thenReturn(user);
        when(repertoireRepository.save(any())).thenReturn(repertoire);
        when(repertoireMapper.toRepertoireResponseDto(any())).thenReturn(responseDto);

        RepertoireResponseDto result = repertoireService.createRepertoire(requestDto);

        assertNotNull(result);
        assertEquals("Repertório Casamento", result.getNameRepertoire());
        verify(repertoireRepository).save(repertoire);
    }

    @Test
    void shouldThrowException_WhenRepertoireAlreadyExists() {
        when(repertoireRepository.findRepertoireByNameRepertoireAndUserIdUser(anyString(), anyLong()))
                .thenReturn(Optional.of(repertoire));

        assertThrows(RepertoireIsPresentException.class, () ->
                repertoireService.createRepertoire(requestDto));
    }

    @Test
    void shouldReturnAllRepertoiresByUser() {
        when(repertoireRepository.findAllRepertoireByUserIdUser(1L))
                .thenReturn(List.of(repertoire));
        when(repertoireMapper.toListRepertoireResponseDto(anyList()))
                .thenReturn(List.of(responseDto));

        List<RepertoireResponseDto> result = repertoireService.getAllRepertoireByIdUser(1L);

        assertEquals(1, result.size());
        assertEquals("Repertório Casamento", result.get(0).getNameRepertoire());
    }

    @Test
    void shouldThrowException_WhenUserHasNoRepertoire() {
        when(repertoireRepository.findAllRepertoireByUserIdUser(1L))
                .thenReturn(List.of());

        assertThrows(RepertoireNotFoundException.class, () ->
                repertoireService.getAllRepertoireByIdUser(1L));
    }

    @Test
    void shouldReturnRepertoireById() {
        when(repertoireRepository.findById(1L)).thenReturn(Optional.of(repertoire));
        when(repertoireMapper.toRepertoireResponseDto(any())).thenReturn(responseDto);

        RepertoireResponseDto result = repertoireService.getRepertoireByIdRepertoire(1L);

        assertNotNull(result);
        assertEquals("Repertório Casamento", result.getNameRepertoire());
    }

    @Test
    void shouldThrowException_WhenRepertoireNotFoundById() {
        when(repertoireRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RepertoireNotFoundException.class, () ->
                repertoireService.getRepertoireByIdRepertoire(1L));
    }

    @Test
    void shouldUpdateRepertoireSuccessfully() {
        when(repertoireRepository.findRepertoireByNameRepertoireAndUserIdUser(anyString(), anyLong()))
                .thenReturn(Optional.empty());
        when(repertoireRepository.findById(1L)).thenReturn(Optional.of(repertoire));
        when(repertoireRepository.save(any())).thenReturn(repertoire);
        when(repertoireMapper.toRepertoireResponseDto(any())).thenReturn(responseDto);

        RepertoireResponseDto result = repertoireService.updateRepertoire(1L, requestDto);

        assertNotNull(result);
        assertEquals("Repertório Casamento", result.getNameRepertoire());
    }

    @Test
    void shouldUpdateRepertoire_WhenNameIsNotNull() {
        // Arrange
        RepertoireRequestDto updateDto = new RepertoireRequestDto("Novo Repertório", 1L);

        Repertoire existing = Repertoire.builder()
                .idRepertoire(1L)
                .nameRepertoire("Antigo Repertório")
                .user(user)
                .build();

        when(repertoireRepository.findRepertoireByNameRepertoireAndUserIdUser(anyString(), anyLong()))
                .thenReturn(Optional.empty());
        when(repertoireRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(repertoireRepository.save(existing)).thenReturn(existing);
        when(repertoireMapper.toRepertoireResponseDto(existing))
                .thenReturn(new RepertoireResponseDto(1L, "Novo Repertório", List.of(),
                        1L));

        // Act
        RepertoireResponseDto result = repertoireService.updateRepertoire(1L, updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("Novo Repertório", existing.getNameRepertoire());
        verify(repertoireRepository).save(existing);
    }

    @Test
    void shouldUpdateRepertoire_WhenNameIsNull() {
        RepertoireRequestDto updateDto = new RepertoireRequestDto(null, 1L);

        Repertoire existing = Repertoire.builder()
                .idRepertoire(1L)
                .nameRepertoire("Repertório Original")
                .user(user)
                .build();

        when(repertoireRepository.findRepertoireByNameRepertoireAndUserIdUser(any(), anyLong()))
                .thenReturn(Optional.empty());
        when(repertoireRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(repertoireRepository.save(existing)).thenReturn(existing);
        when(repertoireMapper.toRepertoireResponseDto(existing))
                .thenReturn(new RepertoireResponseDto(1L, "Repertório Original", List.of(),
                        1L));

        RepertoireResponseDto result = repertoireService.updateRepertoire(1L, updateDto);
        assertNotNull(result);

        assertEquals("Repertório Original", existing.getNameRepertoire());
        verify(repertoireRepository).save(existing);
    }


    @Test
    void shouldDeleteRepertoireWithoutBlocks() {
        when(repertoireRepository.findById(1L)).thenReturn(Optional.of(repertoire));

        String result = repertoireService.deleteRepertoire(1L);

        assertEquals("Repertorio com id 1excluído com sucesso", result);
        verify(repertoireRepository).delete(repertoire);
    }

    @Test
    void shouldDeleteRepertoireWithBlocks() {
        BlockMusic block = new BlockMusic();
        repertoire.setBlockMusics(List.of(block));

        when(repertoireRepository.findById(1L)).thenReturn(Optional.of(repertoire));
        when(blockMusicRepository.findAllBlockMusicByRepertoireIdRepertoire(1L))
                .thenReturn(List.of(block));

        repertoireService.deleteRepertoire(1L);

        verify(blockMusicRepository).delete(block);
        verify(repertoireRepository).delete(repertoire);
    }

    @Test
    void shouldDeleteRepertoireWithEmptyBlockMusicList() {
        Repertoire repertoire = new Repertoire();
        repertoire.setIdRepertoire(1L);
        repertoire.setBlockMusics(Collections.emptyList());

        when(repertoireRepository.findById(1L)).thenReturn(Optional.of(repertoire));

        String result = repertoireService.deleteRepertoire(1L);

        verify(blockMusicRepository, never()).findAllBlockMusicByRepertoireIdRepertoire(anyLong());
        verify(repertoireRepository).delete(repertoire);
        assertEquals("Repertorio com id 1excluído com sucesso", result);
    }
}
