import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/admin-club">
        <Translate contentKey="global.menu.entities.adminClub" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cliente">
        <Translate contentKey="global.menu.entities.cliente" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/registrador">
        <Translate contentKey="global.menu.entities.registrador" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/trabajador">
        <Translate contentKey="global.menu.entities.trabajador" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cajero">
        <Translate contentKey="global.menu.entities.cajero" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/caja">
        <Translate contentKey="global.menu.entities.caja" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/producto">
        <Translate contentKey="global.menu.entities.producto" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/venta">
        <Translate contentKey="global.menu.entities.venta" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/producto-venta">
        <Translate contentKey="global.menu.entities.productoVenta" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/producto-caja">
        <Translate contentKey="global.menu.entities.productoCaja" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/producto-deposito">
        <Translate contentKey="global.menu.entities.productoDeposito" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/club">
        <Translate contentKey="global.menu.entities.club" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/asociado">
        <Translate contentKey="global.menu.entities.asociado" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/asociado-club">
        <Translate contentKey="global.menu.entities.asociadoClub" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/evento">
        <Translate contentKey="global.menu.entities.evento" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/deposito">
        <Translate contentKey="global.menu.entities.deposito" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/documento">
        <Translate contentKey="global.menu.entities.documento" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/acceso">
        <Translate contentKey="global.menu.entities.acceso" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/forma-pago">
        <Translate contentKey="global.menu.entities.formaPago" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tipo-producto">
        <Translate contentKey="global.menu.entities.tipoProducto" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/mesa">
        <Translate contentKey="global.menu.entities.mesa" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/producto-mesa">
        <Translate contentKey="global.menu.entities.productoMesa" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/entregador">
        <Translate contentKey="global.menu.entities.entregador" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/plan">
        <Translate contentKey="global.menu.entities.plan" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/plan-contratado">
        <Translate contentKey="global.menu.entities.planContratado" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
