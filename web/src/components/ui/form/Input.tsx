import { useFormContext } from 'react-hook-form';
import { TextInput, TextInputProps } from '@mantine/core';

interface InputProps extends Omit<TextInputProps, 'error'> {
  name: string;
  label: string;
}

export function Input({ name, label, ...rest }: InputProps) {
  const { register, formState, getFieldState } = useFormContext();

  const { error } = getFieldState(name, formState);

  return (
    <TextInput
      label={label}
      {...register(name)}
      error={error?.message as string}
      {...rest}
      mb="md"
    />
  );
}
