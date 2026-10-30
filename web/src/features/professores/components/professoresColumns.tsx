import { ProfessorListItem } from '../types';
import { getTableHelpers } from '@/components/ui/dataTable/columnHelpers';

interface ColunasCallbacks {
  onEditar: (id: string | number) => void;
  onVerDetalhe: (id: string | number) => void;
  podeEditar: boolean;
}

export function getProfessoresColumns({
  onEditar,
  onVerDetalhe,
  podeEditar,
}: ColunasCallbacks) {
  const helpers = getTableHelpers<ProfessorListItem>();

  return [
    helpers.link('nome', 'Nome', (row) => onVerDetalhe(row.id)),
    helpers.text('telefone', 'Telefone', { enableSorting: false }),
    helpers.text('email', 'Email', { enableSorting: false }),
    helpers.status('usuarioStatus', { enableSorting: false }),
    ...helpers.edit(onEditar, podeEditar),
  ];
}
