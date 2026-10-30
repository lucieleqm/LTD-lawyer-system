import React from 'react';
import { TouchableOpacity, Text, StyleSheet, Dimensions } from 'react-native';

const { width } = Dimensions.get('window');

export default function MenuButton({ label, onPress, color = '#2a3a7f' }) {
    return(
        <TouchableOpacity onPress={onPress} style={[styles.button, { backgroundColor: color }]}>
            <Text style={styles.buttonText}>{label}</Text>
        </TouchableOpacity>
    )
}

const styles = StyleSheet.create({
    button: {
        width: width * 0.85,
        paddingVertical: 16,
        borderRadius: 10,
        marginBottom: 18,
        alignItems: 'center',
        elevation: 2,
        color: '#2a3a7f',
    },
    buttonText: {
        fontSize: 16,
        color: '#fff',
        fontWeight: '700',
    },
});