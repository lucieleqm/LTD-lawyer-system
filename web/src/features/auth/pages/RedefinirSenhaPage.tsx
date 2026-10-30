import { useNavigate, useSearchParams, Link } from 'react-router-dom';
import { Paper, Title, Text, Stack, Anchor, Center, Button } from '@mantine/core';
import { Form } from '@/components/ui/form/Form';
import { Input } from '@/components/ui/form/Input';
import { notifications } from '@mantine/notifications';
import { useZodForm } from '@/hooks/useZodForm';
import { authService } from '../services/authService';
import { redefinirSenhaSchema, RedefinirSenhaRequest } from '../schema';
import { paths } from '@/routes/paths';

export default function RedefinirSenhaPage() {
  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');
  const navigate = useNavigate();
  const methods = useZodForm(redefinirSenhaSchema);

  async function handleSalvar(dados: RedefinirSenhaRequest) {
    if (!token) return;
    await authService.redefinirSenha(token, dados);
    notifications.show({
      message: 'Senha redefinida com sucesso. Faça login novamente.',
      color: 'teal',
    });
    navigate(paths.login, { replace: true });
  }

  if (!token) {
    return (
      <Center mih="100vh">
        <Text c="dimmed">Link inválido ou expirado.</Text>
      </Center>
    );
  }

  return (
    <Stack align="center" justify="center" mih="100vh" bg="gray.0">
      <Paper withBorder shadow="sm" p="xl" w={380} radius="md">
        <Title order={3} ta="center" mb="xs">
          Redefinir senha
        </Title>
        <Text size="sm" c="dimmed" ta="center" mb="md">
          Escolha uma nova senha para acessar sua conta.
        </Text>

        <Form methods={methods} onSubmit={handleSalvar}>
          <Input name="novaSenha" label="Nova senha" type="password" />
          <Input
            name="confirmarSenha"
            label="Confirmar senha"
            type="password"
          />
          <Button type="submit" fullWidth mt="sm">
            Redefinir senha
          </Button>
        </Form>

        <Anchor
          component={Link}
          to={paths.login}
          size="sm"
          ta="center"
          display="block"
          mt="md"
        >
          Voltar ao login
        </Anchor>
      </Paper>
    </Stack>
  );
}
