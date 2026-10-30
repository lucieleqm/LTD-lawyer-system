import { enderecoSchema } from '@/schemas/endereco.schema';
import { tratarEnderecoOpcional, vazioParaUndefined } from '@/utils/zodHelpers';
import { z } from 'zod';

export const dadosBaseSchema = z.object({
  nome: z.string().trim().min(3, 'O nome é obrigatório'),
  telefone: z.preprocess(
    vazioParaUndefined,
    z.string().min(8, 'Telefone inválido').optional(),
  ),
  dataNascimento: z.string(),
  endereco: z.preprocess(tratarEnderecoOpcional, enderecoSchema.optional()),
});

export const criarAdvogadoSchema = dadosBaseSchema.extend({
  email: z.string().email('Tem de ser um e-mail válido'),
  senha: z.string().min(6, 'Senha deve ter ao menos 6 caracteres'),
});

export const atualizarAdvogadoSchema = dadosBaseSchema;

export type CriarAdvogadoRequest = z.infer<typeof criarAdvogadoSchema>;
export type AtualizarAdvogadoRequest = z.infer<typeof atualizarAdvogadoSchema>;
