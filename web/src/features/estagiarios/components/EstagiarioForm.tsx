import { Form } from '@/components/ui/form/Form';
import { Input } from '@/components/ui/form/Input';
import { Select } from '@/components/ui/form/Select';

import { Button, Grid, Stack } from '@mantine/core';
import { useZodForm } from '@/hooks/useZodForm';
import {
  AtualizarEstagiarioRequest,
  atualizarEstagiarioSchema,
  CriarEstagiarioRequest,
  criarEstagiarioSchema,
} from '../schema';
import { FormSection } from '@/components/ui/form/FormSection';
import { MaskInput } from '@/components/ui/form/MaskInput';

interface EstagiarioFormProps {
  valoresIniciais?: Partial<
    CriarEstagiarioRequest & AtualizarEstagiarioRequest
  >;
  onSubmit: (dados: any) => Promise<void>;
  modo: 'criar' | 'editar';
  periodos: Array<{ value: string; label: string }>;
}

export function EstagiarioForm({
  valoresIniciais,
  onSubmit,
  modo,
  periodos,
}: EstagiarioFormProps) {
  const schema =
    modo === 'criar' ? criarEstagiarioSchema : atualizarEstagiarioSchema;

  const methods = useZodForm(schema, {
    defaultValues: valoresIniciais,
  });

  const { isSubmitting } = methods.formState;

  return (
    <Form methods={methods} onSubmit={onSubmit}>
      <Stack gap="lg">
        <FormSection legend="Dados Pessoais">
          <Grid>
            <Grid.Col span={12}>
              <Input name="nome" label="Nome Completo" withAsterisk />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 8 }}>
              <Input name="email" label="Email" type="email" withAsterisk />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 4 }}>
              <MaskInput
                name="telefone"
                label="Telefone"
                mask="(99) 99999-9999"
                placeholder="(__) _____-____"
              />
            </Grid.Col>
          </Grid>
        </FormSection>

        <FormSection legend="Dados Acadêmicos">
          <Grid>
            <Grid.Col span={{ base: 12, md: 4 }}>
              <Input name="matricula" label="Matrícula" withAsterisk />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 8 }}>
              <Select
                name="periodoEstagio"
                label="Período do Estágio"
                options={periodos}
                withAsterisk
              />
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
              ? 'Cadastrar Estagiário'
              : 'Salvar Alterações'}
        </Button>
      </Stack>
    </Form>
  );
}
