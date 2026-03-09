package com.crud.demo.service;

import com.crud.demo.dto.UserRequestDTO;
import com.crud.demo.dto.UserResponseDTO;
import com.crud.demo.infra.ResourceNotFoundException;
import com.crud.demo.model.User;
import com.crud.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<UserResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getDateCreated()))
                .toList();
    }

    public UserResponseDTO findById(long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return toResponseDTO(user);
    }

    public UserResponseDTO insert(UserRequestDTO dto) {
        User user = new User();

        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        user = repository.save(user);
        return toResponseDTO(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public UserResponseDTO update(long id, UserRequestDTO dto) {
        User user = repository.getReferenceById(id);
        updateData(user, dto);
        user = repository.save(user);
        return toResponseDTO(user);
    }

    private void updateData(User entity, UserRequestDTO dto) {
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setEmail(dto.email());
    }

    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getDateCreated()
        );
    }
}
