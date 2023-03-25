package com.tyclients.tycapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Asociado.
 */
@Entity
@Table(name = "asociado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Asociado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Type(type = "uuid-char")
    @Column(name = "identificador_general", length = 36)
    private UUID identificadorGeneral;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Documento documento;

    @OneToMany(mappedBy = "asociado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productoVentas", "cajero", "asociado", "formaPago" }, allowSetters = true)
    private Set<Venta> ventas = new HashSet<>();

    @OneToMany(mappedBy = "asociado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "asociado", "club", "registrador" }, allowSetters = true)
    private Set<AsociadoClub> asociadoClubs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Asociado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getIdentificadorGeneral() {
        return this.identificadorGeneral;
    }

    public Asociado identificadorGeneral(UUID identificadorGeneral) {
        this.setIdentificadorGeneral(identificadorGeneral);
        return this;
    }

    public void setIdentificadorGeneral(UUID identificadorGeneral) {
        this.identificadorGeneral = identificadorGeneral;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public Asociado estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Asociado createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Asociado updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Documento getDocumento() {
        return this.documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Asociado documento(Documento documento) {
        this.setDocumento(documento);
        return this;
    }

    public Set<Venta> getVentas() {
        return this.ventas;
    }

    public void setVentas(Set<Venta> ventas) {
        if (this.ventas != null) {
            this.ventas.forEach(i -> i.setAsociado(null));
        }
        if (ventas != null) {
            ventas.forEach(i -> i.setAsociado(this));
        }
        this.ventas = ventas;
    }

    public Asociado ventas(Set<Venta> ventas) {
        this.setVentas(ventas);
        return this;
    }

    public Asociado addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.setAsociado(this);
        return this;
    }

    public Asociado removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.setAsociado(null);
        return this;
    }

    public Set<AsociadoClub> getAsociadoClubs() {
        return this.asociadoClubs;
    }

    public void setAsociadoClubs(Set<AsociadoClub> asociadoClubs) {
        if (this.asociadoClubs != null) {
            this.asociadoClubs.forEach(i -> i.setAsociado(null));
        }
        if (asociadoClubs != null) {
            asociadoClubs.forEach(i -> i.setAsociado(this));
        }
        this.asociadoClubs = asociadoClubs;
    }

    public Asociado asociadoClubs(Set<AsociadoClub> asociadoClubs) {
        this.setAsociadoClubs(asociadoClubs);
        return this;
    }

    public Asociado addAsociadoClub(AsociadoClub asociadoClub) {
        this.asociadoClubs.add(asociadoClub);
        asociadoClub.setAsociado(this);
        return this;
    }

    public Asociado removeAsociadoClub(AsociadoClub asociadoClub) {
        this.asociadoClubs.remove(asociadoClub);
        asociadoClub.setAsociado(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asociado)) {
            return false;
        }
        return id != null && id.equals(((Asociado) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Asociado{" +
            "id=" + getId() +
            ", identificadorGeneral='" + getIdentificadorGeneral() + "'" +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
