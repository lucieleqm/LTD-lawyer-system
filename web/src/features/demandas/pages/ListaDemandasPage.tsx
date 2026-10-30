import { useNavigate } from 'react-router-dom';
import { ListLayout } from '@/components/ui/list/ListLayout';
import { DataTable } from '@/components/ui/dataTable/DataTable';
import { usePermission } from '@/features/auth/hooks/usePermission';
import { useListaPaginada } from '@/hooks/useListaPaginada';
import { paths } from '@/routes/paths';
import { useMemo } from 'react';
import { demandasService } from '../services/demandasService';
import { getDemandasColumns } from '../components/demandasColumns';
import { ETAPA_DEMANDA, EtapaDemanda, etapaDemandaLabel } from '../constants';
import { DemandaListItem } from '../types';
import {
  TEMPESTIVIDADE,
  Tempestividade,
  tempestividadeLabel,
} from '@/constants/tempestividade';
import { EnumFilterSelect } from '@/components/ui/list/EnumFilterSelect';
import { useAuth } from '@/features/auth/hooks/useAuth';
import { RowSelectionState } from '@tanstack/react-table';

interface FiltroDemandas {
  termo: string;
  etapaAtual?: EtapaDemanda;
  tempestividade?: Tempestividade;
}

export default function ListaDemandasPage() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const podeCriar = usePermission('demandas:criar');
  const podeEditar = usePermission('demandas:editar');

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
  } = useListaPaginada<DemandaListItem, FiltroDemandas>({
    fetchFn: demandasService.listar,
    filtroInicial: { termo: '' },
  });
  // const [rowSelection, setRowSelection] = useState<RowSelectionState>({});
  // const quantidadeSelecionada = Object.keys(rowSelection).length;

  const columns = useMemo(
    () =>
      getDemandasColumns({
        onEditar: (id) => navigate(paths.demandas.editar(id)),
        onVerDetalhe: (id) => navigate(paths.demandas.detalhe(id)),
        podeEditar,
        role: user!.role,
      }),
    [navigate, podeEditar],
  );

  return (
    <ListLayout
      title="Demandas"
      description="Aqui estão todas as demandas cadastradas no sistema."
      breadcrumbs={[
        { label: 'Início', link: paths.home },
        { label: 'Demandas' },
      ]}
      canCreate={podeCriar}
      onCreate={() => navigate(paths.demandas.novo)}
      createButtonText="Nova Demanda"
      searchProps={{
        value: filtro.termo,
        onChange: (termo) => setFiltro({ ...filtro, termo }),
        placeholder: 'Buscar Demandas...',
        tooltip: 'Busque por nome do advogado, professor ou estagiário.',
      }}
      filters={
        <>
          <EnumFilterSelect
            placeholder="Filtrar por Etapa"
            values={Object.values(ETAPA_DEMANDA)}
            labels={etapaDemandaLabel}
            value={filtro.etapaAtual}
            onChange={(v) => setFiltro({ ...filtro, etapaAtual: v })}
          />
          <EnumFilterSelect
            placeholder="Filtrar por Tempestividade"
            values={Object.values(TEMPESTIVIDADE)}
            labels={tempestividadeLabel}
            value={filtro.tempestividade}
            onChange={(v) => setFiltro({ ...filtro, tempestividade: v })}
          />
        </>
      }
    >
      <>
        {/* {quantidadeSelecionada > 0 && (
          <Button color="red">Apagar {quantidadeSelecionada} itens</Button>
        )} */}
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
      </>
    </ListLayout>
  );
}
