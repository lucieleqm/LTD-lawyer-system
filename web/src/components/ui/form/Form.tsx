import { normalizeError } from '@/lib/api/errorHandler';
import { notifications } from '@mantine/notifications';
import React from 'react';
import {
  FormProvider,
  FieldValues,
  UseFormReturn,
  Path,
} from 'react-hook-form';

interface FormProps<T extends FieldValues> {
  methods: UseFormReturn<T>;
  onSubmit: (dados: T) => Promise<void> | void;
  children: React.ReactNode;
}

export function Form<T extends FieldValues>({
  methods,
  onSubmit,
  children,
}: FormProps<T>) {
  async function handleSubmit(data: T) {
    try {
      await onSubmit(data);
    } catch (err) {
      const appError = normalizeError(err);

      if (Object.keys(appError.fields).length > 0) {
        Object.entries(appError.fields).forEach(([field, message]) => {
          methods.setError(field as Path<T>, { type: 'server', message });
        });
      } else {
        notifications.show({
          title: appError.title,
          message: appError.message,
          color: 'red',
        });
      }
    }
  }

  return (
    <FormProvider {...methods}>
      <form onSubmit={methods.handleSubmit(handleSubmit)}>{children}</form>
    </FormProvider>
  );
}
