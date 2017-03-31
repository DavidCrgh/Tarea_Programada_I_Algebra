package Aritmetica_Logica;

/**
 * Creado por David Valverde Garro - 2016034774
 * el 10-Mar-17.
 */
public class Fraccion {
    public long numerador;
    public long denominador;

    public Fraccion(long _numerador, long _denominador){
        numerador = _numerador;
        denominador = _denominador;
    }
    public Fraccion(long _numerador){
        numerador = _numerador;
        denominador = 1;
    }
    public Fraccion(){
        this.numerador = 0;
        this.denominador = 1;
    }
    //Operaciones basicas
    public static Fraccion sumar(Fraccion operando1, Fraccion operando2){
        long numeradorResultado;
        long denominadorResultado = econtrarDenominadorComun(operando1.denominador, operando2.denominador);

        numeradorResultado = (operando1.numerador * (denominadorResultado/operando2.denominador)) +
                (operando2.numerador * (denominadorResultado / operando2.denominador));

        return simplificar(new Fraccion(numeradorResultado,denominadorResultado));
    }

    public static Fraccion restar(Fraccion operando1, Fraccion operando2){
        long numeradorResultado;
        long denominadorResultado = econtrarDenominadorComun(operando1.denominador, operando2.denominador);

        numeradorResultado = (operando1.numerador * (denominadorResultado/operando2.denominador)) -
                (operando2.numerador * (denominadorResultado / operando2.denominador));

        return simplificar(new Fraccion(numeradorResultado,denominadorResultado));
    }

    public static Fraccion multiplicar(Fraccion operando1, Fraccion operando2){
        long numeradorResultado = operando1.numerador * operando2.numerador;
        long denominadorResultado = operando1.denominador * operando2.denominador;

        return simplificar(new Fraccion(numeradorResultado,denominadorResultado));
    }

    public static Fraccion multiplicar(Fraccion operando1, int operando2){
        long numeradorResultado = operando1.numerador * operando2;
        long denominadorResultado = operando1.denominador * 1;

        return simplificar(new Fraccion(numeradorResultado,denominadorResultado));
    }


    public static Fraccion dividir(Fraccion operando1, Fraccion operando2){
        long numeradorResultado = operando1.numerador * operando2.denominador;
        long denominadorResultado = operando1.denominador * operando2.numerador;

        return simplificar(new Fraccion(numeradorResultado, denominadorResultado));
    }
    public static Fraccion dividir(int operando1, Fraccion operando2){
        long numeradorResultado = operando1 * operando2.denominador;
        long denominadorResultado = 1 * operando2.numerador;

        return simplificar(new Fraccion(numeradorResultado, denominadorResultado));
    }
    public static Fraccion simplificar(Fraccion fraccion){
        if(fraccion.numerador == 0){
            return new Fraccion(0,1);
        } else{
            long divisorComun = encontrarDivisorComun(fraccion.numerador, fraccion.denominador);
            long numeradorResultado = fraccion.numerador / divisorComun;
            long denominadorResultado = fraccion.denominador / divisorComun;

            return new Fraccion(numeradorResultado, denominadorResultado);
        }
    }

    //Auxiliares
    private static long econtrarDenominadorComun(long denominador1, long denominador2){
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
    private static long encontrarDivisorComun(long numero1, long numero2){
        if (numero1 == numero2) {
            return numero1;
        } else {
            long divisor = 2;
            long minimo = numero1;
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
}

