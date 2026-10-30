import { vazioParaUndefined } from '@/utils/zodHelpers';
import { z } from 'zod';
import { PERIODO_ESTAGIO } from './constants';

const dadosBaseSchema = z.object({
  nome: z.string().min(1, 'Nome é obrigatório'),
  telefone: z.preprocess(
    vazioParaUndefined,
    z.string().min(8, 'Telefone inválido').optional(),
  ),
  email: z.string().min(1, 'E-mail é obrigatório').email('E-mail inválido'),
  matricula: z.string().min(1, 'Matrícula é obrigatória'),
  periodoEstagio: z.enum(PERIODO_ESTAGIO, {
    message: 'Período do estágio é obrigatório',
  }),
});

// criar = dados base + acesso ao sistema
export const criarEstagiarioSchema = dadosBaseSchema.extend({
  senha: z.string().min(6, 'Senha deve ter ao menos 6 caracteres'),
});

export const atualizarEstagiarioSchema = dadosBaseSchema;

export type CriarEstagiarioRequest = z.infer<typeof criarEstagiarioSchema>;
export type AtualizarEstagiarioRequest = z.infer<
  typeof atualizarEstagiarioSchema
>;
