import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { StackParamList  } from './types';

import Home from './screens/Home/Home';
import Admin from './screens/HomeAdmin/HomeAdmin';
import CadastrarAssistido from './screens/CadastrarAssistido/CadastrarAssistido';
import CadastrarEstagiario from './screens/CadastrarEstagiario/CadastrarEstagiario';
import Processos from './screens/Processos/Processos';
import CadastrarProcesso from './screens/CadastrarProcesso/CadastrarProcesso';

const Stack = createNativeStackNavigator<StackParamList>();

export default function Routes() {
    return(
        <Stack.Navigator initialRouteName="Home">
            <Stack.Screen name="Home" component={Home} options={{ title: 'Início' }}/>
            <Stack.Screen name="Admin" component={Admin} options={{ title: 'Painel do Administrador' }}/>
            {/* <Stack.Screen name="CadastrarAssistido" component={CadastrarAssistido} options={{ title: 'Cadastrar Assistido' }} />
            <Stack.Screen name="CadastrarEstagiario" component={CadastrarEstagiario} options={{ title: 'Cadastrar Estagiário' }} /> */}
            <Stack.Screen name="Processos" component={Processos} options={{ title: 'Processos' }} />
            {/* <Stack.Screen name="CadastrarProcesso" component={CadastrarProcesso} options={{ title: 'Cadastrar Processo' }} /> */}
        </Stack.Navigator>
    )
}