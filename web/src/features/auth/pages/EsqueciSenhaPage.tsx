import { useState } from 'react';
import { Link } from 'react-router-dom';
import { Button, Title, Text, Anchor } from '@mantine/core';
import { Form } from '@/components/ui/form/Form';
import { Input } from '@/components/ui/form/Input';
import { useZodForm } from '@/hooks/useZodForm';
import { authService } from '../services/authService';
import { esqueciSenhaSchema, EsqueciSenhaRequest } from '../schema';
import { paths } from '@/routes/paths';
import { AuthLayout } from '@/components/layouts/AuthLayout';
import { IconMail } from '@tabler/icons-react';

export default function EsqueciSenhaPage() {
  const methods = useZodForm(esqueciSenhaSchema);
  const [enviado, setEnviado] = useState(false);

  async function handleSalvar(dados: EsqueciSenhaRequest) {
    await authService.esqueciSenha(dados);
    setEnviado(true);
  }

  return (
    <AuthLayout>
      <Title order={3} ta="center" mb="xs">
        Esqueci minha senha
      </Title>

      {enviado ? (
        <Text size="sm" c="dimmed" ta="center">
          Se o e-mail informado estiver cadastrado, você receberá um link para
          redefinir sua senha em instantes.
        </Text>
      ) : (
        <>
          <Text size="sm" c="dimmed" ta="center" mb="md">
            Informe seu e-mail de login para receber o link de redefinição.
          </Text>
          <Form methods={methods} onSubmit={handleSalvar}>
            <Input
              name="login"
              label="E-mail"
              leftSection={<IconMail size={16} />}
            />
            <Button type="submit" fullWidth mt="sm">
              Enviar link
            </Button>
          </Form>
        </>
      )}

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
    </AuthLayout>
  );
}
