import { useAuth } from './useAuth';
import { can, PermissionAction } from '@/permissions/permissions';

export function usePermission(action: PermissionAction): boolean {
  const { user } = useAuth();

  if (!user) return false;

  return can(user.role, action);
}
