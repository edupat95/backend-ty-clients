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

import { IAcceso } from 'app/shared/model/acceso.model';
import { getEntities, reset } from './acceso.reducer';

export const Acceso = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const accesoList = useAppSelector(state => state.acceso.entities);
  const loading = useAppSelector(state => state.acceso.loading);
  const totalItems = useAppSelector(state => state.acceso.totalItems);
  const links = useAppSelector(state => state.acceso.links);
  const entity = useAppSelector(state => state.acceso.entity);
  const updateSuccess = useAppSelector(state => state.acceso.updateSuccess);

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
      <h2 id="acceso-heading" data-cy="AccesoHeading">
        <Translate contentKey="fidelizacion2App.acceso.home.title">Accesos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="fidelizacion2App.acceso.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/acceso/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="fidelizacion2App.acceso.home.createLabel">Create new Acceso</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={accesoList ? accesoList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {accesoList && accesoList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="fidelizacion2App.acceso.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('costoPuntos')}>
                    <Translate contentKey="fidelizacion2App.acceso.costoPuntos">Costo Puntos</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaCanje')}>
                    <Translate contentKey="fidelizacion2App.acceso.fechaCanje">Fecha Canje</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('estado')}>
                    <Translate contentKey="fidelizacion2App.acceso.estado">Estado</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdDate')}>
                    <Translate contentKey="fidelizacion2App.acceso.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedDate')}>
                    <Translate contentKey="fidelizacion2App.acceso.updatedDate">Updated Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="fidelizacion2App.acceso.asociado">Asociado</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="fidelizacion2App.acceso.evento">Evento</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {accesoList.map((acceso, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/acceso/${acceso.id}`} color="link" size="sm">
                        {acceso.id}
                      </Button>
                    </td>
                    <td>{acceso.costoPuntos}</td>
                    <td>{acceso.fechaCanje ? <TextFormat type="date" value={acceso.fechaCanje} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{acceso.estado ? 'true' : 'false'}</td>
                    <td>{acceso.createdDate ? <TextFormat type="date" value={acceso.createdDate} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{acceso.updatedDate ? <TextFormat type="date" value={acceso.updatedDate} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{acceso.asociado ? <Link to={`/asociado/${acceso.asociado.id}`}>{acceso.asociado.id}</Link> : ''}</td>
                    <td>{acceso.evento ? <Link to={`/evento/${acceso.evento.id}`}>{acceso.evento.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/acceso/${acceso.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/acceso/${acceso.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/acceso/${acceso.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
                <Translate contentKey="fidelizacion2App.acceso.home.notFound">No Accesos found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Acceso;
