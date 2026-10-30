import { getTableHelpers } from '@/components/ui/dataTable/columnHelpers';
import { Role } from '@/constants/roles';
import { filtrarColunasPorRole } from '@/features/demandas/columnVisibility';
import { ProcessoListItem } from '../types';
import {
  areaDireitoLabel,
  processoStatusLabel,
  tribunalLabel,
} from '../constants';

interface ColunasCallbacks {
  onEditar: (id: string | number) => void;
  onVerDetalhe: (id: string | number) => void;
  podeEditar: boolean;
  role: Role;
}

export function getProcessosColumns({
  onEditar,
  onVerDetalhe,
  podeEditar,
  role,
}: ColunasCallbacks) {
  const helpers = getTableHelpers<ProcessoListItem>();

  const colunas = [
    // helpers.selection({ size: 20 }),
    helpers.link(
      'numeroProcesso',
      'Número do Processo',
      (row) => onVerDetalhe(row.id),
      {
        enableSorting: false,
      },
    ),
    helpers.date('prazoFinal', 'Prazo Final'),
    helpers.text('numeroProcessoPje', 'Número PJE', {
      enableSorting: false,
    }),

    helpers.enumMap('tribunal', 'Tribunal', tribunalLabel, {
      enableSorting: false,
    }),
    helpers.enumMap('areaDireito', 'Área do Direito', areaDireitoLabel, {
      enableSorting: false,
    }),
    helpers.enumMap('status', 'Status', processoStatusLabel, {
      enableSorting: false,
    }),

    helpers.text('assistidoNome', 'Assistido', { enableSorting: false }),
    helpers.text('advogadoNome', 'Advogado', { enableSorting: false }),
    helpers.text('estagiarioNome', 'Estagiário', { enableSorting: false }),

    ...helpers.edit(onEditar, podeEditar),
  ];

  return filtrarColunasPorRole(colunas, role);
}
