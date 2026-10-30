import { ROLE, Role } from '@/constants/roles';
import { ColumnDef, RowData, StockFeatures } from '@tanstack/react-table';

export const colunasOcultasPorRole: Record<string, readonly Role[]> = {
  nomeEstagiario: [ROLE.ESTAGIARIO],
  nomeAdvogado: [ROLE.ADVOGADO],
  nomeProfessor: [ROLE.PROFESSOR],
};

/**
 * Oculta colunas que o usuário não precisa ver por ser uma informação
 * implícita para ele, ex: Estagiário não precisa ver uma coluna que
 * mostra o nome do estagiário na tabela de demandas, pq já é implícito que todas
 * as demandas mostradas pertecem a ele.
 */
export function filtrarColunasPorRole<TData extends RowData>(
  colunas: (ColumnDef<StockFeatures, TData, any> & {
    accessorKey?: string;
    id?: string;
  })[],
  role: Role,
) {
  return colunas.filter((coluna) => {
    const chave = coluna.id ?? coluna.accessorKey;
    const ocultaPara = chave ? colunasOcultasPorRole[chave] : undefined;
    return !ocultaPara?.includes(role);
  });
}
