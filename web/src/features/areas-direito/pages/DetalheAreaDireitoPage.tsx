import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { AreaDireito } from '../types';
import { areasDireitoService } from '../services/areasDireitoService';
import { LoadingState } from '@/components/ui/LoadingState';
import { EmptyState } from '@/components/ui/EmptyState';
import {
  DetailField,
  DetailHeader,
  DetailSection,
  DetailShell,
} from '@/components/ui/detail';
import { paths } from '@/routes/paths';
import { EditButton } from '@/components/ui/EditButton';
import { usePermission } from '@/features/auth/hooks/usePermission';
import { formatarData } from '@/utils/formatters';
import { situacaoRegistro } from '@/constants/situacao';

export function DetalheAreaDireitoPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const podeEditar = usePermission('areasDireito:editar');

  const [isLoading, setIsLoading] = useState(true);
  const [areaDireito, setAreaDireito] = useState<AreaDireito | null>(null);

  function carregar() {
    if (!id) return;
    setIsLoading(true);
    areasDireitoService
      .buscarPorId(Number(id))
      .then(setAreaDireito)
      .finally(() => setIsLoading(false));
  }

  useEffect(carregar, [id]);

  if (isLoading) return <LoadingState />;
  if (!areaDireito) return <EmptyState />;

  return (
    <DetailShell>
      <DetailHeader
        title={areaDireito.nome}
        breadcrumbs={[
          { label: 'Início', link: paths.home },
          { label: 'Áreas do Direito', link: paths.areasDireito.lista },
          { label: areaDireito.nome },
        ]}
        actions={[
          <>
            {podeEditar && (
              <EditButton
                onClick={() =>
                  navigate(paths.areasDireito.editar(areaDireito.id))
                }
              />
            )}
          </>,
        ]}
      />
      <DetailSection title="Informações Gerais">
        <DetailField
          label="Situação"
          value={situacaoRegistro(areaDireito.ativo).label}
        />
        <DetailField
          label="Criado em"
          value={formatarData(areaDireito.criadoEm)}
        />
        <DetailField label="Criado por" value={areaDireito.criadoPor} />
        <DetailField
          label="Última atualização"
          value={formatarData(areaDireito.atualizadoEm)}
        />
        <DetailField label="Atualizado Por" value={areaDireito.atualizadoPor} />
      </DetailSection>
    </DetailShell>
  );
}
