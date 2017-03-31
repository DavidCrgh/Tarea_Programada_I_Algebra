package Aritmetica_Logica;

/**
 * Creado por David Valverde Garro - 2016034774
 * el 10-Mar-17.
 */
public class Fraccion {
    public int numerador;
    public int denominador;

    public Fraccion(int _numerador, int _denominador){
        numerador = _numerador;
        denominador = _denominador;
    }
    public Fraccion(int _numerador){
        numerador = _numerador;
        denominador = 1;
    }
    public Fraccion(){
        this.numerador = 0;
        this.denominador = 1;
    }
    //Operaciones basicas
    public static Fraccion sumar(Fraccion operando1, Fraccion operando2){
        int numeradorResultado;
        int denominadorResultado = econtrarDenominadorComun(operando1.denominador, operando2.denominador);

        numeradorResultado = (operando1.numerador * (denominadorResultado/operando2.denominador)) +
                (operando2.numerador * (denominadorResultado / operando2.denominador));

        return simplificar(new Fraccion(numeradorResultado,denominadorResultado));
    }

    public static Fraccion restar(Fraccion operando1, Fraccion operando2){
        int numeradorResultado;
        int denominadorResultado = econtrarDenominadorComun(operando1.denominador, operando2.denominador);

        numeradorResultado = (operando1.numerador * (denominadorResultado/operando2.denominador)) -
                (operando2.numerador * (denominadorResultado / operando2.denominador));

        return simplificar(new Fraccion(numeradorResultado,denominadorResultado));
    }

    public static Fraccion multiplicar(Fraccion operando1, Fraccion operando2){
        int numeradorResultado = operando1.numerador * operando2.numerador;
        int denominadorResultado = operando1.denominador * operando2.denominador;

        return simplificar(new Fraccion(numeradorResultado,denominadorResultado));
    }

    public static Fraccion multiplicar(Fraccion operando1, int operando2){
        int numeradorResultado = operando1.numerador * operando2;
        int denominadorResultado = operando1.denominador * 1;

        return simplificar(new Fraccion(numeradorResultado,denominadorResultado));
    }


    public static Fraccion dividir(Fraccion operando1, Fraccion operando2){
        int numeradorResultado = operando1.numerador * operando2.denominador;
        int denominadorResultado = operando1.denominador * operando2.numerador;

        return simplificar(new Fraccion(numeradorResultado, denominadorResultado));
    }
    public static Fraccion dividir(int operando1, Fraccion operando2){
        int numeradorResultado = operando1 * operando2.denominador;
        int denominadorResultado = 1 * operando2.numerador;

        return simplificar(new Fraccion(numeradorResultado, denominadorResultado));
    }
    public static Fraccion simplificar(Fraccion fraccion){
        int divisorComun = encontrarDivisorComun(fraccion.numerador, fraccion.denominador);
        int numeradorResultado = fraccion.numerador / divisorComun;
        int denominadorResultado = fraccion.denominador / divisorComun;

        return new Fraccion(numeradorResultado, denominadorResultado);
    }

    //Auxiliares
    private static int econtrarDenominadorComun(int denominador1, int denominador2){
        if(denominador1 == denominador2){
            return denominador1;
        } else {
            return denominador1 * denominador2;
        }
    }

    @Override
    public String toString() {
        return "Fraccion{" +
                "numerador=" + numerador +
                ", denominador=" + denominador +
                '}';
    }

    //Encuentra el maximo divisor comun entre dos numeros, se usa para simplificar fracciones.
    private static int encontrarDivisorComun(int numero1, int numero2){
        int divisor = 2;
        int minimo = numero1;
        if (numero1 > numero2)
            minimo = numero2;
        while (!(divisor > minimo)){
            if (numero1 % divisor == 0 && numero2 % divisor == 0)
                return divisor * encontrarDivisorComun(numero1/divisor, numero2/divisor);
            divisor++;
        }
        return 1;
    }
}

