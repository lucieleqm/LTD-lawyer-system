import { useNavigate } from 'react-router-dom';
import { ListLayout } from '@/components/ui/list/ListLayout';
import { DataTable } from '@/components/ui/dataTable/DataTable';
import { usePermission } from '@/features/auth/hooks/usePermission';
import { useListaPaginada } from '@/hooks/useListaPaginada';
import { tribunaisService } from '../services/tribunaisService';

import { paths } from '@/routes/paths';
import { useMemo } from 'react';
import { getTribunaisColumns } from '../components/tribunaisColumns';

export default function ListaTribunaisPage() {
  const navigate = useNavigate();
  const podeCriar = usePermission('tribunais:criar');
  const podeEditar = usePermission('tribunais:editar');

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
    fetchFn: tribunaisService.listar,
    filtroInicial: { termo: '' },
  });

  const columns = useMemo(
    () =>
      getTribunaisColumns({
        onEditar: (id) => navigate(paths.tribunais.editar(id)),
        onVerDetalhe: (id) => navigate(paths.tribunais.detalhe(id)),
        podeEditar,
      }),
    [navigate, podeEditar],
  );

  return (
    <ListLayout
      title="Tribunais"
      description="Aqui estão todos os tribunais cadastrados no sistema."
      breadcrumbs={[
        { label: 'Início', link: paths.home },
        { label: 'Tribunais' },
      ]}
      canCreate={podeCriar}
      onCreate={() => navigate(paths.tribunais.novo)}
      createButtonText="Novo Tribunal"
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
