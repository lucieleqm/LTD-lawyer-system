import { Form } from '@/components/ui/form/Form';
import { Input } from '@/components/ui/form/Input';

import { Button, Grid, Stack } from '@mantine/core';
import { useZodForm } from '@/hooks/useZodForm';
import {
  atualizarTribunalSchema,
  CriarTribunalRequest,
  criarTribunalSchema,
} from '../schema';

import { FormSection } from '@/components/ui/form/FormSection';
import { esferaJusticaLabel } from '../constants';
import { Select } from '@/components/ui/form/Select';

interface TribunalFormProps {
  valoresIniciais?: Partial<CriarTribunalRequest>;
  onSubmit: (dados: any) => Promise<void>;
  modo: 'criar' | 'editar';
}

export function TribunalForm({
  valoresIniciais,
  onSubmit,
  modo,
}: TribunalFormProps) {
  const schema =
    modo === 'criar' ? criarTribunalSchema : atualizarTribunalSchema;

  const methods = useZodForm(schema, {
    defaultValues: valoresIniciais,
  });

  const { isSubmitting } = methods.formState;

  return (
    <Form methods={methods} onSubmit={onSubmit}>
      <Stack gap="lg">
        <FormSection legend="Informações do Tribunal">
          <Grid>
            <Grid.Col span={4}>
              <Select
                name="esfera"
                label="Esfera"
                options={Object.entries(esferaJusticaLabel).map(
                  ([value, label]) => ({ value, label }),
                )}
                withAsterisk
              />
            </Grid.Col>

            <Grid.Col span={4}>
              <Input name="nome" label="Nome" withAsterisk />
            </Grid.Col>
            
            <Grid.Col span={4}>
              <Input name="sigla" label="Sigla" withAsterisk />
            </Grid.Col>

          </Grid>
        </FormSection>

        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting
            ? 'Salvando...'
            : modo === 'criar'
              ? 'Cadastrar Tribunal'
              : 'Salvar Alterações'}
        </Button>
      </Stack>
    </Form>
  );
}
