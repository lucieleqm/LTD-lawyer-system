import { Form } from '@/components/ui/form/Form';
import { Input } from '@/components/ui/form/Input';

import { Button, Grid, Stack } from '@mantine/core';
import { useZodForm } from '@/hooks/useZodForm';
import { EnderecoFields } from '@/components/ui/form/EnderecoFields';
import { FormSection } from '@/components/ui/form/FormSection';
import {
  AtualizarAssistidoRequest,
  atualizarAssistidoSchema,
  CriarAssistidoRequest,
  criarAssistidoSchema,
} from '../schema';
import { Select } from '@/components/ui/form/Select';

interface AssistidoFormProps {
  valoresIniciais?: Partial<CriarAssistidoRequest & AtualizarAssistidoRequest>;
  onSubmit: (dados: any) => Promise<void>;
  modo: 'criar' | 'editar';
  estadosCivis: Array<{ value: string; label: string }>;
}

export function AssistidoForm({
  valoresIniciais,
  onSubmit,
  modo,
  estadosCivis,
}: AssistidoFormProps) {
  const schema =
    modo === 'criar' ? criarAssistidoSchema : atualizarAssistidoSchema;
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
              <Input name="email" label="Email" type="email" />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 4 }}>
              <Input name="telefone" label="Telefone" />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 4 }}>
              <Input name="matricula" label="Matrícula" withAsterisk />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 4 }}>
              <Input name="profissao" label="Profissão" />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 4 }}>
              <Input name="nacionalidade" label="Nacionalidade" />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 4 }}>
              <Input name="naturalidade" label="Naturalidade" />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 8 }}>
              <Select
                name="estadoCivil"
                label="Estado Civil"
                options={estadosCivis}
              />
            </Grid.Col>
          </Grid>
        </FormSection>

        <EnderecoFields />

        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting
            ? 'Salvando...'
            : modo === 'criar'
              ? 'Cadastrar Assistido'
              : 'Salvar Alterações'}
        </Button>
      </Stack>
    </Form>
  );
}
