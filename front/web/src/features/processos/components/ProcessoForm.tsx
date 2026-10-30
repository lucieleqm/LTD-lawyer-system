import { useZodForm } from '@/hooks/useZodForm';
import {
  AtualizarProcessoRequest,
  atualizarProcessoSchema,
  CriarProcessoRequest,
  criarProcessoSchema,
} from '../schema';
import { Form } from '@/components/ui/form/Form';
import { Button, Grid, Stack } from '@mantine/core';
import { FormSection } from '@/components/ui/form/FormSection';
import { AsyncSelect } from '@/components/ui/form/AsyncSelect';
import { advogadosService } from '@/features/advogados/services/advogadosService';
import { estagiariosService } from '@/features/estagiarios/services/estagiariosService';
import { Textarea } from '@/components/ui/form/TextArea';
import { Input } from '@/components/ui/form/Input';
import { Select } from '@/components/ui/form/Select';
import { assistidosService } from '@/features/assistidos/services/assistidosService';
import { PartesEnvolvidasSection } from './PartesEnvolvidasSection';

interface ProcessoFormProps {
  valoresIniciais?: Partial<CriarProcessoRequest & AtualizarProcessoRequest>;
  onSubmit: (dados: any) => Promise<void>;
  modo: 'criar' | 'editar';
  opcoesAreasDireito?: Array<{ value: string; label: string }>;
  opcoesTribunais?: Array<{ value: string; label: string }>;
  opcoesPapeis?: Array<{ value: string; label: string }>;
}

export function ProcessoForm({
  valoresIniciais,
  onSubmit,
  modo,
  opcoesAreasDireito = [],
  opcoesTribunais = [],
  opcoesPapeis = [],
}: ProcessoFormProps) {
  const schema =
    modo === 'criar' ? criarProcessoSchema : atualizarProcessoSchema;

  const methods = useZodForm(schema, {
    defaultValues: valoresIniciais,
  });

  const { isSubmitting } = methods.formState;

  const textoBotao =
    modo === 'criar' ? 'Adicionar Processo' : 'Salvar Alterações';


  return (
    <Form methods={methods} onSubmit={onSubmit}>
      <Stack gap="lg">
        <FormSection legend="Dados do Processo">
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
                name="assistidoId"
                label="Assistido"
                fetchData={assistidosService.listarOpcoes}
                withAsterisk
              />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 12 }}>
              <Input name="numeroProcessoPje" label="Número do Processo PJE" />
            </Grid.Col>

            <Grid.Col span={12}>
              <Textarea name="assunto" label="Assunto" />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 4 }}>
              <Input name="vara" label="Vara" />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 4 }}>
              <Select
                name="areaDireito"
                label="Área do Direito"
                options={opcoesAreasDireito}
              />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 4 }}>
              <Select
                name="tribunal"
                label="Tribunal"
                options={opcoesTribunais}
              />
            </Grid.Col>

            <Grid.Col span={{ base: 12, md: 4 }}>
              <Input name="prazoFinal" label="Prazo Final" type="date" />
            </Grid.Col>
          </Grid>
        </FormSection>

        <PartesEnvolvidasSection opcoesPapeis={opcoesPapeis} />

        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting ? 'Salvando...' : textoBotao}
        </Button>
      </Stack>
    </Form>
  );
}
