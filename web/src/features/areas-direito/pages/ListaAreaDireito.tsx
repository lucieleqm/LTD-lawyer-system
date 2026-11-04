import { useNavigate } from 'react-router-dom';
import { ListLayout } from '@/components/ui/list/ListLayout';
import { DataTable } from '@/components/ui/dataTable/DataTable';
import { usePermission } from '@/features/auth/hooks/usePermission';
import { useListaPaginada } from '@/hooks/useListaPaginada';


import { paths } from '@/routes/paths';
import { useMemo } from 'react';
import { getAreasDireitoColumns } from '../components/areasdireitoColumns';
import { areasDireitoService } from '../services/areasDireitoService';

export default function ListaAreasDireitoPage() {
  const navigate = useNavigate();
  const podeCriar = usePermission('areasDireito:criar');
  const podeEditar = usePermission('areasDireito:editar');

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
    fetchFn: areasDireitoService.listar,
    filtroInicial: { termo: '' },
  });

  const columns = useMemo(
    () =>
      getAreasDireitoColumns({
        onEditar: (id) => navigate(paths.areasDireito.editar(id)),
        onVerDetalhe: (id) => navigate(paths.areasDireito.detalhe(id)),
        podeEditar,
      }),
    [navigate, podeEditar],
  );

  return (
    <ListLayout
      title="Áreas do Direito"
      description="Aqui estão todos as áreas do direito cadastradas no sistema."
      breadcrumbs={[
        { label: 'Início', link: paths.home },
        { label: 'Áreas do Direito' },
      ]}
      canCreate={podeCriar}
      onCreate={() => navigate(paths.areasDireito.novo)}
      createButtonText="Nova Área do Direito"
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
