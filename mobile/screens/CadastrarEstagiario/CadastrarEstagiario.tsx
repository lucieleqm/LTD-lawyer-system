import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Alert,
  Dimensions,
  KeyboardTypeOptions,
} from 'react-native';

import axios from 'axios';

const API_URL = process.env.EXPO_PUBLIC_API_URL;

type FormTypes = {
  nome: string;
  email: string;
  matricula: string;
  periodo: string;
  senha: string;
};

const { width } = Dimensions.get('window');

export default function CadastrarEstagiario() {

  const [nome, setNome] = useState<string>('');
  const [email, setEmail] = useState<string>('');
  const [matricula, setMatricula] = useState<string>('');
  const [periodo, setPeriodo] = useState<string>('Estágio I');
  const [senha, setSenha] = useState<string>('');

  const cadastrarEstagiario = async () => {
    try {
      await axios.post(`${API_URL}/estagiarios/`, {
        nome,
        email,
        matricula,
        periodo,
        senha
      });
      limparCampos();
    } catch(error) {
      console.log(error);
    }
  }

  function limparCampos() {
    setNome('');
    setEmail('');
    setMatricula('');
    setSenha('');
  }

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>Cadastro - Núcleo de Práticas Jurídicas</Text>


      <View style={styles.inputGroup}>
        <Text style={styles.label}>Nome Completo</Text>
        <TextInput
          style={styles.input}
          placeholder='Nome Completo'
          value={nome}
          onChangeText={(value) => setNome(value)}
          placeholderTextColor="#888"
        />
      </View>

      <View style={styles.inputGroup}>
        <Text style={styles.label}>E-mail</Text>
        <TextInput
          style={styles.input}
          placeholder='E-mail'
          value={email}
          keyboardType='email-address'
          onChangeText={(value) => setEmail(value)}
          placeholderTextColor="#888"
        />
      </View>

      <View style={styles.inputGroup}>
        <Text style={styles.label}>Matrícula</Text>
        <TextInput
          style={styles.input}
          placeholder='Matrícula'
          value={matricula}
          onChangeText={(value) => setMatricula(value)}
          placeholderTextColor="#888"
        />
      </View>

      <View style={styles.inputGroup}>
        <Text style={styles.label}>Periodo</Text>
        <TextInput
          style={styles.input}
          placeholder='Periodo'
          value={periodo}
          onChangeText={(value) => setPeriodo(value)}
          placeholderTextColor="#888"
        />
      </View>

      <View style={styles.inputGroup}>
        <Text style={styles.label}>Senha</Text>
        <TextInput
          style={styles.input}
          placeholder='Senha'
          secureTextEntry
          value={senha}
          onChangeText={(value) => setSenha(value)}
          placeholderTextColor="#888"
        />
      </View>


      <TouchableOpacity style={styles.button} onPress={cadastrarEstagiario}>
        <Text style={styles.buttonText}>Enviar Cadastro</Text>
      </TouchableOpacity>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    paddingVertical: 30,
    paddingHorizontal: 20,
    backgroundColor: '#f4f6f8',
    alignItems: 'center',
  },
  title: {
    fontSize: 22,
    fontWeight: '700',
    color: '#1a2e66',
    marginBottom: 25,
    textAlign: 'center',
  },
  inputGroup: {
    width: width * 0.92,
    marginBottom: 14,
  },
  label: {
    fontSize: 15,
    marginBottom: 6,
    color: '#333',
    fontWeight: '500',
  },
  input: {
    height: 46,
    backgroundColor: '#fff',
    borderColor: '#ccd1d9',
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 12,
    fontSize: 15,
    color: '#000',
  },
  button: {
    marginTop: 30,
    backgroundColor: '#1a2e66',
    paddingVertical: 14,
    paddingHorizontal: 30,
    borderRadius: 8,
    width: width * 0.92,
    alignItems: 'center',
    elevation: 2,
  },
  buttonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: '600',
  },
});
