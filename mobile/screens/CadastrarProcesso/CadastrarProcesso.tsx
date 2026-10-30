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

// type FormTypes = {
//   numero: string;
//   assunto: string;
//   vara: string;
//   responsavel: string;
//   prazo: string;
// };

const { width } = Dimensions.get('window');

import axios from 'axios';

const API_URL = process.env.EXPO_PUBLIC_API_URL;

export default function CadastrarProcesso() {

  const [assunto, setAssunto] = useState<string>('Seguro de Carro');
  const [vara, setVara] = useState<string>('23423vara423');
  const [responsavel, setResponsavel] = useState<string>('Júlio');
  const [prazo, setPrazo] = useState<string>('');

  const cadastrarProcesso = async () => {
    try {
      await axios.post(`${API_URL}/processos/`, {
        assunto,
        vara,
        responsavel,
        prazo
      });
      Alert.alert('Cadatardo com Sucesso', 'O cadastro foi cadastrado com sucesso.');
      limparCampos();
    } catch (error) {
      console.log(error)
    }
  };

  function limparCampos() {
    setAssunto('');
    setVara('');
    setResponsavel('');
    setPrazo('');
  }

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>Cadastro de Processo</Text>


      <View style={styles.inputGroup}>
        <Text style={styles.label}>Assunto</Text>
        <TextInput
          style={styles.input}
          placeholder='Assunto'
          value={assunto}
          onChangeText={(value) => setAssunto(value)}
          keyboardType='default'
          placeholderTextColor="#999"
        />
      </View>

      <View style={styles.inputGroup}>
        <Text style={styles.label}>Vara</Text>
        <TextInput
          style={styles.input}
          placeholder='Vara'
          value={vara}
          onChangeText={(value) => setVara(value)}
          keyboardType='default'
          placeholderTextColor="#999"
        />
      </View>


      <View style={styles.inputGroup}>
        <Text style={styles.label}>Responsavel</Text>
        <TextInput
          style={styles.input}
          placeholder='Responsavel'
          value={responsavel}
          onChangeText={(value) => setResponsavel(value)}
          keyboardType='default'
          placeholderTextColor="#999"
        />
      </View>

      <View style={styles.inputGroup}>
        <Text style={styles.label}>Prazo</Text>
        <TextInput
          style={styles.input}
          placeholder='Prazo (DD/MM/AAAA)'
          value={prazo}
          onChangeText={(value) => setPrazo(value)}
          keyboardType='default'
          placeholderTextColor="#999"
        />
      </View>

      <TouchableOpacity style={styles.button} onPress={cadastrarProcesso}>
        <Text style={styles.buttonText}>Cadastrar Processo</Text>
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
