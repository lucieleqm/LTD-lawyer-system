import { useFormContext, Controller } from 'react-hook-form';
import { MaskInput as MantineMaskInput, MaskInputProps } from '@mantine/core';

interface Props extends Omit<MaskInputProps, 'error'> {
  name: string;
  label: string;
}

export function MaskInput({ name, label, ...rest }: Props) {
  const { control } = useFormContext();

  return (
    <Controller
      name={name}
      control={control}
      render={({ field, fieldState }) => (
        <MantineMaskInput
          name={field.name}
          ref={field.ref}
          label={label}
          defaultValue={field.value}
          onChangeRaw={(raw) => {
            field.onChange(raw);
          }}
          onBlur={field.onBlur}
          error={fieldState.error?.message}
          mb="md"
          {...rest}
        />
      )}
    />
  );
}
