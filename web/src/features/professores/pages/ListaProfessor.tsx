import { useNavigate } from 'react-router-dom';
import { ListLayout } from '@/components/ui/list/ListLayout';
import { DataTable } from '@/components/ui/dataTable/DataTable';
import { usePermission } from '@/features/auth/hooks/usePermission';
import { useListaPaginada } from '@/hooks/useListaPaginada';
import { professoresService } from '../services/professoresService';
import { getProfessoresColumns } from '../components/professoresColumns';
import { paths } from '@/routes/paths';
import { useMemo } from 'react';

export default function ListaProfessoresPage() {
  const navigate = useNavigate();
  const podeCriar = usePermission('professores:criar');
  const podeEditar = usePermission('professores:editar');

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
    fetchFn: professoresService.listar,
    filtroInicial: { termo: '' },
  });

  const columns = useMemo(
    () =>
      getProfessoresColumns({
        onEditar: (id) => navigate(paths.professores.editar(id)),
        onVerDetalhe: (id) => navigate(paths.professores.detalhe(id)),
        podeEditar,
      }),
    [navigate, podeEditar],
  );

  return (
    <ListLayout
      title="Professores"
      description="Aqui estão todos os professores cadastrados no sistema."
      breadcrumbs={[
        { label: 'Início', link: paths.home },
        { label: 'Professores' },
      ]}
      canCreate={podeCriar}
      onCreate={() => navigate(paths.professores.novo)}
      createButtonText="Novo Professor"
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
