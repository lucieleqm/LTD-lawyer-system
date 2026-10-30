import { Form } from '@/components/ui/form/Form';
import { Input } from '@/components/ui/form/Input';

import { Button, Grid, Stack } from '@mantine/core';
import { useZodForm } from '@/hooks/useZodForm';
import {
  CriarAdvogadoRequest,
  AtualizarAdvogadoRequest,
  criarAdvogadoSchema,
  atualizarAdvogadoSchema,
} from '../schema';
import { EnderecoFields } from '@/components/ui/form/EnderecoFields';
import { FormSection } from '@/components/ui/form/FormSection';

interface AdvogadoFormProps {
  valoresIniciais?: Partial<CriarAdvogadoRequest & AtualizarAdvogadoRequest>;
  onSubmit: (dados: any) => Promise<void>;
  modo: 'criar' | 'editar';
}

export function AdvogadoForm({
  valoresIniciais,
  onSubmit,
  modo,
}: AdvogadoFormProps) {
  const schema =
    modo === 'criar' ? criarAdvogadoSchema : atualizarAdvogadoSchema;

  const methods = useZodForm(schema, {
    defaultValues: {
      endereco: {},
      ...valoresIniciais,
    },
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

            <Grid.Col span={4}>
              <Input
                name="dataNascimento"
                label="Data de Nascimento"
                type="date"
              />
            </Grid.Col>
          </Grid>
        </FormSection>

        <EnderecoFields />

        <Input name="senha" label="Senha" type="password" />

        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting
            ? 'Salvando...'
            : modo === 'criar'
              ? 'Cadastrar Advogado'
              : 'Salvar Alterações'}
        </Button>
      </Stack>
    </Form>
  );
}
