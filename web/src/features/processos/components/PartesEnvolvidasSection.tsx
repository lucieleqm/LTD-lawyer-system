import { useFieldArray, useWatch, useFormContext } from 'react-hook-form';
import { Box, Grid, Stack, Text, Button } from '@mantine/core';
import { FormSection } from '@/components/ui/form/FormSection';
import { Input } from '@/components/ui/form/Input';
import { Select } from '@/components/ui/form/Select';
import { PAPEL_NO_PROCESSO } from '../constants';

interface PartesEnvolvidasSectionProps {
  opcoesPapeis: Array<{ value: string; label: string }>;
}

export function PartesEnvolvidasSection({
  opcoesPapeis,
}: PartesEnvolvidasSectionProps) {
  const { control } = useFormContext();

  const { fields, append, remove } = useFieldArray({
    control,
    name: 'partesEnvolvidas',
  });

  const partes = useWatch({ control, name: 'partesEnvolvidas' }) || [];

  return (
    <FormSection legend="Partes Envolvidas (Opcional)">
      <Stack gap="md">
        {fields.length === 0 && (
          <Text c="dimmed" size="sm">
            Nenhuma parte envolvida adicionada ainda.
          </Text>
        )}

        {fields.map((field, index) => {
          // Verifica se a parte atual selecionou 'OUTROS'
          const papelSelecionado = partes[index]?.papel;

          return (
            <Box
              key={field.id}
              p="md"
              bd="1px solid var(--mantine-color-gray-3)"
              style={{ borderRadius: 8 }}
            >
              <Grid align="flex-start">
                <Grid.Col span={{ base: 12, md: 5 }}>
                  <Input
                    name={`partesEnvolvidas.${index}.nome`}
                    label="Nome"
                    placeholder="Nome da parte"
                    withAsterisk
                  />
                </Grid.Col>

                <Grid.Col span={{ base: 12, md: 4 }}>
                  <Select
                    name={`partesEnvolvidas.${index}.papel`}
                    label="Papel no Processo"
                    options={opcoesPapeis}
                    withAsterisk
                  />
                </Grid.Col>

                <Grid.Col span={{ base: 12, md: 3 }}>
                  <Button
                    color="red"
                    variant="subtle"
                    mt={24}
                    onClick={() => remove(index)}
                    fullWidth
                  >
                    Remover Parte
                  </Button>
                </Grid.Col>

                {papelSelecionado === PAPEL_NO_PROCESSO.OUTROS && (
                  <Grid.Col span={12}>
                    <Input
                      name={`partesEnvolvidas.${index}.especificacaoPapel`}
                      label="Especifique o Papel"
                      placeholder="Ex: Curador Especial"
                      withAsterisk
                    />
                  </Grid.Col>
                )}
              </Grid>
            </Box>
          );
        })}

        <Box>
          <Button
            variant="outline"
            onClick={() =>
              append({ nome: '', papel: '', especificacaoPapel: '' })
            }
          >
            + Adicionar Parte Envolvida
          </Button>
        </Box>
      </Stack>
    </FormSection>
  );
}
