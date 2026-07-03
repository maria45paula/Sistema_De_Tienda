package org.taller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.taller.MetodosPago.PAYPAL;
import static org.taller.MetodosPago.TARJETADECREDITO;
import static org.taller.Producto.getListaProductos;
import static org.taller.Producto.getNombreProductos;
import static org.taller.Respuesta.SI;


public class Pedido {
    private List<Producto> pedidos = new ArrayList<>();
    private static List<Pedido> pedidosPendientes = new ArrayList<>();
    private MetodosPago metodoDePago;
    private String estadoDePedido = "Pendiente";
    private int totalPedido;
    private Respuesta respuestaUsuario=SI;
    private int descuento;

    public Pedido() {

    }

    public Pedido(List<Producto> pedidos, MetodosPago metodoDePago) {
        this.pedidos = pedidos;
        this.metodoDePago = metodoDePago;
    }

    public List<Producto> getPedidos() {
        return pedidos;
    }

    public void setEstadoDePedido(String estado) {
        this.estadoDePedido = estado;
    }

    public int getTotalPedido() {
        return totalPedido;
    }

    public MetodosPago getMetodoDePago(){
        return metodoDePago;
    }

    public void getPedidosPendientes() {
            int i = 1;
            System.out.println("--Lista de pedidos Pendientes--");

            for (Pedido pedido : pedidosPendientes) {
                System.out.println("Pedido " + i);

                for (Producto producto : pedido.getPedidos()) {
                    System.out.println("Nombre: " + producto.getNombre());
                    System.out.println("Precio: $" + producto.getPrecio());
                }
                System.out.println("Total:$"+pedido.calcularTotal());
                System.out.println("Metodo de pago:"+getMetodoDePago());


                i++;
            }
    }

    public  void agregarProductoAFactura() {
        Scanner entrada = new Scanner(System.in);
        int posicion;
        do {
            posicion = 0;
            if (respuestaUsuario == SI) {
               Producto.mostrarListaProductos();
                System.out.println("Ingrese el nombre del producto a añadir en el pedido:");
                String producto = entrada.nextLine();

                for (String nombre : getNombreProductos()) {

                    if (producto.equalsIgnoreCase(nombre)) {
                        pedidos.add(getListaProductos().get(posicion));
                    }
                    posicion++;
                }
            } do {
                System.out.println("¿Desea ingresar otro producto a su pedido? SI/NO");
                String textoIngresado = entrada.nextLine().trim().toUpperCase();

                try {
                    respuestaUsuario = Respuesta.valueOf(textoIngresado);
                } catch (IllegalArgumentException e) {
                    System.out.println("Respuesta inválida. Por favor intente de nuevo.");
                }

            } while (respuestaUsuario != Respuesta.SI && respuestaUsuario != Respuesta.NO);

        } while (respuestaUsuario == Respuesta.SI);
        finalizarCompra();
    }

    public void aplicarDescuento(){
        Scanner entrada=new Scanner(System.in);
        System.out.println("¿Desea aplicar un descuento?");

        do{
            try {
                String textoIngresado = entrada.nextLine().trim().toUpperCase();
                respuestaUsuario = Respuesta.valueOf(textoIngresado);
            } catch (IllegalArgumentException e) {
                System.out.println("Respuesta inválida. Por favor intente de nuevo.");
            }
        } while (respuestaUsuario != Respuesta.SI && respuestaUsuario != Respuesta.NO);

            if (respuestaUsuario == SI) {
                System.out.println("1:Descuento porcentual \n 2:Descuento Parcial");
                int numero = entrada.nextInt();
                if (numero == 1) {
                    DescuentoPorcentual desc = new DescuentoPorcentual();
                    int precioFinal = desc.aplicarDescuento(new Pedido(new ArrayList<>(pedidos), metodoDePago));
                    this.totalPedido = precioFinal;
                    System.out.println("El precio total con el descuento es de:"+totalPedido);
                } else if (numero == 2) {
                    DescuentoParcial desc = new DescuentoParcial();
                    int precioFinal = desc.aplicarDescuento(new Pedido(new ArrayList<>(pedidos), metodoDePago));
                    this.totalPedido = precioFinal;
                    System.out.println("El precio total con el descuento es de:"+totalPedido);

                }
            }



    }

    public void descuentoListaObjetoPedido(){
        getPedidosPendientes();
        aplicarDescuento();
    }

    public void finalizarCompra() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("\n--- Factura---");
        for (Producto factura : pedidos) {
            System.out.println("Producto:" + factura.getNombre() + " Precio:$" + factura.getPrecio());
        }

        int total = calcularTotal();
        System.out.println("El valor total del pedido es de:" + total);

        aplicarDescuento();

        System.out.println("Elija el metodo de pago: (TARJETA DE CREDITO / PAYPAL)");

        do{
            try {
                String textoIngresado = entrada.nextLine().trim().toUpperCase();
                metodoDePago = MetodosPago.valueOf(textoIngresado);
            } catch (IllegalArgumentException e) {
                System.out.println("Respuesta inválida. Por favor intente de nuevo.");
            }
        } while(metodoDePago != TARJETADECREDITO && metodoDePago != PAYPAL );

        if (metodoDePago == metodoDePago.PAYPAL) {
            metodoDePago.PAYPAL.pagoPaypal();

        } else if(metodoDePago == metodoDePago.TARJETADECREDITO) {
            metodoDePago.TARJETADECREDITO.pagoTargeta();

        }
        pedidosPendientes.add(new Pedido(new ArrayList<>(pedidos), metodoDePago));

    }

    public int calcularTotal() {
        int total = 0;
        for (Producto precio : pedidos) {
            total += precio.getPrecio();
        }
        this.totalPedido = total;
        return total;
    }
}
