import { useFormContext } from 'react-hook-form';
import { Textarea as MantineTextarea, TextareaProps } from '@mantine/core';

interface FormTextareaProps extends Omit<TextareaProps, 'error'> {
  name: string;
  label: string;
}

export function Textarea({ name, label, ...rest }: FormTextareaProps) {
  const {
    register,
    formState: { errors },
  } = useFormContext();
  const erro = errors[name]?.message as string | undefined;

  return (
    <MantineTextarea
      label={label}
      error={erro}
      {...register(name)}
      {...rest}
      mb="md"
    />
  );
}
