import { useNavigate } from "react-router-dom";
import { CriarProcessoRequest } from "../schema";
import { processosService } from "../services/processosService";
import { notifications } from "@mantine/notifications";
import { paths } from "@/routes/paths";
import { Container } from "@mantine/core";
import { PageHeader } from "@/components/ui/PageHeader";
import { ProcessoForm } from "../components/ProcessoForm";
import { areaDireitoLabel, papelNoProcessoLabel, tribunalLabel } from "../constants";


export default function CriarProcessoPage() {
  const navigate = useNavigate();

  const opcoesTribunais = Object.entries(tribunalLabel).map(
    ([value, label]) => ({ value, label }),
  );

  const opcoesAreasDireito = Object.entries(areaDireitoLabel).map(
    ([value, label]) => ({ value, label }),
  );
  const opcoesPapeis = Object.entries(papelNoProcessoLabel).map(
    ([value, label]) => ({ value, label }),
  );

  async function handleSalvar(dados: CriarProcessoRequest) {
    await processosService.criar(dados);
    notifications.show({
      message: 'Processo registrado com sucesso',
      color: 'teal',
    });
    navigate(paths.processos.lista);
  }

  return (
    <Container pos="relative">
      <PageHeader title="Novo Processo" />

      <ProcessoForm
        modo="criar"
        onSubmit={handleSalvar}
        opcoesTribunais={opcoesTribunais}
        opcoesAreasDireito={opcoesAreasDireito}
        opcoesPapeis={opcoesPapeis}
      />
    </Container>
  );
}
