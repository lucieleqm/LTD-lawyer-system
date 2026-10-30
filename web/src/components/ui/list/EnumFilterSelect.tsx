import { Select } from '@mantine/core';

interface EnumFilterSelectProps<T extends string> {
  placeholder: string;
  values: readonly T[];
  labels: Record<T, string>;
  value: T | undefined | null;
  onChange: (value: T | undefined) => void;
  w?: number | string;
}

export function EnumFilterSelect<T extends string>({
  placeholder,
  values,
  labels,
  value,
  onChange,
  w = 200,
}: EnumFilterSelectProps<T>) {
  return (
    <Select
      placeholder={placeholder}
      checkIconPosition="right"
      data={values.map((v) => ({ value: v, label: labels[v] }))}
      value={value ?? null}
      onChange={(valor) => onChange((valor as T) ?? undefined)}
      clearable
      w={w}
      radius={"lg"}
    />
  );
}
