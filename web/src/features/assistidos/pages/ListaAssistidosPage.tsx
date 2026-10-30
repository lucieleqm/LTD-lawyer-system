import { useNavigate } from 'react-router-dom';
import { ListLayout } from '@/components/ui/list/ListLayout';
import { DataTable } from '@/components/ui/dataTable/DataTable';
import { usePermission } from '@/features/auth/hooks/usePermission';
import { useListaPaginada } from '@/hooks/useListaPaginada';
import { paths } from '@/routes/paths';
import { useMemo } from 'react';
import { getAssistidosColumns } from '../components/assistidosColumns';
import { assistidosService } from '../services/assistidosService';

export default function ListaAssistidosPage() {
  const navigate = useNavigate();
  const podeCriar = usePermission('assistidos:criar');
  const podeEditar = usePermission('assistidos:editar');

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
    fetchFn: assistidosService.listar,
    filtroInicial: { termo: '' },
  });

  const columns = useMemo(
    () =>
      getAssistidosColumns({
        onEditar: (id) => navigate(paths.assistidos.editar(id)),
        onVerDetalhe: (id) => navigate(paths.assistidos.detalhe(id)),
        podeEditar,
      }),
    [navigate, podeEditar],
  );

  return (
    <ListLayout
      title="Assistidos"
      description="Aqui estão todos os assistidos cadastrados no sistema."
      breadcrumbs={[
        { label: 'Início', link: paths.home },
        { label: 'Assistidos' },
      ]}
      canCreate={podeCriar}
      onCreate={() => navigate(paths.assistidos.novo)}
      createButtonText="Novo Assistido"
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
