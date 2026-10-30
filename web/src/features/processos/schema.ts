import { vazioParaUndefined } from '@/utils/zodHelpers';
import z from 'zod';

export const parteEnvolvidaSchema = z.object({
  nome: z.string().min(1, 'O nome é obrigatório'),
  papel: z.string().min(1, 'O papel no processo é obrigatório'),
  especificacaoPapel: z.preprocess(vazioParaUndefined, z.string().optional()),
});

const processoBaseSchema = z.object({
  advogadoId: z.coerce.number().int().positive('Advogado é obrigatório'),
  estagiarioId: z.coerce.number().int().positive('Estagiário é obrigatório'),
  assistidoId: z.coerce.number().int().positive('Assistido é obrigatório'),
  numeroProcessoPje: z.preprocess(vazioParaUndefined, z.string().optional()),
  areaDireito: z.preprocess(vazioParaUndefined, z.string().optional()),
  assunto: z.preprocess(vazioParaUndefined, z.string().optional()),
  vara: z.preprocess(vazioParaUndefined, z.string().optional()),
  tribunal: z.preprocess(vazioParaUndefined, z.string().optional()),
  prazoFinal: z.preprocess(vazioParaUndefined, z.string().optional()),
  partesEnvolvidas: z.array(parteEnvolvidaSchema).optional().default([]),
});

export const criarProcessoSchema = processoBaseSchema;
export const atualizarProcessoSchema = processoBaseSchema;

export type ParteEnvolvidaRequest = z.infer<typeof parteEnvolvidaSchema>;
export type CriarProcessoRequest = z.infer<typeof criarProcessoSchema>;
export type AtualizarProcessoRequest = z.infer<typeof atualizarProcessoSchema>;
