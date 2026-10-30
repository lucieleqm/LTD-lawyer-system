import { modals, OpenConfirmModal } from '@mantine/modals';
import { notifications } from '@mantine/notifications';
import { Text } from '@mantine/core';
import {
  UsuarioStatus,
  usuarioStatusColor,
  usuarioStatusLabel,
} from '@/constants/usuarioStatus';
import { usuariosService } from '@/features/usuarios/services/usuariosService';

const MENSAGENS_ALTERACAO_STATUS: Record<
  UsuarioStatus,
  { title: string; body: string; success: string }
> = {
  ATIVO: {
    title: 'Reativar acesso',
    body: 'O usuário voltará a ter acesso ao sistema e suas funções serão restabelecidas normalmente. Deseja continuar?',
    success: 'Acesso reativado com sucesso.',
  },
  INATIVO: {
    title: 'Inativar usuário',
    body: 'O perfil será arquivado. O usuário perderá o acesso ao sistema e não estará mais disponível para novas operações ou novos vínculos. Deseja continuar?',
    success: 'Usuário inativado com sucesso.',
  },
  BLOQUEADO: {
    title: 'Bloquear acesso',
    body: 'O usuário perderá o acesso ao sistema temporariamente. Seu cadastro continuará visível e disponível para vínculos internos, mas ele não poderá fazer login. Deseja continuar?',
    success: 'Acesso bloqueado com sucesso.',
  },
};

interface UseChangeUsuarioStatusOptions {
  onSuccess: () => void;
}

export function useChangeUsuarioStatus({
  onSuccess,
}: UseChangeUsuarioStatusOptions) {
  function alterar(id: number, novoStatus: UsuarioStatus) {
    const messages = MENSAGENS_ALTERACAO_STATUS[novoStatus];
    const corDoBotao = usuarioStatusColor[novoStatus];

    modals.openConfirmModal({
      title: messages.title,
      labels: { confirm: 'Confirmar', cancel: 'Cancelar' },
      children: <Text size="sm">{messages.body}</Text>,
      confirmProps: { color: corDoBotao },

      onConfirm: async () => {
        await usuariosService.alterarStatus(id, novoStatus);
        notifications.show({
          message: messages.success,
          color: 'teal',
        });
        onSuccess();
      },
    });
  }

  return { alterar };
}
