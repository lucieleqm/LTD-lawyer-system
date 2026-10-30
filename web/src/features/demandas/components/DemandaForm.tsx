import { Form } from '@/components/ui/form/Form';
import { Input } from '@/components/ui/form/Input';
import { Button, Grid, Stack, Text } from '@mantine/core';
import { useZodForm } from '@/hooks/useZodForm';
import { FormSection } from '@/components/ui/form/FormSection';
import {
  AtualizarDemandaRequest,
  CriarDemandaRequest,
  atualizarDemandaSchema,
  criarDemandaSchema,
} from '../schema';
import { AsyncSelect } from '@/components/ui/form/AsyncSelect';
import { estagiariosService } from '@/features/estagiarios/services/estagiariosService';
import { advogadosService } from '@/features/advogados/services/advogadosService';
import { professoresService } from '@/features/professores/services/professoresService';
import { Select } from '@/components/ui/form/Select';
import { NumberInput } from '@/components/ui/form/NumberInput';
import { Textarea } from '@/components/ui/form/TextArea';

interface DemandaFormProps {
  valoresIniciais?: Partial<CriarDemandaRequest & AtualizarDemandaRequest>;
  onSubmit: (dados: any) => Promise<void>;
  modo: 'criar' | 'editar';
  opcoesEtapas?: Array<{ value: string; label: string }>;
}

export function DemandaForm({
  valoresIniciais,
  onSubmit,
  modo,
  opcoesEtapas = [],
}: DemandaFormProps) {
  const schema = modo === 'criar' ? criarDemandaSchema : atualizarDemandaSchema;

  const methods = useZodForm(schema, {
    defaultValues: valoresIniciais,
  });

  const { isSubmitting } = methods.formState;
  const prazoDocumentos = methods.watch('prazoDocumentos');
  const diasAdicionais = methods.watch('diasAdicionais');

  let prazoFinal = '';

  if (prazoDocumentos) {
    // O 'T12:00:00' evita bugs de fuso horário que fazem a data voltar 1 dia
    const data = new Date(`${prazoDocumentos}T12:00:00`);

    if (!isNaN(data.getTime())) {
      const dias = Number(diasAdicionais) || 0;
      data.setDate(data.getDate() + dias);
      prazoFinal = new Intl.DateTimeFormat('pt-BR').format(data);
    }
  }

  const textoBotao =
    modo === 'criar' ? 'Adicionar Demanda' : 'Salvar Alterações';

  return (
    <Form methods={methods} onSubmit={onSubmit}>
      <Stack gap="lg">
        <FormSection legend="Dados da Demanda">
          <Grid>
            <Grid.Col span={{ base: 12, md: 4 }}>
              <AsyncSelect
                name="advogadoId"
                label="Advogado Responsável"
                fetchData={advogadosService.listarOpcoes}
                withAsterisk
              />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 4 }}>
              <AsyncSelect
                name="estagiarioId"
                label="Estagiário Responsável"
                fetchData={estagiariosService.listarOpcoes}
                withAsterisk
              />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 4 }}>
              <AsyncSelect
                name="professorId"
                label="Professor Orientador"
                fetchData={professoresService.listarOpcoes}
                withAsterisk
              />
            </Grid.Col>

            <Grid.Col span={12}>
              <Textarea
                name="descricao"
                label="Descrição da Demanda"
                withAsterisk
              />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 6 }}>
              <NumberInput
                name="diasAdicionais"
                label="Dias Adicionais"
                min={0}
                withAsterisk
              />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 6 }}>
              <Input
                name="prazoDocumentos"
                label="Prazo para Documentos"
                type="date"
                withAsterisk
              />
            </Grid.Col>

            {prazoFinal && (
              <Grid.Col span={12}>
                <Text size="sm" c="dimmed" mt="-sm">
                  Prazo final (com dias adicionais):{' '}
                  <Text span fw={600} c="blue">
                    {prazoFinal}
                  </Text>
                </Text>
              </Grid.Col>
            )}

            <Grid.Col span={12}>
              <Select
                name="etapaAtual"
                label="Etapa Atual"
                options={opcoesEtapas}
                withAsterisk
              />
            </Grid.Col>
          </Grid>
        </FormSection>

        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting ? 'Salvando...' : textoBotao}
        </Button>
      </Stack>
    </Form>
  );
}
