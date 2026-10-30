import { useEffect, useState } from 'react';
import { Modal, Stack, Text, Center, Loader, Button } from '@mantine/core';
import { useZodForm } from '@/hooks/useZodForm';
import { Form } from '@/components/ui/form/Form';
import { Select } from '@/components/ui/form/Select';
import { Textarea } from '@/components/ui/form/TextArea';
import { Input } from '@/components/ui/form/Input';
import { notifications } from '@mantine/notifications';
import { demandasService } from '../services/demandasService';
import { criarTramitacaoSchema, CriarTramitacaoRequest } from '../schema';
import { TramitacaoDisponivel } from '../types';

interface AdicionarTramitacaoModalProps {
  opened: boolean;
  onClose: () => void;
  demandaId: number;
  onSuccess: () => void;
}

export function AdicionarTramitacaoModal({
  opened,
  onClose,
  demandaId,
  onSuccess,
}: AdicionarTramitacaoModalProps) {
  const [acoesDisponiveis, setAcoesDisponiveis] = useState<
    TramitacaoDisponivel[]
  >([]);
  const [isLoadingAcoes, setIsLoadingAcoes] = useState(true);
  const methods = useZodForm(criarTramitacaoSchema);

  useEffect(() => {
    if (!opened) return;

    setIsLoadingAcoes(true);
    methods.reset();
    demandasService
      .listarAcoesDisponiveis(demandaId)
      .then(setAcoesDisponiveis)
      .finally(() => setIsLoadingAcoes(false));
  }, [opened, demandaId]);

  async function handleSalvar(dados: CriarTramitacaoRequest) {
    await demandasService.tramitar(demandaId, dados);
    notifications.show({
      message: 'Tramitação registrada com sucesso',
      color: 'teal',
    });
    onClose();
    onSuccess();
  }

  return (
    <Modal opened={opened} onClose={onClose} title="Nova Tramitação" size="md">
      {isLoadingAcoes ? (
        <Center py="xl">
          <Loader color="institucional" size="sm" />
        </Center>
      ) : acoesDisponiveis.length === 0 ? (
        <Text c="dimmed" size="sm" ta="center" py="xl">
          Não há ações disponíveis para o seu perfil nesta etapa da demanda.
        </Text>
      ) : (
        <Form methods={methods} onSubmit={handleSalvar}>
          <Stack gap="md">
            <Select
              name="tipoTramitacao"
              label="Tipo de ação"
              options={acoesDisponiveis.map((a) => ({
                value: a.tipo,
                label: a.descricao,
              }))}
            />

            <Textarea name="observacoes" label="Observações" minRows={3} />

            <Input
              name="linkAnexo"
              label="Link do anexo (opcional)"
              placeholder="https://..."
            />

            <Button
              type="submit"
              fullWidth
              loading={methods.formState.isSubmitting}
            >
              Registrar Tramitação
            </Button>
          </Stack>
        </Form>
      )}
    </Modal>
  );
}
