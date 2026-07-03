package org.taller;

import java.util.Scanner;

public class DescuentoParcial extends Descuentos {

    public int operacionDescuentoParcial(Pedido miPedido) {
        Scanner entrada = new Scanner(System.in);

        System.out.println("Ingrese el valor a descontar:");
        int descuento = entrada.nextInt();
        int totalOriginal = miPedido.calcularTotal();
        int nuevoPrecio = totalOriginal - descuento;
        return nuevoPrecio;
    }

    @Override
    public int aplicarDescuento(Pedido miPedido) {
        return 0;
    }
}
