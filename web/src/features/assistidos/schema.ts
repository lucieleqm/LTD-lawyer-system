import { enderecoSchema } from '@/schemas/endereco.schema';
import { tratarEnderecoOpcional, vazioParaUndefined } from '@/utils/zodHelpers';
import z from 'zod';

const dadosBaseSchema = z.object({
  nome: z.string().min(1, 'Nome é obrigatório'),
  matricula: z.string().min(1, 'Matrícula é obrigatória'),
  telefone: z.preprocess(
    vazioParaUndefined,
    z.string().min(8, 'Telefone inválido').optional(),
  ),
  email: z.preprocess(
    vazioParaUndefined,
    z.string().email('E-mail inválido').optional(),
  ),
  profissao: z.string().optional(),
  nacionalidade: z.string().optional(),
  naturalidade: z.string().optional(),
  estadoCivil: z.string().optional(),
  endereco: z.preprocess(tratarEnderecoOpcional, enderecoSchema.optional()),
});

export const criarAssistidoSchema = dadosBaseSchema;
export const atualizarAssistidoSchema = dadosBaseSchema;

export type CriarAssistidoRequest = z.infer<typeof criarAssistidoSchema>;
export type AtualizarAssistidoRequest = z.infer<
  typeof atualizarAssistidoSchema
>;
