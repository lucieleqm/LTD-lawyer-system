import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Anchor, Button, Title } from '@mantine/core';

import { AuthLayout } from '@/components/layouts/AuthLayout';
import { Form } from '@/components/ui/form/Form';
import { Input } from '@/components/ui/form/Input';
import { useZodForm } from '@/hooks/useZodForm';
import { paths } from '@/routes/paths';

import { useAuth } from '../hooks/useAuth';
import { LoginRequest, loginSchema } from '../schema';
import { IconLock, IconMail } from '@tabler/icons-react';
import { PasswordInput } from '@/components/ui/form/PasswordInput';

export default function LoginPage() {
  const methods = useZodForm(loginSchema);
  const { login } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const from =
    (location.state as { from?: Location })?.from?.pathname || paths.home;

  const handleSalvar = async (dados: LoginRequest) => {
    await login(dados);
    navigate(from, { replace: true });
  };

  return (
    <AuthLayout>
      <Form methods={methods} onSubmit={handleSalvar}>
        <Title order={4} ta="center" mb={4}>
          Bem-vindo(a) de volta
        </Title>
        <Input
          name="login"
          label="Email"
          leftSection={<IconMail size={16} />}
        />
        <PasswordInput
          name="password"
          label="Senha"
          type="password"
          leftSection={<IconLock size={16} />}
        />

        <Button
          type="submit"
          fullWidth
          mt="sm"
          loading={methods.formState.isSubmitting}
        >
          Entrar
        </Button>

        <Anchor
          component={Link}
          to={paths.esqueciSenha}
          size="sm"
          ta="center"
          display="block"
          mt="md"
        >
          Esqueci minha senha
        </Anchor>
      </Form>
    </AuthLayout>
  );
}
