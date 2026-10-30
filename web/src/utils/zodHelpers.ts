export function tratarEnderecoOpcional(valores: unknown) {
  if (!valores || typeof valores !== 'object') return undefined;

  const preencheuAlgumCampo = Object.values(valores).some(
    (campo) => campo !== '' && campo !== null && campo !== undefined,
  );
  return preencheuAlgumCampo ? valores : undefined;
}

// utilizar quando precisa validar um campo mas ele é opcional.
export function vazioParaUndefined(valor: unknown) {
  if (valor === null || valor === undefined) {
    return undefined;
  }
  if (typeof valor === 'string' && valor.trim() === '') {
    return undefined;
  }
  return valor;
}

export function removerMascara(valor: unknown) {
  if (typeof valor !== 'string') return valor;
  // remove tudo o que nao for número
  return valor.replace(/\D/g, '');
}
