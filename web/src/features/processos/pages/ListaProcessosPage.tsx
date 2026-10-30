import { usePermission } from '@/features/auth/hooks/usePermission';
import {
  AREA_DIREITO,
  AreaDireito,
  areaDireitoLabel,
  PROCESSO_STATUS,
  ProcessoStatus,
  processoStatusLabel,
  TRIBUNAL,
  Tribunal,
  tribunalLabel,
} from '../constants';
import { useListaPaginada } from '@/hooks/useListaPaginada';
import { ProcessoListItem } from '../types';
import { processosService } from '../services/processosService';
import { useMemo } from 'react';
import { getProcessosColumns } from '../components/processosColumns';
import { paths } from '@/routes/paths';
import { useNavigate } from 'react-router-dom';
import { ListLayout } from '@/components/ui/list/ListLayout';
import { EnumFilterSelect } from '@/components/ui/list/EnumFilterSelect';
import { DataTable } from '@/components/ui/dataTable/DataTable';
import { useAuth } from '@/features/auth/hooks/useAuth';

interface FiltroProcessos {
  termo: string;
  status?: ProcessoStatus;
  areaDireito?: AreaDireito;
  tribunal?: Tribunal;
}

export default function ListaProcessosPage() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const podeCriar = usePermission('processos:criar');
  const podeEditar = usePermission('processos:editar');

  const {
    dados,
    isLoading,
    totalPages,
    totalElements,
    pagination,
    setPagination,
    sorting,
    setSorting,
    filtro,
    setFiltro,
  } = useListaPaginada<ProcessoListItem, FiltroProcessos>({
    fetchFn: processosService.listar,
    filtroInicial: { termo: '' },
  });

  const columns = useMemo(
    () =>
      getProcessosColumns({
        onEditar: (id) => navigate(paths.processos.editar(id)),
        onVerDetalhe: (id) => navigate(paths.processos.detalhe(id)),
        podeEditar,
        role: user!.role,
      }),
    [navigate, podeEditar],
  );

  return (
    <ListLayout
      title="Processos"
      description="Listagem de processos do sistema."
      breadcrumbs={[
        { label: 'Início', link: paths.home },
        { label: 'Processos' },
      ]}
      canCreate={podeCriar}
      onCreate={() => navigate(paths.processos.novo)}
      createButtonText="Novo Processo"
      searchProps={{
        value: filtro.termo,
        onChange: (termo) => setFiltro({ ...filtro, termo }),
        placeholder: 'Buscar Processos...',
        tooltip:
          'Busque por número do processo, PJe, nome do assistido, advogado ou estagiário',
      }}
      filters={
        <>
          <EnumFilterSelect
            placeholder="Filtrar por Status"
            values={Object.values(PROCESSO_STATUS)}
            labels={processoStatusLabel}
            value={filtro.status}
            onChange={(v) => setFiltro({ ...filtro, status: v })}
          />
          <EnumFilterSelect
            placeholder="Filtrar por Área do Direito"
            values={Object.values(AREA_DIREITO)}
            labels={areaDireitoLabel}
            value={filtro.areaDireito}
            onChange={(v) => setFiltro({ ...filtro, areaDireito: v })}
          />
          <EnumFilterSelect
            placeholder="Filtrar por Tribunal"
            values={Object.values(TRIBUNAL)}
            labels={tribunalLabel}
            value={filtro.tribunal}
            onChange={(v) => setFiltro({ ...filtro, tribunal: v })}
          />
        </>
      }
    >
      <DataTable
        data={dados}
        columns={columns}
        isLoading={isLoading}
        pagination={pagination}
        onPaginationChange={setPagination}
        sorting={sorting}
        onSortingChange={setSorting}
        pageCount={totalPages}
        totalElements={totalElements}
        // rowSelection={rowSelection}
        // onRowSelectionChange={setRowSelection}
      />
    </ListLayout>
  );
}
