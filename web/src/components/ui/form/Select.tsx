import { Controller, useFormContext } from 'react-hook-form';
import {
  Select as MantineSelect,
  SelectProps as MantineSelectProps,
} from '@mantine/core';

interface SelectProps extends Omit<MantineSelectProps, 'data'> {
  name: string;
  label: string;
  options: { value: string; label: string }[];
}

export function Select({ name, label, options, ...rest }: SelectProps) {
  const {
    control,
    formState: { errors },
  } = useFormContext();

  const erro = errors[name]?.message as string | undefined;

  return (
    <Controller
      name={name}
      control={control}
      render={({ field: { onChange, onBlur, value, ref } }) => (
        <MantineSelect
          label={label}
          error={erro}
          data={options}
          placeholder="Selecione uma opção..."
          checkIconPosition="right"
          onChange={onChange}
          onBlur={onBlur}
          value={value || null}
          ref={ref}
          mb="md"
          {...rest}
        />
      )}
    ></Controller>
  );
}
