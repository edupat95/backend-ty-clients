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
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "costo_total", nullable = false)
    private Long costoTotal;

    @NotNull
    @Column(name = "costo_total_puntos", nullable = false)
    private Long costoTotalPuntos;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "identificador_ticket", length = 36, nullable = false)
    private UUID identificadorTicket;

    @NotNull
    @Column(name = "entregado", nullable = false)
    private Boolean entregado;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @OneToMany(mappedBy = "venta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "venta", "producto" }, allowSetters = true)
    private Set<ProductoVenta> productoVentas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "trabajador", "ventas", "caja" }, allowSetters = true)
    private Cajero cajero;

    @ManyToOne
    @JsonIgnoreProperties(value = { "documento", "ventas", "asociadoClubs" }, allowSetters = true)
    private Asociado asociado;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ventas", "club" }, allowSetters = true)
    private FormaPago formaPago;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Venta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCostoTotal() {
        return this.costoTotal;
    }

    public Venta costoTotal(Long costoTotal) {
        this.setCostoTotal(costoTotal);
        return this;
    }

    public void setCostoTotal(Long costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Long getCostoTotalPuntos() {
        return this.costoTotalPuntos;
    }

    public Venta costoTotalPuntos(Long costoTotalPuntos) {
        this.setCostoTotalPuntos(costoTotalPuntos);
        return this;
    }

    public void setCostoTotalPuntos(Long costoTotalPuntos) {
        this.costoTotalPuntos = costoTotalPuntos;
    }

    public UUID getIdentificadorTicket() {
        return this.identificadorTicket;
    }

    public Venta identificadorTicket(UUID identificadorTicket) {
        this.setIdentificadorTicket(identificadorTicket);
        return this;
    }

    public void setIdentificadorTicket(UUID identificadorTicket) {
        this.identificadorTicket = identificadorTicket;
    }

    public Boolean getEntregado() {
        return this.entregado;
    }

    public Venta entregado(Boolean entregado) {
        this.setEntregado(entregado);
        return this;
    }

    public void setEntregado(Boolean entregado) {
        this.entregado = entregado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Venta createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Venta updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<ProductoVenta> getProductoVentas() {
        return this.productoVentas;
    }

    public void setProductoVentas(Set<ProductoVenta> productoVentas) {
        if (this.productoVentas != null) {
            this.productoVentas.forEach(i -> i.setVenta(null));
        }
        if (productoVentas != null) {
            productoVentas.forEach(i -> i.setVenta(this));
        }
        this.productoVentas = productoVentas;
    }

    public Venta productoVentas(Set<ProductoVenta> productoVentas) {
        this.setProductoVentas(productoVentas);
        return this;
    }

    public Venta addProductoVenta(ProductoVenta productoVenta) {
        this.productoVentas.add(productoVenta);
        productoVenta.setVenta(this);
        return this;
    }

    public Venta removeProductoVenta(ProductoVenta productoVenta) {
        this.productoVentas.remove(productoVenta);
        productoVenta.setVenta(null);
        return this;
    }

    public Cajero getCajero() {
        return this.cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public Venta cajero(Cajero cajero) {
        this.setCajero(cajero);
        return this;
    }

    public Asociado getAsociado() {
        return this.asociado;
    }

    public void setAsociado(Asociado asociado) {
        this.asociado = asociado;
    }

    public Venta asociado(Asociado asociado) {
        this.setAsociado(asociado);
        return this;
    }

    public FormaPago getFormaPago() {
        return this.formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Venta formaPago(FormaPago formaPago) {
        this.setFormaPago(formaPago);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venta)) {
            return false;
        }
        return id != null && id.equals(((Venta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", costoTotal=" + getCostoTotal() +
            ", costoTotalPuntos=" + getCostoTotalPuntos() +
            ", identificadorTicket='" + getIdentificadorTicket() + "'" +
            ", entregado='" + getEntregado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
