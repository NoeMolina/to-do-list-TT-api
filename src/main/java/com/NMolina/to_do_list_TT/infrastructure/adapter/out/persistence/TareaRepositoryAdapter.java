package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.Task;
import com.NMolina.to_do_list_TT.domain.port.out.TaskRepositoryPort;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.CategoriaEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.EstatusEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.TareaEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.mapper.TareaPersistenceMapper;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository.CategoriaJpaRepository;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository.EstatusJpaRepository;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository.TareaJpaRepository;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository.UsuarioJpaRepository;

@Component
public class TareaRepositoryAdapter implements TaskRepositoryPort {

    private final TareaJpaRepository tareaJpaRepository;
    private final CategoriaJpaRepository categoriaJpaRepository;
    private final EstatusJpaRepository estatusJpaRepository;
    private final UsuarioJpaRepository usuarioJpaRepository;
    private final TareaPersistenceMapper mapper;

    public TareaRepositoryAdapter(TareaJpaRepository tareaJpaRepository,
            CategoriaJpaRepository categoriaJpaRepository,
            EstatusJpaRepository estatusJpaRepository,
            UsuarioJpaRepository usuarioJpaRepository,
            TareaPersistenceMapper mapper) {
        this.tareaJpaRepository = tareaJpaRepository;
        this.categoriaJpaRepository = categoriaJpaRepository;
        this.estatusJpaRepository = estatusJpaRepository;
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Task save(Task task) {
        CategoriaEntity categoria = categoriaJpaRepository.findById(task.getCategory().getId())
                .orElseThrow(() -> new IllegalStateException("Categoría no encontrada"));
        EstatusEntity estatus = estatusJpaRepository.findById(task.getStatus().getId())
                .orElseThrow(() -> new IllegalStateException("Estatus no encontrado"));
        UsuarioEntity usuario = usuarioJpaRepository.findById(task.getUserId())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        TareaEntity entity = mapper.toEntity(task, categoria, estatus, usuario);
        TareaEntity saved = tareaJpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return tareaJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        return tareaJpaRepository.findAllByUsuarioId(userId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        tareaJpaRepository.deleteById(id);
    }
}
