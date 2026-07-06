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
    private static List<Pedido> pedidosPagados = new ArrayList<>();
    private MetodosPago metodoDePago;
    private String estadoDePedido = "Pendiente";
    private int totalPedido;
    private Respuesta respuestaUsuario=SI;
    private int descuento;

    public Pedido() {

    }

    public Pedido(List<Producto> pedidos) {
        this.pedidos = pedidos;

    }

    public List<Producto> getPedidos() {
        return pedidos;
    }

    public String getEstadoDePedido() {
        return estadoDePedido;
    }

    public void setEstadoDePedido(String estado) {
        this.estadoDePedido = estado;
    }

    public int getTotalPedido() {
            return totalPedido;
    }
    public int getDescuento() {
        return descuento;
    }

    public MetodosPago getMetodoDePago(){
        return metodoDePago;
    }

    public void setMetodoDePago(MetodosPago metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public void setTotalPedido(int totalPedido) {
        this.totalPedido = totalPedido;
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
                System.out.println("Estado de pedido:"+pedido.getEstadoDePedido());
                System.out.println("Descuento:$"+pedido.getDescuento());
                System.out.println("Total:$"+pedido.getTotalPedido());
                System.out.println("Metodo de pago:"+pedido.getMetodoDePago());


                i++;
            }
    }

    public void getPedidosPagados() {
        int i = 1;
        System.out.println("--Lista de pedidos Pagados--");

        for (Pedido pedido : pedidosPagados) {
            System.out.println("Pedido " + i);

            for (Producto producto : pedido.getPedidos()) {
                System.out.println("Nombre: " + producto.getNombre());
                System.out.println("Precio: $" + producto.getPrecio());
            }

            System.out.println("Total: $" + pedido.getTotalPedido());
            System.out.println("Descuento: $" + pedido.getDescuento());
            System.out.println("Método de pago: " + pedido.getMetodoDePago());

            i++;
        }
    }

    public  void agregarProductoAFactura() {
        Scanner entrada = new Scanner(System.in);
        int posicion;
        Producto productoVacio = new Producto();
        List<String> productooo = getNombreProductos();


        if(Producto.getListaProductos().isEmpty()){
            System.out.println("No hay ningun producto, no puede realizar el pedido");
            return;
        }

        do {
            posicion = 0;
            if (respuestaUsuario == SI) {
               Producto.mostrarListaProductos();
                      System.out.println("Ingrese el nombre del producto a añadir en el pedido:");
                      String producto;
                      boolean acceptable = true;

                    do {
                        producto = entrada.nextLine();
                        for (int i = 0; productooo.size() > i; i++) {
                            if (!(productooo.get(i)).equalsIgnoreCase(producto)) {
                                acceptable = false;
                            } else{ acceptable = true;

                            }

                        }
                         if(acceptable == true){ break;}
                        System.out.println("El producto ingresado no existe, intente de nuevo");

                    } while(acceptable== false);

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
        Pedido nuevoPedido = new Pedido(new ArrayList<>(pedidos));
        nuevoPedido.calcularTotal();
        nuevoPedido.finalizarCompra();


        pedidosPendientes.add(nuevoPedido);

        pedidos.clear();
        respuestaUsuario = SI;
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
                    int precioFinal = desc.aplicarDescuento(this);
                    this.descuento = totalPedido - precioFinal;
                    this.totalPedido = precioFinal;
                    System.out.println("El precio total con el descuento es de:"+totalPedido);
                } else if (numero == 2) {
                    DescuentoParcial desc = new DescuentoParcial();
                    int precioFinal = desc.aplicarDescuento(this);
                    this.descuento = totalPedido - precioFinal;
                    this.totalPedido = precioFinal;
                    System.out.println("El precio total con el descuento es de:"+totalPedido);

                }
            }
        this.estadoDePedido="Pagado";
        pedidosPagados.add(this);
        pedidosPendientes.remove(this);
    }

    public void descuentoListaObjetoPedido(){
            Scanner entrada = new Scanner(System.in);
            getPedidosPendientes();

            System.out.print("Seleccione el número del pedido: ");
            int opcion = entrada.nextInt();

            if (opcion < 1 || opcion > pedidosPendientes.size()) {
                System.out.println("Pedido no válido.");
                return;
            }

            Pedido pedidoSeleccionado = pedidosPendientes.get(opcion - 1);
            pedidoSeleccionado.aplicarDescuento();


        System.out.println("El pedido fue pagado y eliminado de la lista de pendientes.");

    }


    public void finalizarCompra() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("\n--- Factura---");
        for (Producto factura : pedidos) {
            System.out.println("Producto:" + factura.getNombre() + " Precio:$" + factura.getPrecio());
        }

        if (totalPedido == 0) {
            calcularTotal();
        }

        System.out.println("El valor total del pedido es de: $" + totalPedido);

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
        System.out.println("Información de pago guardada con exito.");

        respuestaUsuario = SI;

    }

    public int calcularTotal() {
        int total = 0;
        for (Producto precio : pedidos) {
            total += precio.getPrecio();
        }
        this.totalPedido = total;
        return totalPedido;
    }
}
