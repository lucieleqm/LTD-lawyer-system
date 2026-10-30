import { Form } from '@/components/ui/form/Form';
import { Input } from '@/components/ui/form/Input';

import { Button, Grid, Stack } from '@mantine/core';
import { useZodForm } from '@/hooks/useZodForm';
import {
  CriarAdministrativoRequest,
  criarAdministrativoSchema,
} from '../schema';

import { FormSection } from '@/components/ui/form/FormSection';
import { Select } from '@/components/ui/form/Select';
import { atualizarAdvogadoSchema } from '@/features/advogados/schema';
import { OPCOES_SELECT_ADMINISTRATIVO } from '../constants';

interface AdministrativoFormProps {
  valoresIniciais?: Partial<CriarAdministrativoRequest>;
  onSubmit: (dados: any) => Promise<void>;
  modo: 'criar' | 'editar';
}

export function AdministrativoForm({
  valoresIniciais,
  onSubmit,
  modo,
}: AdministrativoFormProps) {
  const schema =
    modo === 'criar' ? criarAdministrativoSchema : atualizarAdvogadoSchema;

  const methods = useZodForm(schema, {
    defaultValues: valoresIniciais,
  });

  const { isSubmitting } = methods.formState;

  return (
    <Form methods={methods} onSubmit={onSubmit}>
      <Stack gap="lg">
        <FormSection legend="Dados Pessoais">
          <Grid>
            <Grid.Col>
              <Input name="nome" label="Nome Completo" withAsterisk />
            </Grid.Col>

            <Grid.Col span={8}>
              <Input name="email" label="Email" type="email" withAsterisk />
            </Grid.Col>

            <Grid.Col span={4}>
              <Input name="telefone" label="Telefone" />
            </Grid.Col>
          </Grid>
        </FormSection>

        <FormSection legend="Dados Administrativos">
          <Grid>
            <Grid.Col span={{ base: 12, md: 8 }}>
              <Select
                name="role"
                label="Cargo"
                options={OPCOES_SELECT_ADMINISTRATIVO}
                withAsterisk
              />
            </Grid.Col>
          </Grid>
        </FormSection>

        <Input name="senha" label="Senha" type="password" />

        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting
            ? 'Salvando...'
            : modo === 'criar'
              ? 'Cadastrar Administrador'
              : 'Salvar Alterações'}
        </Button>
      </Stack>
    </Form>
  );
}
