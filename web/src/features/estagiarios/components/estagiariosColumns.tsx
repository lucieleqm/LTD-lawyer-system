import { periodoEstagioLabel } from '../constants';
import { EstagiarioListItem } from '../types';
import { getTableHelpers } from '@/components/ui/dataTable/columnHelpers';

interface ColunasCallbacks {
  onEditar: (id: string | number) => void;
  onVerDetalhe: (id: string | number) => void;
  podeEditar: boolean;
}

export function getEstagiariosColumns({
  onEditar,
  onVerDetalhe,
  podeEditar,
}: ColunasCallbacks) {
  const helpers = getTableHelpers<EstagiarioListItem>();

  return [
    helpers.link('nome', 'Nome', (row) => onVerDetalhe(row.id)),
    helpers.text('matricula', 'Matrícula', { enableSorting: false }),
    helpers.enumMap('periodoEstagio', 'Período', periodoEstagioLabel, {
      enableSorting: false,
    }),
    helpers.status('usuarioStatus', { enableSorting: false }),
    ...helpers.edit(onEditar, podeEditar),
  ];
}
