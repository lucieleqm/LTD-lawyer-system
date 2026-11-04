import { vazioParaUndefined } from '@/utils/zodHelpers';
import z from 'zod';

export const dadosBaseSchema = z.object({
  nome: z.string().trim().min(3, 'O nome é obrigatório'),
  sigla: z.string().trim().min(2, 'A sigla é obrigatória'),
  esfera: z.preprocess(
    vazioParaUndefined,
    z.string().min(1, 'Esfera inválida').optional(),
  ),
});

export const criarTribunalSchema = dadosBaseSchema
export const atualizarTribunalSchema = dadosBaseSchema;

export type CriarTribunalRequest = z.infer<typeof criarTribunalSchema>;
export type AtualizarTribunalRequest = z.infer<
  typeof atualizarTribunalSchema
>;
