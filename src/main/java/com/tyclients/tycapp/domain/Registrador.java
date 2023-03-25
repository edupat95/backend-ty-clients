package com.tyclients.tycapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Registrador.
 */
@Entity
@Table(name = "registrador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Registrador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @JsonIgnoreProperties(value = { "user", "club", "adminClub" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Trabajador trabajador;

    @OneToMany(mappedBy = "registrador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "asociado", "club", "registrador" }, allowSetters = true)
    private Set<AsociadoClub> asociadoClubs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Registrador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public Registrador estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Registrador createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Registrador updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Trabajador getTrabajador() {
        return this.trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Registrador trabajador(Trabajador trabajador) {
        this.setTrabajador(trabajador);
        return this;
    }

    public Set<AsociadoClub> getAsociadoClubs() {
        return this.asociadoClubs;
    }

    public void setAsociadoClubs(Set<AsociadoClub> asociadoClubs) {
        if (this.asociadoClubs != null) {
            this.asociadoClubs.forEach(i -> i.setRegistrador(null));
        }
        if (asociadoClubs != null) {
            asociadoClubs.forEach(i -> i.setRegistrador(this));
        }
        this.asociadoClubs = asociadoClubs;
    }

    public Registrador asociadoClubs(Set<AsociadoClub> asociadoClubs) {
        this.setAsociadoClubs(asociadoClubs);
        return this;
    }

    public Registrador addAsociadoClub(AsociadoClub asociadoClub) {
        this.asociadoClubs.add(asociadoClub);
        asociadoClub.setRegistrador(this);
        return this;
    }

    public Registrador removeAsociadoClub(AsociadoClub asociadoClub) {
        this.asociadoClubs.remove(asociadoClub);
        asociadoClub.setRegistrador(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Registrador)) {
            return false;
        }
        return id != null && id.equals(((Registrador) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Registrador{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
