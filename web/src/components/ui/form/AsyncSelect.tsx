import { useState, useEffect } from 'react';
import { Controller, useFormContext } from 'react-hook-form';
import {
  Select as MantineSelect,
  SelectProps as MantineSelectProps,
  Loader,
} from '@mantine/core';
import { useDebouncedValue } from '@mantine/hooks';

interface AsyncSelectProps extends Omit<
  MantineSelectProps,
  'data' | 'searchable' | 'onSearchChange'
> {
  name: string;
  label: string;
  fetchData: (termo: string) => Promise<{ value: string; label: string }[]>;
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

  const [options, setOptions] = useState<{ value: string; label: string }[]>(
    [],
  );
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    let montado = true;

    async function carregarDados() {
      setLoading(true);
      try {
        const data = await fetchData(debouncedSearch);
        if (montado) setOptions(data);
      } catch (error) {
        console.error(`Erro ao buscar opções para ${name}`, error);
        if (montado) setOptions([]);
      } finally {
        if (montado) setLoading(false);
      }
    }

    carregarDados();

    return () => {
      montado = false; // Evita memory leaks se o componente desmontar
    };
  }, [debouncedSearch, fetchData]);

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
