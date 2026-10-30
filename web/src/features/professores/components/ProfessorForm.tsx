import { Form } from '@/components/ui/form/Form';
import { Input } from '@/components/ui/form/Input';

import { Button, Grid, Stack } from '@mantine/core';
import { useZodForm } from '@/hooks/useZodForm';
import {
  atualizarProfessorSchema,
  CriarProfessorRequest,
  criarProfessorSchema,
} from '../schema';

import { FormSection } from '@/components/ui/form/FormSection';

interface ProfessorFormProps {
  valoresIniciais?: Partial<CriarProfessorRequest>;
  onSubmit: (dados: any) => Promise<void>;
  modo: 'criar' | 'editar';
}

export function ProfessorForm({
  valoresIniciais,
  onSubmit,
  modo,
}: ProfessorFormProps) {
  const schema =
    modo === 'criar' ? criarProfessorSchema : atualizarProfessorSchema;

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

            {modo === 'criar' && (
              <Grid.Col span={8}>
                <Input name="email" label="Email" type="email" withAsterisk />
              </Grid.Col>
            )}

            <Grid.Col span={4}>
              <Input name="telefone" label="Telefone" />
            </Grid.Col>
          </Grid>
        </FormSection>

        {modo === 'criar' && (
          <Input name="senha" label="Senha" type="password" />
        )}

        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting
            ? 'Salvando...'
            : modo === 'criar'
              ? 'Cadastrar Professor'
              : 'Salvar Alterações'}
        </Button>
      </Stack>
    </Form>
  );
}
