import {
  ActionIcon,
  Anchor,
  Checkbox,
  Group,
  Text,
  Tooltip,
} from '@mantine/core';
import { IconAlertCircle, IconEdit } from '@tabler/icons-react';
import type { ColumnDef, RowData, StockFeatures } from '@tanstack/react-table';
import { UsuarioStatus } from '@/constants/usuarioStatus';
import { UsuarioStatusBadge } from '../UsuarioStatusBadge';
import { formatarData } from '@/utils/formatters';

function createTextColumn<TData extends RowData>(
  accessorKey: keyof TData,
  headerLabel: string,
  overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
): ColumnDef<StockFeatures, TData, unknown> {
  return {
    accessorKey: accessorKey as string,
    header: headerLabel,
    ...overrides,
  };
}

function createLinkColumn<TData extends RowData>(
  accessorKey: keyof TData,
  headerLabel: string,
  onClick: (row: TData) => void,
  overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
): ColumnDef<StockFeatures, TData, unknown> {
  return {
    accessorKey: accessorKey as string,
    header: headerLabel,
    cell: ({ getValue, row }) => {
      const textValue = getValue() as string;
      return (
        <Anchor
          onClick={() => onClick(row.original)}
          c="institucional.6"
          lineClamp={2}
          title={textValue}
          style={{ wordBreak: 'break-word' }}
        >
          {textValue}
        </Anchor>
      );
    },
    ...overrides,
  };
}

function createStatusColumn<TData extends RowData>(
  accessorKey: keyof TData,
  overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
): ColumnDef<StockFeatures, TData, any> {
  return {
    accessorKey: accessorKey as string,
    header: 'Status',
    cell: ({ getValue }) => {
      const status = getValue() as UsuarioStatus;
      return <UsuarioStatusBadge status={status} />;
    },
    ...overrides,
  };
}

function createEnumColumn<TData extends RowData>(
  accessorKey: keyof TData,
  headerLabel: string,
  dicionario: Record<string, string>,
  overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
): ColumnDef<StockFeatures, TData, unknown> {
  return {
    accessorKey: accessorKey as string,
    header: headerLabel,
    cell: ({ getValue }) => {
      const valor = getValue() as string;
      return dicionario[valor] || 'Não definido';
    },
    ...overrides,
  };
}

function createEditColumn<T extends { id: string | number }>(
  onEdit: (id: string | number) => void,
  visivel: boolean,
  overrides?: Partial<ColumnDef<StockFeatures, T, unknown>>,
): ColumnDef<StockFeatures, T, unknown>[] {
  if (!visivel) return [];
  return [
    {
      id: 'acoes',
      header: '',
      cell: ({ row }) => (
        <Tooltip label="Editar" openDelay={400}>
          <ActionIcon variant="subtle" onClick={() => onEdit(row.original.id)}>
            <IconEdit size={16} />
          </ActionIcon>
        </Tooltip>
      ),
      ...overrides,
    },
  ];
}

function createSelectionColumn<TData extends RowData>(
  overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
): ColumnDef<StockFeatures, TData, unknown> {
  return {
    id: 'select',
    header: ({ table }) => (
      <Checkbox
        checked={table.getIsAllPageRowsSelected()}
        indeterminate={table.getIsSomePageRowsSelected()}
        onChange={table.getToggleAllPageRowsSelectedHandler()}
        aria-label="Selecionar todos"
        color="institucional"
      />
    ),
    cell: ({ row }) => (
      <Checkbox
        checked={row.getIsSelected()}
        onChange={row.getToggleSelectedHandler()}
        disabled={!row.getCanSelect()}
        aria-label="Selecionar linha"
        color="institucional"
      />
    ),
    enableSorting: false,
    ...overrides,
  };
}

function createDateAlertColumn<TData extends RowData>(
  accessorKey: keyof TData,
  headerLabel: string,
  checkIsOverdue: (row: TData) => boolean,
  overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
): ColumnDef<StockFeatures, TData, unknown> {
  return {
    accessorKey: accessorKey as string,
    header: headerLabel,
    cell: ({ getValue, row }) => {
      const dataBruta = getValue() as string;
      const dataFormatada = formatarData(dataBruta, 'somente-data');

      if (dataFormatada === '-') return '-';
      const emAtraso = checkIsOverdue(row.original);

      return (
        <Group gap="xs" wrap="nowrap">
          <Text
            size="sm"
            c={emAtraso ? 'red.7' : 'inherit'}
            fw={emAtraso ? 600 : 400}
          >
            {dataFormatada}
          </Text>

          {emAtraso && (
            <Tooltip label="Fora do prazo" withArrow>
              <IconAlertCircle size={16} color="var(--mantine-color-red-7)" />
            </Tooltip>
          )}
        </Group>
      );
    },
    ...overrides,
  };
}

function createDateColumn<TData extends RowData>(
  accessorKey: keyof TData,
  headerLabel: string,
  overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
): ColumnDef<StockFeatures, TData, unknown> {
  return {
    accessorKey: accessorKey as string,
    header: headerLabel,
    cell: ({ getValue }) => {
      const valor = getValue() as string;
      return formatarData(valor, 'somente-data');
    },
    ...overrides,
  };
}

export function getTableHelpers<
  TData extends RowData & { id: string | number },
>() {
  return {
    text: (
      acessorKey: keyof TData,
      headerLabel: string,
      overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
    ) => createTextColumn<TData>(acessorKey, headerLabel, overrides),

    link: (
      accessorKey: keyof TData,
      headerLabel: string,
      onClick: (row: TData) => void,
      overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
    ) => createLinkColumn<TData>(accessorKey, headerLabel, onClick, overrides),

    status: (
      accessorKey: keyof TData,
      overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
    ) => createStatusColumn<TData>(accessorKey, overrides),

    enumMap: (
      accessorKey: keyof TData,
      headerLabel: string,
      dicionario: Record<string, string>,
      overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
    ) =>
      createEnumColumn<TData>(accessorKey, headerLabel, dicionario, overrides),

    edit: (
      onEdit: (id: string | number) => void,
      visivel: boolean,
      overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
    ) => createEditColumn<TData>(onEdit, visivel, overrides),

    selection: (
      overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
    ) => createSelectionColumn<TData>(overrides),

    date: (
      accessorKey: keyof TData,
      headerLabel: string,
      overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
    ) => createDateColumn<TData>(accessorKey, headerLabel, overrides),

    dateWithAlert: (
      accessorKey: keyof TData,
      headerLabel: string,
      checkIsOverdue: (row: TData) => boolean,
      overrides?: Partial<ColumnDef<StockFeatures, TData, unknown>>,
    ) =>
      createDateAlertColumn<TData>(
        accessorKey,
        headerLabel,
        checkIsOverdue,
        overrides,
      ),
  };
}
