import { OrgaoJulgador } from '../types';
import { getTableHelpers } from '@/components/ui/dataTable/columnHelpers';

interface ColunasCallbacks {
  onEditar: (id: string | number) => void;
  onVerDetalhe: (id: string | number) => void;
  podeEditar: boolean;
}

export function getOrgaosJulgadoresColumns({
  onEditar,
  onVerDetalhe,
  podeEditar,
}: ColunasCallbacks) {
  const helpers = getTableHelpers<OrgaoJulgador>();

  return [
    helpers.link('nome', 'Nome', (row) => onVerDetalhe(row.id)),
    helpers.date('criadoEm', 'Criação'),
    helpers.date('atualizadoEm', 'Última atualização'),
    helpers.text('ativo', 'Situação'),
    ...helpers.edit(onEditar, podeEditar),
  ];
}
