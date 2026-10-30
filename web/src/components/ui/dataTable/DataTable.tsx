import { useState } from 'react';
import {
  useTable,
  stockFeatures,
  type ColumnDef,
  type StockFeatures,
  type SortingState,
  type PaginationState,
  Updater,
  RowData,
  RowSelectionState,
} from '@tanstack/react-table';
import { Table, Group, Text, Pagination } from '@mantine/core';
import {
  IconChevronUp,
  IconChevronDown,
  IconSelector,
} from '@tabler/icons-react';
import classes from './DataTable.module.css';
import { EmptyState } from '../EmptyState';
import { LoadingState } from '../LoadingState';

interface DataTableProps<TData extends RowData> {
  data: TData[];
  columns: ColumnDef<StockFeatures, TData, any>[];
  isLoading?: boolean;
  emptyMessage?: string;
  totalElements?: number;
  pagination: PaginationState;
  onPaginationChange: (updater: Updater<PaginationState>) => void;
  pageCount: number;
  sorting?: SortingState;
  onSortingChange?: (updater: Updater<SortingState>) => void;
  rowSelection?: RowSelectionState;
  onRowSelectionChange?: (updater: Updater<RowSelectionState>) => void;
}

export function DataTable<TData extends RowData>({
  data,
  columns,
  isLoading,
  totalElements,
  pagination,
  onPaginationChange,
  pageCount,
  sorting = [],
  onSortingChange,
  rowSelection = {},
  onRowSelectionChange,
}: DataTableProps<TData>) {
  const table = useTable({
    data,
    columns,
    features: stockFeatures,
    state: { sorting, pagination, rowSelection },
    onSortingChange,
    manualSorting: true,
    onPaginationChange,
    manualPagination: true,
    pageCount,
    onRowSelectionChange,
    // getRowId: (row) => row.id,
  });

  if (isLoading) {
    return <LoadingState />;
  }

  if (data.length === 0) {
    return <EmptyState />;
  }

  return (
    <div>
      <div className={classes.wrapper}>
        <Table.ScrollContainer minWidth={600}>
          <Table striped highlightOnHover verticalSpacing="sm">
            <Table.Thead className={classes.header}>
              {table.getHeaderGroups().map((headerGroup) => (
                <Table.Tr key={headerGroup.id}>
                  {headerGroup.headers.map((header) => (
                    <Table.Th
                      key={header.id}
                      onClick={header.column.getToggleSortingHandler()}
                      className={classes.headerCell}
                      style={{
                        cursor: header.column.getCanSort()
                          ? 'pointer'
                          : 'default',
                      }}
                    >
                      <Group gap={4} wrap="nowrap">
                        <table.FlexRender header={header} />
                        {header.column.getCanSort() && (
                          <IconSort direction={header.column.getIsSorted()} />
                        )}
                      </Group>
                    </Table.Th>
                  ))}
                </Table.Tr>
              ))}
            </Table.Thead>

            <Table.Tbody>
              {table.getRowModel().rows.map((row) => (
                <Table.Tr key={row.id} className={classes.row}>
                  {row.getAllCells().map((cell) => (
                    <Table.Td key={cell.id}>
                      <table.FlexRender cell={cell} />
                    </Table.Td>
                  ))}
                </Table.Tr>
              ))}
            </Table.Tbody>
          </Table>
        </Table.ScrollContainer>
      </div>

      <Group justify="space-between" mt="md">
        <Text size="sm" c="dimmed">
          {totalElements !== undefined
            ? `${totalElements} registro(s) — página ${table.state.pagination.pageIndex + 1} de ${table.getPageCount()}`
            : `Página ${table.state.pagination.pageIndex + 1} de ${table.getPageCount()}`}
        </Text>
        <Pagination
          total={table.getPageCount()}
          value={table.state.pagination.pageIndex + 1}
          onChange={(page) => table.setPageIndex(page - 1)}
          size="sm"
          color="institucional"
        />
      </Group>
    </div>
  );
}

function IconSort({ direction }: { direction: false | 'asc' | 'desc' }) {
  if (direction === 'asc')
    return (
      <IconChevronUp size={14} color="var(--mantine-color-institucional-6)" />
    );
  if (direction === 'desc')
    return (
      <IconChevronDown size={14} color="var(--mantine-color-institucional-6)" />
    );
  return <IconSelector size={14} opacity={0.4} />;
}
