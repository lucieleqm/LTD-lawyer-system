import { useEffect, useState } from 'react';
import { useDebouncedValue } from '@mantine/hooks';
import type { PaginationState, SortingState } from '@tanstack/react-table';
import { PageResponse } from '@/types/pageResponse';

interface UseListaPaginadaOptions<T, TFiltro extends Record<string, any>> {
  fetchFn: (
    params: {
      pageIndex: number;
      pageSize: number;
      sort?: string[];
    } & TFiltro,
  ) => Promise<PageResponse<T>>;
  filtroInicial?: TFiltro;
  pageSizeInicial?: number;
  sortingInicial?: SortingState;
  debounceMs?: number;
}

export function useListaPaginada<T, TFiltro extends Record<string, any>>({
  fetchFn,
  filtroInicial = {} as TFiltro,
  pageSizeInicial = 15,
  sortingInicial = [],
  debounceMs = 400,
}: UseListaPaginadaOptions<T, TFiltro>) {
  const [dados, setDados] = useState<T[]>([]);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [isLoading, setIsLoading] = useState(true);
  const [refetchTrigger, setRefetchTrigger] = useState(0);
  const refetch = () => setRefetchTrigger((prev) => prev + 1);

  const [pagination, setPagination] = useState<PaginationState>({
    pageIndex: 0,
    pageSize: pageSizeInicial,
  });

  const [sorting, setSorting] = useState<SortingState>(sortingInicial);
  const [filtro, setFiltro] = useState<TFiltro>(filtroInicial);
  const [filtroDebounced] = useDebouncedValue(filtro, debounceMs);

  useEffect(() => {
    setPagination((prev) =>
      prev.pageIndex === 0 ? prev : { ...prev, pageIndex: 0 },
    );
  }, [filtroDebounced, sorting]);

  useEffect(() => {
    let ativo = true; // evita "race condition": ignora resposta de uma busca antiga que chegou atrasada

    setIsLoading(true);

    const sortStrings = sorting.map(
      (s) => `${s.id},${s.desc ? 'desc' : 'asc'}`,
    );

    fetchFn({
      pageIndex: pagination.pageIndex,
      pageSize: pagination.pageSize,
      sort: sortStrings.length > 0 ? sortStrings : undefined,
      ...filtroDebounced,
    })
      .then((response) => {
        if (!ativo) return;
        setDados(response.content);
        setTotalPages(response.page.totalPages);
        setTotalElements(response.page.totalElements);
      })
      .finally(() => {
        if (ativo) setIsLoading(false);
      });

    return () => {
      ativo = false;
    };
  }, [
    pagination.pageIndex,
    pagination.pageSize,
    filtroDebounced,
    sorting,
    refetchTrigger,
  ]);

  return {
    dados,
    isLoading,
    totalPages,
    totalElements,
    pagination,
    setPagination,
    filtro,
    setFiltro,
    sorting,
    setSorting,
    refetch,
  };
}
