package com.tyclients.tycapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A AsociadoClub.
 */
@Entity
@Table(name = "asociado_club")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AsociadoClub implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "identificador", length = 36, nullable = false)
    private UUID identificador;

    @Column(name = "fecha_asociacion")
    private Instant fechaAsociacion;

    @Column(name = "puntos_club")
    private Long puntosClub;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "documento", "ventas", "asociadoClubs" }, allowSetters = true)
    private Asociado asociado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "adminClub",
            "planContratado",
            "tipoProductos",
            "formaPagos",
            "cajas",
            "depositos",
            "trabajadors",
            "asociadoClubs",
            "eventos",
            "productos",
            "mesas",
        },
        allowSetters = true
    )
    private Club club;

    @ManyToOne
    @JsonIgnoreProperties(value = { "trabajador", "asociadoClubs" }, allowSetters = true)
    private Registrador registrador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AsociadoClub id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getIdentificador() {
        return this.identificador;
    }

    public AsociadoClub identificador(UUID identificador) {
        this.setIdentificador(identificador);
        return this;
    }

    public void setIdentificador(UUID identificador) {
        this.identificador = identificador;
    }

    public Instant getFechaAsociacion() {
        return this.fechaAsociacion;
    }

    public AsociadoClub fechaAsociacion(Instant fechaAsociacion) {
        this.setFechaAsociacion(fechaAsociacion);
        return this;
    }

    public void setFechaAsociacion(Instant fechaAsociacion) {
        this.fechaAsociacion = fechaAsociacion;
    }

    public Long getPuntosClub() {
        return this.puntosClub;
    }

    public AsociadoClub puntosClub(Long puntosClub) {
        this.setPuntosClub(puntosClub);
        return this;
    }

    public void setPuntosClub(Long puntosClub) {
        this.puntosClub = puntosClub;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public AsociadoClub estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public AsociadoClub createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public AsociadoClub updatedDate(Instant updatedDate) {
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

    public AsociadoClub asociado(Asociado asociado) {
        this.setAsociado(asociado);
        return this;
    }

    public Club getClub() {
        return this.club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public AsociadoClub club(Club club) {
        this.setClub(club);
        return this;
    }

    public Registrador getRegistrador() {
        return this.registrador;
    }

    public void setRegistrador(Registrador registrador) {
        this.registrador = registrador;
    }

    public AsociadoClub registrador(Registrador registrador) {
        this.setRegistrador(registrador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AsociadoClub)) {
            return false;
        }
        return id != null && id.equals(((AsociadoClub) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AsociadoClub{" +
            "id=" + getId() +
            ", identificador='" + getIdentificador() + "'" +
            ", fechaAsociacion='" + getFechaAsociacion() + "'" +
            ", puntosClub=" + getPuntosClub() +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
