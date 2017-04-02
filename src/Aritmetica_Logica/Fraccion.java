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

    //Operaciones basicas
    public static Fraccion sumar(Fraccion operando1, Fraccion operando2){
        long numeradorResultado;
        long denominadorResultado;

        if (operando1.denominador == operando2.denominador){
            numeradorResultado = operando1.numerador + operando2.numerador;
            denominadorResultado = operando1.denominador;
        }
        else{
            numeradorResultado = (operando1.numerador * operando2.denominador) + (operando2.numerador * operando1.denominador);
            denominadorResultado = operando1.denominador * operando2.denominador;
        }

        return simplificar(new Fraccion(numeradorResultado,denominadorResultado));
    }

    public static Fraccion restar(Fraccion minuendo, Fraccion sustraendo){
        long numeradorResultado;
        long denominadorResultado;

        if (minuendo.denominador == sustraendo.denominador){
            numeradorResultado = minuendo.numerador - sustraendo.numerador;
            denominadorResultado = minuendo.denominador;
        }
        else{
            numeradorResultado = (minuendo.numerador * sustraendo.denominador) - (sustraendo.numerador * minuendo.denominador);
            denominadorResultado = minuendo.denominador * sustraendo.denominador;
        }

        return simplificar(new Fraccion(numeradorResultado,denominadorResultado));
    }

    public static Fraccion invertir(Fraccion fraccion){
        return new Fraccion(fraccion.denominador, fraccion.numerador);
    }

    public static Fraccion multiplicar(Fraccion operando1, Fraccion operando2){
        long numeradorResultado = operando1.numerador * operando2.numerador;
        long denominadorResultado = operando1.denominador * operando2.denominador;

        return simplificar(new Fraccion(numeradorResultado,denominadorResultado));
    }

    public static Fraccion simplificar(Fraccion fraccion){
        if (fraccion.numerador == 0) {
            return new Fraccion(0,1);
        }
        else{
            long divisorComun = encontrarDivisorComun(fraccion.numerador, fraccion.denominador);
            long numeradorResultado = fraccion.numerador / divisorComun;
            long denominadorResultado = fraccion.denominador / divisorComun;
            if (numeradorResultado < 0 && denominadorResultado < 0 ||
                    numeradorResultado > 0 && denominadorResultado < 0){
                numeradorResultado *= -1;
                denominadorResultado *= -1;
            }
            return new Fraccion(numeradorResultado, denominadorResultado);
        }
    }

    //Auxiliares
    @Override
    public String toString() {
        return "" + numerador + "/" + denominador;
    }

    public static long encontrarDivisorComun(long numero1, long numero2){
        return encontrarDivisorComunAux(Math.abs(numero1), Math.abs(numero2));
    }

    //Encuentra el maximo divisor comun entre dos numeros, se usa para simplificar fracciones.
    public static long encontrarDivisorComunAux(long numero1, long numero2){
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

