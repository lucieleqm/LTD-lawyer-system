import { Form } from '@/components/ui/form/Form';
import { Input } from '@/components/ui/form/Input';

import { Button, Grid, Stack } from '@mantine/core';
import { useZodForm } from '@/hooks/useZodForm';
import { orgaoJulgadorSchema, OrgaoJulgadorRequest } from '../schema';

import { FormSection } from '@/components/ui/form/FormSection';
import { AsyncSelect } from '@/components/ui/form/AsyncSelect';
import { tribunaisService } from '@/features/tribunais/services/tribunaisService';
import { NumberInput } from '@/components/ui/form/NumberInput';

interface OrgaoJulgadorFormProps {
  valoresIniciais?: Partial<OrgaoJulgadorRequest>;
  onSubmit: (dados: any) => Promise<void>;
  modo: 'criar' | 'editar';
}

export function OrgaoJulgadorForm({
  valoresIniciais,
  onSubmit,
  modo,
}: OrgaoJulgadorFormProps) {
  const schema = modo === 'criar' ? orgaoJulgadorSchema : orgaoJulgadorSchema;

  const methods = useZodForm(schema, {
    defaultValues: valoresIniciais,
  });

  const { isSubmitting } = methods.formState;

  return (
    <Form methods={methods} onSubmit={onSubmit}>
      <Stack gap="lg">
        <FormSection legend="Informações do Órgão Julgador">
          <Grid>
            <Grid.Col span={4}>
              <Input name="nome" label="Nome" withAsterisk />
            </Grid.Col>

            <Grid.Col span={4}>
              <NumberInput name="instancia" label="Instância" withAsterisk />
            </Grid.Col>

            <Grid.Col span={4}>
              <AsyncSelect
                name="tribunalId"
                label="Tribunal"
                fetchData={tribunaisService.listarOpcoes}
                withAsterisk
              />
            </Grid.Col>
          </Grid>
        </FormSection>

        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting
            ? 'Salvando...'
            : modo === 'criar'
              ? 'Cadastrar'
              : 'Salvar Alterações'}
        </Button>
      </Stack>
    </Form>
  );
}
