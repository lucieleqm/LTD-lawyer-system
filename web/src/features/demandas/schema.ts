import { vazioParaUndefined } from '@/utils/zodHelpers';
import z from 'zod';
import { ETAPA_DEMANDA } from './constants';

const demandaBaseSchema = z.object({
  advogadoId: z.coerce.number().int().positive('Advogado é obrigatório'),
  estagiarioId: z.coerce.number().int().positive('Estagiário é obrigatório'),
  professorId: z.coerce.number().int().positive('Professor é obrigatório'),
  descricao: z.string().min(1, 'A descrição é obrigatória'),
  diasAdicionais: z.coerce
    .number('Campo obrigatório')
    .int('Deve ser um número inteiro')
    .min(0, 'Dias adicionais não podem ser negativos'),
  prazoDocumentos: z.string().min(1, 'O prazo de documentos é obrigatório'),
  etapaAtual: z.enum(ETAPA_DEMANDA, { message: 'A etapa atual é obrigatória' }),
});

export const criarDemandaSchema = demandaBaseSchema;
export const atualizarDemandaSchema = demandaBaseSchema;

export const criarTramitacaoSchema = z.object({
  tipoTramitacao: z.string().min(1, 'O tipo de tramitação é obrigatório'),
  observacoes: z.preprocess(vazioParaUndefined, z.string().optional()),
  linkAnexo: z.preprocess(
    vazioParaUndefined,
    z.string().url('Deve ser um link válido').optional(),
  ),
});

export type CriarDemandaRequest = z.infer<typeof criarDemandaSchema>;
export type AtualizarDemandaRequest = z.infer<typeof atualizarDemandaSchema>;
export type CriarTramitacaoRequest = z.infer<typeof criarTramitacaoSchema>;
