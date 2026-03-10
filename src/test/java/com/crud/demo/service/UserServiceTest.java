package com.crud.demo.service;

import com.crud.demo.dto.UserRequestDTO;
import com.crud.demo.dto.UserResponseDTO;
import com.crud.demo.infra.ResourceNotFoundException;
import com.crud.demo.model.User;
import com.crud.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;

    private User userEntity;
    private UserRequestDTO userRequestDTO;

    @BeforeEach
    void setUp() {
        userEntity = new User();
        userEntity.setId(1L);
        userEntity.setFirstName("Walter");
        userEntity.setLastName("White");
        userEntity.setEmail("walter@example.com");

        userRequestDTO = new UserRequestDTO("Walter", "White", "walter@example.com", null);
    }

    @Test
    void should_return_all_users() {
        List<User> list = List.of(userEntity);
        when(repository.findAll()).thenReturn(list);

        List<UserResponseDTO> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());

        assertEquals(userEntity.getId(), result.get(0).id());
        assertEquals(userEntity.getFirstName(), result.get(0).firstName());
        assertEquals(userEntity.getLastName(), result.get(0).lastName());
        assertEquals(userEntity.getEmail(), result.get(0).email());

        verify(repository, times(1)).findAll();
    }

    @Test
    void should_find_by_id_when_id_exists() {
        when(repository.findById(1L)).thenReturn(Optional.of(userEntity));

        UserResponseDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Walter", result.firstName());
        assertEquals("White", result.lastName());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void should_return_exception_when_id_is_not_found() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(2L));

        verify(repository, times(1)).findById(2L);
    }

    @Test
    void should_insert_an_user_and_return_user_response_dto() {
        when(repository.save(any(User.class))).thenReturn(userEntity);

        UserResponseDTO result = service.insert(userRequestDTO);

        assertNotNull(result);
        assertEquals(userRequestDTO.firstName(), result.firstName());
        assertEquals(userRequestDTO.lastName(), result.lastName());
        assertEquals(userRequestDTO.email(), result.email());

        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void should_update_an_user_and_return_updated_data() {
        when(repository.getReferenceById(1L)).thenReturn(userEntity);

        when(repository.save(any(User.class))).thenReturn(userEntity);

        UserRequestDTO updateDto = new UserRequestDTO("Heisenberg", "White", "heisenberg@example.com", null);

        UserResponseDTO result = service.update(1L, updateDto);

        assertNotNull(result);

        assertEquals("Heisenberg", result.firstName());
        assertEquals("White", result.lastName());
        assertEquals("heisenberg@example.com", result.email());

        verify(repository, times(1)).getReferenceById(1L);
        verify(repository, times(1)).save(userEntity);
    }

    @Test
    void should_delete_an_user_by_id() {
        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
