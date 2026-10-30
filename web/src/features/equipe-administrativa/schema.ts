import { vazioParaUndefined } from '@/utils/zodHelpers';
import z from 'zod';

export const dadosBaseSchema = z.object({
  nome: z.string().trim().min(3, 'O nome é obrigatório'),
  telefone: z.preprocess(
    vazioParaUndefined,
    z.string().min(8, 'Telefone inválido').optional(),
  ),
});

export const criarAdministrativoSchema = dadosBaseSchema.extend({
  email: z.string().email('Tem de ser um e-mail válido'),
  senha: z.string().min(6, 'Senha deve ter ao menos 6 caracteres'),
  role: z.string('O cargo é obrigatório'),
});

export type CriarAdministrativoRequest = z.infer<
  typeof criarAdministrativoSchema
>;
