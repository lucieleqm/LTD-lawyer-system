import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '@/features/auth/hooks/useAuth';
import { paths } from './paths';
import { Center, Loader } from '@mantine/core';

export function PublicRoute() {
  const { isAuthenticated, isLoading } = useAuth();

  if (isLoading) {
    return (
      <Center h="100vh" bg="gray.0">
        <Loader color="institucional" type="dots" />
      </Center>
    );
  }

  return isAuthenticated ? <Navigate to={paths.home} replace /> : <Outlet />;
}
