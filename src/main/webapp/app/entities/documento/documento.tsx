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

import { IDocumento } from 'app/shared/model/documento.model';
import { getEntities, reset } from './documento.reducer';

export const Documento = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const documentoList = useAppSelector(state => state.documento.entities);
  const loading = useAppSelector(state => state.documento.loading);
  const totalItems = useAppSelector(state => state.documento.totalItems);
  const links = useAppSelector(state => state.documento.links);
  const entity = useAppSelector(state => state.documento.entity);
  const updateSuccess = useAppSelector(state => state.documento.updateSuccess);

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
      <h2 id="documento-heading" data-cy="DocumentoHeading">
        <Translate contentKey="fidelizacion2App.documento.home.title">Documentos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="fidelizacion2App.documento.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/documento/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="fidelizacion2App.documento.home.createLabel">Create new Documento</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={documentoList ? documentoList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {documentoList && documentoList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="fidelizacion2App.documento.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('numeroTramite')}>
                    <Translate contentKey="fidelizacion2App.documento.numeroTramite">Numero Tramite</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('apellidos')}>
                    <Translate contentKey="fidelizacion2App.documento.apellidos">Apellidos</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nombres')}>
                    <Translate contentKey="fidelizacion2App.documento.nombres">Nombres</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sexo')}>
                    <Translate contentKey="fidelizacion2App.documento.sexo">Sexo</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('numeroDni')}>
                    <Translate contentKey="fidelizacion2App.documento.numeroDni">Numero Dni</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('ejemplar')}>
                    <Translate contentKey="fidelizacion2App.documento.ejemplar">Ejemplar</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nacimiento')}>
                    <Translate contentKey="fidelizacion2App.documento.nacimiento">Nacimiento</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaEmision')}>
                    <Translate contentKey="fidelizacion2App.documento.fechaEmision">Fecha Emision</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('inicioFinCuil')}>
                    <Translate contentKey="fidelizacion2App.documento.inicioFinCuil">Inicio Fin Cuil</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdDate')}>
                    <Translate contentKey="fidelizacion2App.documento.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedDate')}>
                    <Translate contentKey="fidelizacion2App.documento.updatedDate">Updated Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="fidelizacion2App.documento.user">User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {documentoList.map((documento, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/documento/${documento.id}`} color="link" size="sm">
                        {documento.id}
                      </Button>
                    </td>
                    <td>{documento.numeroTramite}</td>
                    <td>{documento.apellidos}</td>
                    <td>{documento.nombres}</td>
                    <td>{documento.sexo}</td>
                    <td>{documento.numeroDni}</td>
                    <td>{documento.ejemplar}</td>
                    <td>
                      {documento.nacimiento ? <TextFormat type="date" value={documento.nacimiento} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {documento.fechaEmision ? (
                        <TextFormat type="date" value={documento.fechaEmision} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{documento.inicioFinCuil}</td>
                    <td>
                      {documento.createdDate ? <TextFormat type="date" value={documento.createdDate} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {documento.updatedDate ? <TextFormat type="date" value={documento.updatedDate} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{documento.user ? documento.user.id : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/documento/${documento.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/documento/${documento.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/documento/${documento.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
                <Translate contentKey="fidelizacion2App.documento.home.notFound">No Documentos found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Documento;
