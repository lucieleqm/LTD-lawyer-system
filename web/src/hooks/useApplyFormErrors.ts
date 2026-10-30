import { UseFormSetError, FieldValues, Path } from 'react-hook-form';
import { AppError } from '@/lib/api/errorHandler';

export function applyFormErrors<T extends FieldValues>(
  error: AppError,
  setError: UseFormSetError<T>,
) {
  Object.entries(error.fields).forEach(([field, message]) => {
    setError(field as Path<T>, { type: 'server', message });
  });
}
