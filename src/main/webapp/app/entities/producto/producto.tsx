import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProducto } from 'app/shared/model/producto.model';
import { getEntities, reset } from './producto.reducer';

export const Producto = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const productoList = useAppSelector(state => state.producto.entities);
  const loading = useAppSelector(state => state.producto.loading);
  const totalItems = useAppSelector(state => state.producto.totalItems);
  const links = useAppSelector(state => state.producto.links);
  const entity = useAppSelector(state => state.producto.entity);
  const updateSuccess = useAppSelector(state => state.producto.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="producto-heading" data-cy="ProductoHeading">
        <Translate contentKey="fidelizacion2App.producto.home.title">Productos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="fidelizacion2App.producto.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/producto/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="fidelizacion2App.producto.home.createLabel">Create new Producto</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={productoList ? productoList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {productoList && productoList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="fidelizacion2App.producto.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nombre')}>
                    <Translate contentKey="fidelizacion2App.producto.nombre">Nombre</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('precio')}>
                    <Translate contentKey="fidelizacion2App.producto.precio">Precio</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('costoPuntos')}>
                    <Translate contentKey="fidelizacion2App.producto.costoPuntos">Costo Puntos</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('puntosRecompensa')}>
                    <Translate contentKey="fidelizacion2App.producto.puntosRecompensa">Puntos Recompensa</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('descripcion')}>
                    <Translate contentKey="fidelizacion2App.producto.descripcion">Descripcion</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('estado')}>
                    <Translate contentKey="fidelizacion2App.producto.estado">Estado</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdDate')}>
                    <Translate contentKey="fidelizacion2App.producto.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedDate')}>
                    <Translate contentKey="fidelizacion2App.producto.updatedDate">Updated Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="fidelizacion2App.producto.club">Club</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="fidelizacion2App.producto.tipoProducto">Tipo Producto</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {productoList.map((producto, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/producto/${producto.id}`} color="link" size="sm">
                        {producto.id}
                      </Button>
                    </td>
                    <td>{producto.nombre}</td>
                    <td>{producto.precio}</td>
                    <td>{producto.costoPuntos}</td>
                    <td>{producto.puntosRecompensa}</td>
                    <td>{producto.descripcion}</td>
                    <td>{producto.estado ? 'true' : 'false'}</td>
                    <td>
                      {producto.createdDate ? <TextFormat type="date" value={producto.createdDate} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {producto.updatedDate ? <TextFormat type="date" value={producto.updatedDate} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{producto.club ? <Link to={`/club/${producto.club.id}`}>{producto.club.id}</Link> : ''}</td>
                    <td>
                      {producto.tipoProducto ? (
                        <Link to={`/tipo-producto/${producto.tipoProducto.id}`}>{producto.tipoProducto.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/producto/${producto.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/producto/${producto.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/producto/${producto.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="fidelizacion2App.producto.home.notFound">No Productos found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Producto;
