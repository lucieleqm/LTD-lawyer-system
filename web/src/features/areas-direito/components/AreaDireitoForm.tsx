import { Form } from '@/components/ui/form/Form';
import { Input } from '@/components/ui/form/Input';

import { Button, Grid, Stack } from '@mantine/core';
import { useZodForm } from '@/hooks/useZodForm';
import { areaDireitoSchema, AreaDireitoRequest } from '../schema';

import { FormSection } from '@/components/ui/form/FormSection';

interface AreaDireitoFormProps {
  valoresIniciais?: Partial<AreaDireitoRequest>;
  onSubmit: (dados: any) => Promise<void>;
  modo: 'criar' | 'editar';
}

export function AreaDireitoForm({
  valoresIniciais,
  onSubmit,
  modo,
}: AreaDireitoFormProps) {
  const schema =
    modo === 'criar' ? areaDireitoSchema : areaDireitoSchema;

  const methods = useZodForm(schema, {
    defaultValues: valoresIniciais,
  });

  const { isSubmitting } = methods.formState;

  return (
    <Form methods={methods} onSubmit={onSubmit}>
      <Stack gap="lg">
        <FormSection legend="Informações da Área do Direito">
          <Grid>
    
            <Grid.Col span={4}>
              <Input name="nome" label="Nome" withAsterisk />
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
