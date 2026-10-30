import { useNavigate } from 'react-router-dom';
import { DataTable } from '@/components/ui/dataTable/DataTable';
import { usePermission } from '@/features/auth/hooks/usePermission';
import { useListaPaginada } from '@/hooks/useListaPaginada';
import { administrativoService } from '../services/administrativoService';
import { getAdministrativoColumns } from '../components/administrativoColumns';
import { paths } from '@/routes/paths';
import { ListLayout } from '@/components/ui/list/ListLayout';
import { useMemo } from 'react';

export default function ListaAdministrativoPage() {
  const navigate = useNavigate();
  const podeCriar = usePermission('administrativo:criar');
  const podeEditar = usePermission('administrativo:editar');

  const {
    dados,
    isLoading,
    totalPages,
    totalElements,
    pagination,
    setPagination,
    // refetch,
  } = useListaPaginada({
    fetchFn: administrativoService.listar,
  });

  const columns = useMemo(
    () =>
      getAdministrativoColumns({
        onEditar: (id) => navigate(paths.administrativo.editar(id)),
        onVerDetalhe: (id) => navigate(paths.administrativo.detalhe(id)),
        podeEditar,
      }),
    [podeEditar, navigate],
  );

  return (
    <ListLayout
      title="Equipe Administrativa"
      description="Aqui está a equipe administrativa do sistema."
      breadcrumbs={[
        { label: 'Início', link: paths.home },
        { label: 'Equipe Administrativa' },
      ]}
      canCreate={podeCriar}
      createButtonText="Novo Membro"
      onCreate={() => navigate(paths.administrativo.novo)}
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
