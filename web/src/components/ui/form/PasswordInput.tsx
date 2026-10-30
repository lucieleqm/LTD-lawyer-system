import { useFormContext } from 'react-hook-form';
import {
  PasswordInput as MantinePasswordInput,
  PasswordInputProps,
} from '@mantine/core';

interface Props extends Omit<PasswordInputProps, 'error'> {
  name: string;
  label: string;
}

export function PasswordInput({ name, label, ...rest }: Props) {
  const {
    register,
    formState: { errors },
  } = useFormContext();
  const error = errors[name]?.message as string;

  return (
    <MantinePasswordInput
      label={label}
      {...register(name)}
      error={error}
      mb="md"
      {...rest}
    />
  );
}
