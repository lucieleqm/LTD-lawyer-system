import { useNavigate } from 'react-router-dom';
import { ListLayout } from '@/components/ui/list/ListLayout';
import { DataTable } from '@/components/ui/dataTable/DataTable';
import { usePermission } from '@/features/auth/hooks/usePermission';
import { useListaPaginada } from '@/hooks/useListaPaginada';

import { paths } from '@/routes/paths';
import { useMemo } from 'react';
import { getOrgaosJulgadoresColumns } from '../components/orgaosJulgadoresColumns';
import { orgaosJulgadoresService } from '../services/orgaosJulgadoresService';

export default function ListaOrgaosJulgadoresPage() {
  const navigate = useNavigate();
  const podeCriar = usePermission('orgaosJulgadores:criar');
  const podeEditar = usePermission('orgaosJulgadores:editar');

  const {
    dados,
    isLoading,
    totalPages,
    totalElements,
    pagination,
    setPagination,
    filtro,
    setFiltro,
  } = useListaPaginada({
    fetchFn: orgaosJulgadoresService.listar,
    filtroInicial: { termo: '' },
  });

  const columns = useMemo(
    () =>
      getOrgaosJulgadoresColumns({
        onEditar: (id) => navigate(paths.orgaosJulgadores.editar(id)),
        onVerDetalhe: (id) => navigate(paths.orgaosJulgadores.detalhe(id)),
        podeEditar,
      }),
    [navigate, podeEditar],
  );

  return (
    <ListLayout
      title="Órgãos Julgadores"
      description="Aqui estão todos os órgãos julgadores cadastrados no sistema."
      breadcrumbs={[
        { label: 'Início', link: paths.home },
        { label: 'Órgãos Julgadores' },
      ]}
      canCreate={podeCriar}
      onCreate={() => navigate(paths.orgaosJulgadores.novo)}
      createButtonText="Novo Órgão Julgador"
      searchProps={{
        value: filtro.termo,
        onChange: (termo) => setFiltro({ ...filtro, termo }),
        placeholder: 'Buscar por nome...',
      }}
    >
      <DataTable
        data={dados}
        columns={columns}
        isLoading={isLoading}
        pagination={pagination}
        onPaginationChange={setPagination}
        pageCount={totalPages}
        totalElements={totalElements}
      />
    </ListLayout>
  );
}
