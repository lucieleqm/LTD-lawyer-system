import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  Image,
  Dimensions,
} from 'react-native';

import type { NativeStackNavigationProp } from '@react-navigation/native-stack';
import type { StackParamList } from '../../types';

//import imgPath from './../../assets/Balanca-da-justica.png';

const { width } = Dimensions.get('window');

type HomeScreenNavigationProp = NativeStackNavigationProp<StackParamList, 'Home'>;

type Props = {
  navigation: HomeScreenNavigationProp;
};

export default function Home({ navigation }: Props) {
  return (
    <View style={styles.container}>
      <Image
        source={require('./../../assets/Balanca-da-justica.png')}
        style={styles.logo}
      />

      <Text style={styles.title}>Núcleo de Práticas Jurídicas</Text>
      <Text style={styles.subtitle}>
        Atendimento jurídico gratuito e orientado à comunidade.
      </Text>

      <TouchableOpacity
        style={[styles.button, styles.secondaryButton]}
        onPress={() => navigation.navigate('Admin')}
      >
        <Text style={[styles.buttonText, { color: '#2a3a7f' }]}>Já tenho conta</Text>
      </TouchableOpacity>

      <TouchableOpacity
        style={styles.button}
        onPress={() => navigation.navigate('Admin')}
      >
        <Text style={styles.buttonText}>Fazer Cadastro</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#eef2f7',
    alignItems: 'center',
    justifyContent: 'center',
    paddingHorizontal: 20,
  },
  logo: {
    width: 90,
    height: 90,
    marginBottom: 25,
  },
  title: {
    fontSize: 24,
    fontWeight: '800',
    color: '#2a3a7f',
    marginBottom: 10,
    textAlign: 'center',
  },
  subtitle: {
    fontSize: 16,
    color: '#555',
    textAlign: 'center',
    marginBottom: 40,
    paddingHorizontal: 10,
  },
  button: {
    backgroundColor: '#2a3a7f',
    paddingVertical: 14,
    paddingHorizontal: 20,
    borderRadius: 8,
    width: width * 0.85,
    marginBottom: 15,
    alignItems: 'center',
    elevation: 2,
  },
  secondaryButton: {
    backgroundColor: '#fff',
    borderWidth: 1,
    borderColor: '#2a3a7f',
  },
  buttonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: '700',
  },
});
