package org.taller;

import java.util.Scanner;

public class Main {

    public void iniciarTienda() {
        Scanner sc = new Scanner(System.in);
        int respuestaUsuario;
        System.out.println("Bienvenido");
        boolean continuar = true;

        Pedido gestorPedidos = new Pedido();
        while (continuar) {

            System.out.println();
            System.out.println("------ SISTEMA DE TIENDA-------");
            System.out.println();
            System.out.println("Ingresa el numero para seleccionar una opción");
            System.out.println("1. Hacer nuevo pedido");
            System.out.println("2. Crear nuevo producto");
            System.out.println("3. Ver lista de productos");
            System.out.println("4. Ver lista de pedidos pendientes");
            System.out.println("5. Aplicar descuento a un pedido pendiente");
            System.out.println("6. Hacer pago de pedidos pendientes");
            System.out.println("7. Salir");
            System.out.println();

            respuestaUsuario = sc.nextInt();
            switch (respuestaUsuario) {
                case 1 -> {
                    Pedido nuevoPedido = new Pedido();
                    nuevoPedido.agregarProductoAFactura();
                }
                case 2 -> Producto.agregarProducto();
                case 3 -> Producto.mostrarListaProductos();
                case 4 -> gestorPedidos.getPedidosPendientes();
                case 5 -> gestorPedidos.getPedidosPendientes();
                case 6 -> gestorPedidos.finalizarCompra();
                case 7 -> continuar = false;
            }
        }
    }
    public static void main(String[] args) {
        Main miTienda = new Main();
        miTienda.iniciarTienda();


    }
}