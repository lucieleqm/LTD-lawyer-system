import { useNavigate } from 'react-router-dom';
import { ListLayout } from '@/components/ui/list/ListLayout';
import { DataTable } from '@/components/ui/dataTable/DataTable';
import { usePermission } from '@/features/auth/hooks/usePermission';
import { useListaPaginada } from '@/hooks/useListaPaginada';

import { paths } from '@/routes/paths';
import { useMemo } from 'react';
import { advogadosService } from '../services/advogadosService';
import { getAdvogadosColumns } from '../components/advogadosColumns';

export default function ListaAdvogadosPage() {
  const navigate = useNavigate();
  const podeCriar = usePermission('advogados:criar');
  const podeEditar = usePermission('administrativo:editar');

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
    fetchFn: advogadosService.listar,
    filtroInicial: { termo: '' },
  });

  const columns = useMemo(
    () =>
      getAdvogadosColumns({
        onEditar: (id) => navigate(paths.advogados.editar(id)),
        onVerDetalhe: (id) => navigate(paths.advogados.detalhe(id)),
        podeEditar,
      }),
    [navigate, podeEditar],
  );

  return (
    <ListLayout
      title="Advogados"
      description="Aqui estão todos os advogados cadastrados no sistema."
      breadcrumbs={[
        { label: 'Início', link: paths.home },
        { label: 'Advogados' },
      ]}
      canCreate={podeCriar}
      onCreate={() => navigate(paths.advogados.novo)}
      createButtonText="Novo Advogado"
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
