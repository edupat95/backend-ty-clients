package com.tyclients.tycapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Acceso.
 */
@Entity
@Table(name = "acceso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Acceso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "costo_puntos")
    private Long costoPuntos;

    @Column(name = "fecha_canje")
    private Instant fechaCanje;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @JsonIgnoreProperties(value = { "documento", "ventas", "asociadoClubs" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Asociado asociado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "accesos", "adminClub", "club" }, allowSetters = true)
    private Evento evento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Acceso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCostoPuntos() {
        return this.costoPuntos;
    }

    public Acceso costoPuntos(Long costoPuntos) {
        this.setCostoPuntos(costoPuntos);
        return this;
    }

    public void setCostoPuntos(Long costoPuntos) {
        this.costoPuntos = costoPuntos;
    }

    public Instant getFechaCanje() {
        return this.fechaCanje;
    }

    public Acceso fechaCanje(Instant fechaCanje) {
        this.setFechaCanje(fechaCanje);
        return this;
    }

    public void setFechaCanje(Instant fechaCanje) {
        this.fechaCanje = fechaCanje;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public Acceso estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Acceso createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Acceso updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Asociado getAsociado() {
        return this.asociado;
    }

    public void setAsociado(Asociado asociado) {
        this.asociado = asociado;
    }

    public Acceso asociado(Asociado asociado) {
        this.setAsociado(asociado);
        return this;
    }

    public Evento getEvento() {
        return this.evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Acceso evento(Evento evento) {
        this.setEvento(evento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Acceso)) {
            return false;
        }
        return id != null && id.equals(((Acceso) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Acceso{" +
            "id=" + getId() +
            ", costoPuntos=" + getCostoPuntos() +
            ", fechaCanje='" + getFechaCanje() + "'" +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
