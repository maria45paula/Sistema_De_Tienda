package org.taller;

import java.util.Scanner;


public enum MetodosPago {
    TARGETA_DE_CREDITO, PAYPAL;

    public void pagoTargeta(){

        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese su número de targeta de credito");
        String numeroTargeta = sc.nextLine();

       while(numeroTargeta.length() < 14 || numeroTargeta.length() > 19) {
           System.out.println("Numero de targeta invalido, intente de nuevo");
           numeroTargeta = sc.nextLine();
       }

        System.out.println("Ingrese el CVV");
        String cvv =sc.nextLine();

        while(cvv.length() != 3){
            System.out.println("CVV invalido, ingrese de nuevo");
            cvv =sc.nextLine();
        }

        System.out.println("Pago exitoso, su pedido se encuentra en estado de espera");
    }

    public void pagoPaypal(){
        Scanner scanner = new Scanner(System.in);
        int contadorArroba = 0;
        int total = Pedido.calcularTotal();
        do{
             contadorArroba = 0;
            System.out.println("Ingrese su correo electronico");
            String correo = scanner.nextLine();

            for(int i =0; i < correo.length() ; i++) {

                if (correo.charAt(i) == '@') {
                    contadorArroba++;
                    String complemento = correo.substring(i, correo.length() - 1);
                    if (complemento.equals("gmail.com") ||
                            complemento.equals("hotmail.com") ||
                            complemento.equals("outlook.com")) {

                        System.out.println("El total de su pedido es: $" + Pedido.calcularTotal());
                        System.out.println("Pago exitoso, el estado de su pedido es pendiente");

                    } else{
                        System.out.println("Correo invalido, intente de nuevo");
                        contadorArroba++;
                    }
                }
            }
       }while(contadorArroba != 1);

    }

}

