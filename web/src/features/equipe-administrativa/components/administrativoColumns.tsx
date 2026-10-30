import { usuarioRoleLabel } from '@/constants/roles';
import { AdministrativoListItem } from '../types';
import { getTableHelpers } from '@/components/ui/dataTable/columnHelpers';

interface ColunasCallbacks {
  onEditar: (id: string | number) => void;
  onVerDetalhe: (id: string | number) => void;
  podeEditar: boolean;
}

export function getAdministrativoColumns({
  onEditar,
  onVerDetalhe,
  podeEditar,
}: ColunasCallbacks) {
  const helpers = getTableHelpers<AdministrativoListItem>();

  return [
    helpers.link('nome', 'Nome', (row) => onVerDetalhe(row.id)),
    helpers.enumMap('role', 'Cargo', usuarioRoleLabel, {
      enableSorting: false,
    }),
    helpers.status('usuarioStatus', { enableSorting: false }),
    ...helpers.edit(onEditar, podeEditar),
  ];
}
