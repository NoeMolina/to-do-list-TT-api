package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "subtareas")
public class SubtareaEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarea_id", nullable = false)
    private TareaEntity tarea;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estatus_id", nullable = false)
    private EstatusEntity estatus;

    protected SubtareaEntity() {
    }

    public SubtareaEntity(Long id, TareaEntity tarea, String titulo, String descripcion, EstatusEntity estatus) {
        this.id = id;
        this.tarea = tarea;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estatus = estatus;
    }

    public Long getId() {
        return id;
    }

    public TareaEntity getTarea() {
        return tarea;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public EstatusEntity getEstatus() {
        return estatus;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstatus(EstatusEntity estatus) {
        this.estatus = estatus;
    }
}