type FormatoData = 'curto' | 'com-segundos' | 'extenso' | 'somente-data';

const OPCOES_FORMATO: Record<FormatoData, Intl.DateTimeFormatOptions> = {
  curto: { dateStyle: 'short', timeStyle: 'short' },
  'com-segundos': { dateStyle: 'short', timeStyle: 'medium' },
  extenso: { dateStyle: 'long', timeStyle: 'short' },
  'somente-data': { dateStyle: 'short' },
};

export function formatarData(
  data: string | Date | null | undefined,
  formato: FormatoData = 'curto',
): string {
  if (!data) return '-';
  let dataSaneada = data;

  // Previne o bug de fuso horário do JavaScript
  // Adiciona o meio-dia (T12:00:00) para garantir que fuso nenhum mude o dia.
  if (typeof data === 'string' && data.length === 10) {
    dataSaneada = `${data}T12:00:00`;
  }
  
  try {
    const dataObj = new Date(dataSaneada);

    if (isNaN(dataObj.getTime())) return String(data);

    return new Intl.DateTimeFormat('pt-BR', {
      ...OPCOES_FORMATO[formato],
    }).format(dataObj);
  } catch (error) {
    return String(data);
  }
}
