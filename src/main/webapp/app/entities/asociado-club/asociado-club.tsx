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

import { IAsociadoClub } from 'app/shared/model/asociado-club.model';
import { getEntities, reset } from './asociado-club.reducer';

export const AsociadoClub = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const asociadoClubList = useAppSelector(state => state.asociadoClub.entities);
  const loading = useAppSelector(state => state.asociadoClub.loading);
  const totalItems = useAppSelector(state => state.asociadoClub.totalItems);
  const links = useAppSelector(state => state.asociadoClub.links);
  const entity = useAppSelector(state => state.asociadoClub.entity);
  const updateSuccess = useAppSelector(state => state.asociadoClub.updateSuccess);

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
      <h2 id="asociado-club-heading" data-cy="AsociadoClubHeading">
        <Translate contentKey="fidelizacion2App.asociadoClub.home.title">Asociado Clubs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="fidelizacion2App.asociadoClub.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/asociado-club/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="fidelizacion2App.asociadoClub.home.createLabel">Create new Asociado Club</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={asociadoClubList ? asociadoClubList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {asociadoClubList && asociadoClubList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="fidelizacion2App.asociadoClub.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('identificador')}>
                    <Translate contentKey="fidelizacion2App.asociadoClub.identificador">Identificador</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaAsociacion')}>
                    <Translate contentKey="fidelizacion2App.asociadoClub.fechaAsociacion">Fecha Asociacion</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('puntosClub')}>
                    <Translate contentKey="fidelizacion2App.asociadoClub.puntosClub">Puntos Club</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('estado')}>
                    <Translate contentKey="fidelizacion2App.asociadoClub.estado">Estado</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdDate')}>
                    <Translate contentKey="fidelizacion2App.asociadoClub.createdDate">Created Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedDate')}>
                    <Translate contentKey="fidelizacion2App.asociadoClub.updatedDate">Updated Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="fidelizacion2App.asociadoClub.asociado">Asociado</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="fidelizacion2App.asociadoClub.club">Club</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="fidelizacion2App.asociadoClub.registrador">Registrador</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {asociadoClubList.map((asociadoClub, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/asociado-club/${asociadoClub.id}`} color="link" size="sm">
                        {asociadoClub.id}
                      </Button>
                    </td>
                    <td>{asociadoClub.identificador}</td>
                    <td>
                      {asociadoClub.fechaAsociacion ? (
                        <TextFormat type="date" value={asociadoClub.fechaAsociacion} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{asociadoClub.puntosClub}</td>
                    <td>{asociadoClub.estado ? 'true' : 'false'}</td>
                    <td>
                      {asociadoClub.createdDate ? (
                        <TextFormat type="date" value={asociadoClub.createdDate} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {asociadoClub.updatedDate ? (
                        <TextFormat type="date" value={asociadoClub.updatedDate} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {asociadoClub.asociado ? <Link to={`/asociado/${asociadoClub.asociado.id}`}>{asociadoClub.asociado.id}</Link> : ''}
                    </td>
                    <td>{asociadoClub.club ? <Link to={`/club/${asociadoClub.club.id}`}>{asociadoClub.club.id}</Link> : ''}</td>
                    <td>
                      {asociadoClub.registrador ? (
                        <Link to={`/registrador/${asociadoClub.registrador.id}`}>{asociadoClub.registrador.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/asociado-club/${asociadoClub.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/asociado-club/${asociadoClub.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/asociado-club/${asociadoClub.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
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
                <Translate contentKey="fidelizacion2App.asociadoClub.home.notFound">No Asociado Clubs found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default AsociadoClub;
