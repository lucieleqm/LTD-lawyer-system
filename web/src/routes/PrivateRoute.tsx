import { useAuth } from '@/features/auth/hooks/useAuth';
import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { paths } from './paths';
import { Center, Loader } from '@mantine/core';

export function PrivateRoute() {
  const { isAuthenticated, isLoading } = useAuth();
  const location = useLocation();

  if (isLoading) {
    return (
      <Center h="100vh" bg="gray.0">
        <Loader color="institucional" type="dots" />
      </Center>
    );
  }

  if (!isAuthenticated) {
    return <Navigate to={paths.login} state={{ from: location }} replace />;
  }

  return <Outlet />;
}
