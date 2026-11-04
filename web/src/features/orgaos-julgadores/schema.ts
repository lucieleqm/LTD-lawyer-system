import z from 'zod';

export const orgaoJulgadorSchema = z.object({
  nome: z
    .string()
    .trim()
    .min(1, 'O nome é obrigatório')
    .max(120, 'O nome deve ter no máximo 120 caracteres'),

  tribunalId: z.coerce
    .number({ error: 'Tribunal é obrigatório' })
    .int('Tribunal inválido')
    .positive('Tribunal é obrigatório'),

  instancia: z
    .number({ error: 'A instância é obrigatória' })
    .int('A instância deve ser um número inteiro')
    .min(1, 'A instância é obrigatória')
    .max(3, 'Instância inválida'),
});

export type OrgaoJulgadorRequest = z.infer<typeof orgaoJulgadorSchema>;
