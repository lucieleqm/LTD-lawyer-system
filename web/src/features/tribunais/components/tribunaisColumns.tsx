import { esferaJusticaLabel } from '../constants';
import { Tribunal } from '../types';
import { getTableHelpers } from '@/components/ui/dataTable/columnHelpers';

interface ColunasCallbacks {
  onEditar: (id: string | number) => void;
  onVerDetalhe: (id: string | number) => void;
  podeEditar: boolean;
}

export function getTribunaisColumns({
  onEditar,
  onVerDetalhe,
  podeEditar,
}: ColunasCallbacks) {
  const helpers = getTableHelpers<Tribunal>();

  return [
    helpers.link('nome', 'Nome', (row) => onVerDetalhe(row.id)),
    helpers.text('sigla', 'Sigla'),
    helpers.enumMap('esfera', 'Esfera', esferaJusticaLabel),
    helpers.date('criadoEm', 'Data de criação'),
    helpers.date('atualizadoEm', 'Última atualização'),
    helpers.text('ativo', 'Situação'),
    ...helpers.edit(onEditar, podeEditar),
  ];
}
