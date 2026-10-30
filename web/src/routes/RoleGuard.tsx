import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '@/features/auth/hooks/useAuth';
import { can, PermissionAction } from '@/permissions/permissions';
import { paths } from './paths';

interface RoleGuardProps {
  action: PermissionAction;
  children: React.ReactNode;
}

export function RoleGuard({ action, children }: RoleGuardProps) {
  const { user } = useAuth();

  if (!user) return <Navigate to={paths.login} replace />;

  return can(user.role, action) ? (
    <>{children}</>
  ) : (
    <Navigate to={paths.acessoNegado} replace />
  );
}
