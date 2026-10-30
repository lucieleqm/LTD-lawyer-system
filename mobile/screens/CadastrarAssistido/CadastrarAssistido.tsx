import axios from 'axios';
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

type FormTypes = {
  nome: string;
  cpf: string;
  telefone: string;
  email: string;
  cidade: string;
  bairro: string;
  rua: string;
  numeroCasa: string;
  cep: string;
  complemento: string;
};

const { width } = Dimensions.get('window');

const API_URL = process.env.EXPO_PUBLIC_API_URL;

export default function CadastrarAssistido() {

  const [nome, setNome] = useState<string>('lucas');
  const [matricula, setMatricula] = useState<string>('23423423');
  const [telefone, setTelefone] = useState<string>('88776643466');
  const [email, setEmail] = useState<string>('lucas@gmail.com');
  const [cidade, setCidade] = useState<string>('São Luís');
  const [bairro, setBairro] = useState<string>('Alemanha');
  const [rua, setRua] = useState<string>('Rua das Flores');
  const [numeroDaCasa, setNumeroDaCasa] = useState<string>('12');
  const [cep, setCep] = useState<string>('84023-242');

  const cadastrarAssistido = async () => {
    try {
      await axios.post(`${API_URL}/assistidos/`, {
        nome,
        matricula,
        telefone,
        email,
        cidade,
        bairro,
        rua,
        numeroDaCasa: parseInt(numeroDaCasa),
        cep
      });
      Alert.alert('Cadatardo com Sucesso', 'O assistido foi cadastrado com sucesso.');
      limparCampos();
    } catch (error) {
      console.log(error)
    }
  };

  function limparCampos() {
    setNome('');
    setMatricula('');
    setTelefone('');
    setEmail('');
    setCidade('');
    setBairro('');
    setRua('');
    setNumeroDaCasa('');
    setCep('');
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
        <Text style={styles.label}>Telefone</Text>
        <TextInput
          style={styles.input}
          placeholder='Telefone'
          value={telefone}
          keyboardType='phone-pad'
          onChangeText={(value) => setTelefone(value)}
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
        <Text style={styles.label}>Cidade</Text>
        <TextInput
          style={styles.input}
          placeholder='Cidade'
          value={cidade}
          onChangeText={(value) => setCidade(value)}
          placeholderTextColor="#888"
        />
      </View>

      <View style={styles.inputGroup}>
        <Text style={styles.label}>Bairro</Text>
        <TextInput
          style={styles.input}
          placeholder='Bairro'
          value={bairro}
          onChangeText={(value) => setBairro(value)}
          placeholderTextColor="#888"
        />
      </View>

      <View style={styles.inputGroup}>
        <Text style={styles.label}>Rua</Text>
        <TextInput
          style={styles.input}
          placeholder='Bairro'
          value={rua}
          onChangeText={(value) => setRua(value)}
          placeholderTextColor="#888"
        />
      </View>

      <View style={styles.inputGroup}>
        <Text style={styles.label}>Número da Casa</Text>
        <TextInput
          style={styles.input}
          placeholder='Número da Casa'
          value={numeroDaCasa}
          keyboardType='numeric'
          onChangeText={(value) => setNumeroDaCasa(value)}
          placeholderTextColor="#888"
        />
      </View>

      <View style={styles.inputGroup}>
        <Text style={styles.label}>Cep</Text>
        <TextInput
          style={styles.input}
          placeholder='Cep'
          value={cep}
          onChangeText={(value) => setCep(value)}
          placeholderTextColor="#888"
        />
      </View>

      <TouchableOpacity style={styles.button} onPress={cadastrarAssistido}>
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
    marginBottom: 30,
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
