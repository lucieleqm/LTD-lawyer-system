import { Fieldset, Grid } from '@mantine/core';
import { Input } from './Input';
import { FormSection } from './FormSection';

export function EnderecoFields() {
  return (
    <FormSection legend="Endereço">
      <Grid>
        <Grid.Col span={8}>
          <Input name="endereco.logradouro" label="Logradouro" />
        </Grid.Col>

        <Grid.Col span={4}>
          <Input name="endereco.numero" label="Número" />
        </Grid.Col>

        <Grid.Col span={6}>
          <Input name="endereco.bairro" label="Bairro" />
        </Grid.Col>

        <Grid.Col span={6}>
          <Input name="endereco.cidade" label="Cidade" />
        </Grid.Col>

        <Grid.Col span={8}>
          <Input
            name="endereco.complemento"
            label="Complemento"
            placeholder="Bloco 15, Apto 202"
          />
        </Grid.Col>

        <Grid.Col span={4}>
          <Input name="endereco.cep" label="CEP" />
        </Grid.Col>
      </Grid>
    </FormSection>
  );
}
