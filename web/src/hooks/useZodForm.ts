import { useForm, UseFormProps, FieldValues } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import z, { ZodType } from 'zod';

export function useZodForm<TSchema extends ZodType<any, any>>(
  schema: TSchema,
  options?: Omit<UseFormProps<any>, 'resolver'>,
) {
  return useForm<z.infer<TSchema>>({
    ...options,
    resolver: zodResolver(schema),
  });
}
