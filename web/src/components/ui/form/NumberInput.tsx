import { useFormContext, Controller } from 'react-hook-form';
import {
  NumberInput as MantineNumberInput,
  NumberInputProps,
} from '@mantine/core';

interface Props extends Omit<NumberInputProps, 'error'> {
  name: string;
  label: string;
}

export function NumberInput({ name, label, ...rest }: Props) {
  const { control } = useFormContext();

  return (
    <Controller
      name={name}
      control={control}
      render={({ field, fieldState }) => (
        <MantineNumberInput
          label={label}
          value={field.value}
          onChange={field.onChange}
          error={fieldState.error?.message}
          mb="md"
          {...rest}
        />
      )}
    />
  );
}
