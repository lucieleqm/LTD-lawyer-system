import { useState, useEffect, useRef, useCallback } from 'react';
import { Controller, useFormContext } from 'react-hook-form';
import {
  Select as MantineSelect,
  SelectProps as MantineSelectProps,
  Loader,
} from '@mantine/core';
import { useDebouncedValue } from '@mantine/hooks';
import { FetchOptions, Option } from '@/types/sliceResponse';

interface AsyncSelectProps extends Omit<
  MantineSelectProps,
  'data' | 'searchable' | 'onSearchChange'
> {
  name: string;
  label: string;
  fetchData: FetchOptions;
}

export function AsyncSelect({
  name,
  label,
  fetchData,
  ...rest
}: AsyncSelectProps) {
  const {
    control,
    formState: { errors },
  } = useFormContext();

  const erro = errors[name]?.message as string | undefined;

  const [searchValue, setSearchValue] = useState('');
  const [debouncedSearch] = useDebouncedValue(searchValue, 300);

  const [options, setOptions] = useState<Option[]>([]);
  const [page, setPage] = useState(0);
  const [hasNext, setHasNext] = useState(false);
  const [loading, setLoading] = useState(false);

  const requestId = useRef(0);

  const carregar = useCallback(
    async (termo: string, pagina: number) => {
      const id = ++requestId.current;
      setLoading(true);
      try {
        const slice = await fetchData(termo, pagina);
        if (id !== requestId.current) return; // resposta obsoleta
        setOptions((prev) => (pagina === 0 ? slice.content : mesclar(prev, slice.content)));
        setPage(slice.page);
        setHasNext(slice.hasNext);
        
      } catch (error) {
        console.error(`Erro ao buscar opções para ${name}`, error);
        if (id === requestId.current && pagina === 0) {
          setOptions([]);
          setHasNext(false);
        }
      } finally {
        if (id === requestId.current) setLoading(false);
      }
    },
    [fetchData, name],
  );


  useEffect(() => {
    carregar(debouncedSearch, 0);
  }, [debouncedSearch, carregar]);

  const carregarMais = () => {
    if (hasNext && !loading) carregar(debouncedSearch, page + 1);
  };

    
  return (
    <Controller
      name={name}
      control={control}
      render={({ field: { onChange, onBlur, value, ref } }) => (
        <MantineSelect
          label={label}
          error={erro}
          data={options}
          placeholder="Digite para buscar..."
          checkIconPosition="right"
          searchable
          searchValue={searchValue}
          onSearchChange={setSearchValue}
          onChange={onChange}
          onBlur={onBlur}
          value={value || null}
          ref={ref}
          mb="md"
          rightSection={loading ? <Loader size="xs" /> : null}
          {...rest}
        />
      )}
    />
  );
}

function mesclar(atuais: Option[], novas: Option[]): Option[] {
  const vistos = new Set(atuais.map((o) => o.value));
  return [...atuais, ...novas.filter((o) => !vistos.has(o.value))];
}
