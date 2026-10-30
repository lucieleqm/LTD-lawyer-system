import { Select } from '@mantine/core';
import {
  USUARIO_STATUS,
  UsuarioStatus,
  usuarioStatusLabel,
} from '@/constants/usuarioStatus';

interface UsuarioStatusSelectProps {
  value: UsuarioStatus;
  onChange: (novoStatus: UsuarioStatus) => void;
  disabled?: boolean;
}

export function UsuarioStatusSelect({
  value,
  onChange,
  disabled,
}: UsuarioStatusSelectProps) {
  return (
    <Select
      label="Status de acesso"
      checkIconPosition="right"
      value={value}
      onChange={(v) => v && onChange(v as UsuarioStatus)}
      data={Object.values(USUARIO_STATUS).map((s) => ({
        value: s,
        label: usuarioStatusLabel[s],
      }))}
      disabled={disabled}
      w={200}
      styles={{
        label: {
          color: 'var(--mantine-color-dimmed)',
          fontWeight: 400,
          fontSize: 'var(--mantine-font-size-sm)',
        },
      }}
    />
  );
}
