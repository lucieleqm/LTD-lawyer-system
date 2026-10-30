import { getTableHelpers } from '@/components/ui/dataTable/columnHelpers';
import { DemandaListItem } from '../types';
import { etapaDemandaLabel } from '../constants';
import { TEMPESTIVIDADE } from '@/constants/tempestividade';
import { Role } from '@/constants/roles';
import { filtrarColunasPorRole } from '../columnVisibility';

interface ColunasCallbacks {
  onEditar: (id: string | number) => void;
  onVerDetalhe: (id: string | number) => void;
  podeEditar: boolean;
  role: Role;
}

export function getDemandasColumns({
  onEditar,
  onVerDetalhe,
  podeEditar,
  role,
}: ColunasCallbacks) {
  const helpers = getTableHelpers<DemandaListItem>();

  const colunas = [
    // helpers.selection({ size: 20 }),
    helpers.link('descricao', 'Descrição', (row) => onVerDetalhe(row.id), {
      enableSorting: false,
    }),
    helpers.date('prazoDocumentos', 'Prazo Documentação'),
    helpers.dateWithAlert('prazoFinal', 'Prazo Final', (row) => {
      return row.tempestividade === TEMPESTIVIDADE.FORA_DO_PRAZO;
    }),
    helpers.text('nomeAdvogado', 'Advogado', { enableSorting: false }),
    helpers.text('nomeEstagiario', 'Estagiário', { enableSorting: false }),
    helpers.text('nomeProfessor', 'Professor', { enableSorting: false }),
    helpers.enumMap('etapaAtual', 'Etapa Atual', etapaDemandaLabel, {
      enableSorting: false,
    }),
    ...helpers.edit(onEditar, podeEditar),
  ];

  return filtrarColunasPorRole(colunas, role);
}
