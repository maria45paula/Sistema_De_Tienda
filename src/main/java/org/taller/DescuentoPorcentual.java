package org.taller;

import java.util.Scanner;

public class DescuentoPorcentual extends Descuentos {

    public int operacionDescuento(Pedido miPedido) {
        Scanner entrada = new Scanner(System.in);

        System.out.println("Ingrese el porcentaje de descuento que se va a realizar (0-100):");
        int porcentaje = entrada.nextInt();
        int totalOriginal = miPedido.calcularTotal();
        int nuevoPrecio = totalOriginal - (totalOriginal * porcentaje / 100);
        return nuevoPrecio;
    }

    @Override
    public int aplicarDescuento(Pedido miPedido) {
        int nuevoPrecio = operacionDescuento(miPedido);
        return nuevoPrecio;
    }
}


