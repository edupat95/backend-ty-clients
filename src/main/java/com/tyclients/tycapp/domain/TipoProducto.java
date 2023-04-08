package com.tyclients.tycapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TipoProducto.
 */
@Entity
@Table(name = "tipo_producto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipoProducto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "tipoProducto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "productoVentas", "productoCajas", "productoDepositos", "productoMesas", "club", "tipoProducto" },
        allowSetters = true
    )
    private Set<Producto> productos = new HashSet<>();

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TipoProducto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public TipoProducto estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getName() {
        return this.name;
    }

    public TipoProducto name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(Set<Producto> productos) {
        if (this.productos != null) {
            this.productos.forEach(i -> i.setTipoProducto(null));
        }
        if (productos != null) {
            productos.forEach(i -> i.setTipoProducto(this));
        }
        this.productos = productos;
    }

    public TipoProducto productos(Set<Producto> productos) {
        this.setProductos(productos);
        return this;
    }

    public TipoProducto addProducto(Producto producto) {
        this.productos.add(producto);
        producto.setTipoProducto(this);
        return this;
    }

    public TipoProducto removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.setTipoProducto(null);
        return this;
    }

    public Club getClub() {
        return this.club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public TipoProducto club(Club club) {
        this.setClub(club);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoProducto)) {
            return false;
        }
        return id != null && id.equals(((TipoProducto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoProducto{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
