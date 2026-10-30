import { useNavigate } from 'react-router-dom';
import { ListLayout } from '@/components/ui/list/ListLayout';
import { DataTable } from '@/components/ui/dataTable/DataTable';
import { usePermission } from '@/features/auth/hooks/usePermission';
import { useListaPaginada } from '@/hooks/useListaPaginada';
import { estagiariosService } from '../services/estagiariosService';
import { getEstagiariosColumns } from '../components/estagiariosColumns';
import { paths } from '@/routes/paths';
import { useMemo } from 'react';

export default function ListaEstagiariosPage() {
  const navigate = useNavigate();
  const podeCriar = usePermission('estagiarios:criar');
  const podeEditar = usePermission('estagiarios:editar');

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
    fetchFn: estagiariosService.listar,
    filtroInicial: { termo: '' },
  });

  const columns = useMemo(
    () =>
      getEstagiariosColumns({
        onEditar: (id) => navigate(paths.estagiarios.editar(id)),
        onVerDetalhe: (id) => navigate(paths.estagiarios.detalhe(id)),
        podeEditar,
      }),
    [navigate, podeEditar],
  );

  return (
    <ListLayout
      title="Estagiários"
      description="Aqui estão todos os estagiários cadastrados no sistema."
      breadcrumbs={[
        { label: 'Início', link: paths.home },
        { label: 'Estagiários' },
      ]}
      canCreate={podeCriar}
      onCreate={() => navigate(paths.estagiarios.novo)}
      createButtonText="Novo Estagiário"
      searchProps={{
        value: filtro.termo,
        onChange: (termo) => setFiltro({ ...filtro, termo }),
        placeholder: 'Buscar Estagiário...',
        tooltip: 'Busque por nome ou matrícula do estagiário.',
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
