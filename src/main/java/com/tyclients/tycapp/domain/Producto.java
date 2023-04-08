package com.tyclients.tycapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Task entity.\n@author The JHipster team.
 */
@Schema(description = "Task entity.\n@author The JHipster team.")
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "precio", nullable = false)
    private Long precio;

    @Column(name = "costo")
    private Long costo;

    @NotNull
    @Column(name = "precio_puntos", nullable = false)
    private Long precioPuntos;

    @NotNull
    @Column(name = "puntos_recompensa", nullable = false)
    private Long puntosRecompensa;

    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @OneToMany(mappedBy = "producto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "venta", "producto" }, allowSetters = true)
    private Set<ProductoVenta> productoVentas = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "producto", "caja" }, allowSetters = true)
    private Set<ProductoCaja> productoCajas = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deposito", "producto" }, allowSetters = true)
    private Set<ProductoDeposito> productoDepositos = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mesa", "producto" }, allowSetters = true)
    private Set<ProductoMesa> productoMesas = new HashSet<>();

    @ManyToOne
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
    @JsonIgnoreProperties(value = { "productos", "club" }, allowSetters = true)
    private TipoProducto tipoProducto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Producto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Producto nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPrecio() {
        return this.precio;
    }

    public Producto precio(Long precio) {
        this.setPrecio(precio);
        return this;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public Long getCosto() {
        return this.costo;
    }

    public Producto costo(Long costo) {
        this.setCosto(costo);
        return this;
    }

    public void setCosto(Long costo) {
        this.costo = costo;
    }

    public Long getPrecioPuntos() {
        return this.precioPuntos;
    }

    public Producto precioPuntos(Long precioPuntos) {
        this.setPrecioPuntos(precioPuntos);
        return this;
    }

    public void setPrecioPuntos(Long precioPuntos) {
        this.precioPuntos = precioPuntos;
    }

    public Long getPuntosRecompensa() {
        return this.puntosRecompensa;
    }

    public Producto puntosRecompensa(Long puntosRecompensa) {
        this.setPuntosRecompensa(puntosRecompensa);
        return this;
    }

    public void setPuntosRecompensa(Long puntosRecompensa) {
        this.puntosRecompensa = puntosRecompensa;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public Producto estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Producto createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Producto updatedDate(Instant updatedDate) {
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
            this.productoVentas.forEach(i -> i.setProducto(null));
        }
        if (productoVentas != null) {
            productoVentas.forEach(i -> i.setProducto(this));
        }
        this.productoVentas = productoVentas;
    }

    public Producto productoVentas(Set<ProductoVenta> productoVentas) {
        this.setProductoVentas(productoVentas);
        return this;
    }

    public Producto addProductoVenta(ProductoVenta productoVenta) {
        this.productoVentas.add(productoVenta);
        productoVenta.setProducto(this);
        return this;
    }

    public Producto removeProductoVenta(ProductoVenta productoVenta) {
        this.productoVentas.remove(productoVenta);
        productoVenta.setProducto(null);
        return this;
    }

    public Set<ProductoCaja> getProductoCajas() {
        return this.productoCajas;
    }

    public void setProductoCajas(Set<ProductoCaja> productoCajas) {
        if (this.productoCajas != null) {
            this.productoCajas.forEach(i -> i.setProducto(null));
        }
        if (productoCajas != null) {
            productoCajas.forEach(i -> i.setProducto(this));
        }
        this.productoCajas = productoCajas;
    }

    public Producto productoCajas(Set<ProductoCaja> productoCajas) {
        this.setProductoCajas(productoCajas);
        return this;
    }

    public Producto addProductoCaja(ProductoCaja productoCaja) {
        this.productoCajas.add(productoCaja);
        productoCaja.setProducto(this);
        return this;
    }

    public Producto removeProductoCaja(ProductoCaja productoCaja) {
        this.productoCajas.remove(productoCaja);
        productoCaja.setProducto(null);
        return this;
    }

    public Set<ProductoDeposito> getProductoDepositos() {
        return this.productoDepositos;
    }

    public void setProductoDepositos(Set<ProductoDeposito> productoDepositos) {
        if (this.productoDepositos != null) {
            this.productoDepositos.forEach(i -> i.setProducto(null));
        }
        if (productoDepositos != null) {
            productoDepositos.forEach(i -> i.setProducto(this));
        }
        this.productoDepositos = productoDepositos;
    }

    public Producto productoDepositos(Set<ProductoDeposito> productoDepositos) {
        this.setProductoDepositos(productoDepositos);
        return this;
    }

    public Producto addProductoDeposito(ProductoDeposito productoDeposito) {
        this.productoDepositos.add(productoDeposito);
        productoDeposito.setProducto(this);
        return this;
    }

    public Producto removeProductoDeposito(ProductoDeposito productoDeposito) {
        this.productoDepositos.remove(productoDeposito);
        productoDeposito.setProducto(null);
        return this;
    }

    public Set<ProductoMesa> getProductoMesas() {
        return this.productoMesas;
    }

    public void setProductoMesas(Set<ProductoMesa> productoMesas) {
        if (this.productoMesas != null) {
            this.productoMesas.forEach(i -> i.setProducto(null));
        }
        if (productoMesas != null) {
            productoMesas.forEach(i -> i.setProducto(this));
        }
        this.productoMesas = productoMesas;
    }

    public Producto productoMesas(Set<ProductoMesa> productoMesas) {
        this.setProductoMesas(productoMesas);
        return this;
    }

    public Producto addProductoMesa(ProductoMesa productoMesa) {
        this.productoMesas.add(productoMesa);
        productoMesa.setProducto(this);
        return this;
    }

    public Producto removeProductoMesa(ProductoMesa productoMesa) {
        this.productoMesas.remove(productoMesa);
        productoMesa.setProducto(null);
        return this;
    }

    public Club getClub() {
        return this.club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Producto club(Club club) {
        this.setClub(club);
        return this;
    }

    public TipoProducto getTipoProducto() {
        return this.tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Producto tipoProducto(TipoProducto tipoProducto) {
        this.setTipoProducto(tipoProducto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", precio=" + getPrecio() +
            ", costo=" + getCosto() +
            ", precioPuntos=" + getPrecioPuntos() +
            ", puntosRecompensa=" + getPuntosRecompensa() +
            ", descripcion='" + getDescripcion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
