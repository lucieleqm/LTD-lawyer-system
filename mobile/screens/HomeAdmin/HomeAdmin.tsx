import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Dimensions,
} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import MenuButton from '../../components/MenuButton/MenuButton';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { StackParamList } from './../../types';

type HomeAdminNavigationProp = NativeStackNavigationProp<StackParamList, 'Admin'>;

export default function HomeAdmin() {
  const navigation = useNavigation<HomeAdminNavigationProp>();

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>
      <Text style={styles.title}>Painel Administrativo</Text>

      <View style={styles.menu}>
        <MenuButton
          label="Ver Processos"
          onPress={() => navigation.navigate('Processos')}
        />
        {/* <MenuButton
          label="Cadastrar Assistido"
          onPress={() => navigation.navigate('CadastrarAssistido')}
        />
        <MenuButton
          label="Cadastrar Estagiário"
          onPress={() => navigation.navigate('CadastrarEstagiario')}
        />
        <MenuButton
          label="Cadastrar Processo"
          onPress={() => navigation.navigate('CadastrarProcesso')}
        />*/}
        <MenuButton
          label="Sair"
          onPress={() => navigation.navigate('Home')}
          color="#e74c3c"
        />
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#eef2f7',
  },
  content: {
    paddingTop: 50,
    paddingBottom: 30,
    paddingHorizontal: 20,
    alignItems: 'center',
  },
  title: {
    fontSize: 26,
    fontWeight: '800',
    color: '#2a3a7f',
    marginBottom: 30,
  },
  menu: {
    width: '100%',
    alignItems: 'center',
  },
});
