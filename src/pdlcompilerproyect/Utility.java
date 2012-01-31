package pdlcompilerproyect;

import java.io.*;
import java_cup.runtime.*;

/**
 * Clase de apoyo para el lexer, contiene mensajes de error y avisos,
 * metodos para imprimirlos, limites del tamaño de bits y las funciones para
 * transformar los distintos formatos de numeros a binario
 * 
 * @version 2
 * @author JRuiz
 */
class Utility {
    
  public static final int MAX_SIGNED_SIZE_IN_BITS = 15;
  public static final int MAX_UNSIGNED_SIZE_IN_BITS = 16;
  
  public static final int E_NUMBER_OUT_OF_RANGE = 0; 
  public static final int E_ILLEGAL_ENDING_CHAR = 1;
  public static final int E_SYMBOL_NOT_FOUND = 2;
  public static final int W_UPPERCASED_ENDING_CHAR = 0; 
  public static final int W_IMPLICIT_DOUBLE_PART = 1;
  
  private static final String errorMsg[] = {
    "Error Lexico: linea[<linea>] columna[<columna>] El numero [<cad>] esta fuera de rango",
    "Error Lexico: linea[<linea>] columna[<columna>] El numero [<cad>] no tiene una notacion conocida",
    "Error Morfologico: linea[<linea>] columna[<columna>] El simbolo [<cad>] no es valido"
    };

  private static final String warningMsg[] = {
    "Alerta lexico: linea[<linea>] columna[<columna>] El numero [<cad>] se supone que esta en <notacion>",
    "Alerta lexico linea[<linea>] columna[<columna>] El numero [<cad>] necesita como minimo un digito <posicion> de la parte decimal"
    }; 
  
  /**
   * Imprime el error asociado al codigo que se le pasa como parametro
   * @param code codigo del error
   * @param row fila donde se da el error
   * @param column columna donde se da el error
   * @param cad que ha provocado el error
   */
  public static void error(Symbol sd, int code, int row, int column, String cad) {
      String msg = errorMsg[code];
      msg = msg.replace("<linea>", String.valueOf(row));
      msg = msg.replace("<columna>", String.valueOf(column));
      msg = msg.replace("<cad>", cad);
      System.err.println(msg);
  }
  
  /**
   * Imprime el aviso/incidencia asociado al codigo que se le pasa como parametro
   * @param code codigo de la incidencia
   * @param row fila donde se dio la incidencia
   * @param column columna donde se dio la incidencia
   * @param cad que ha provocado la incidencia
   */
  public static void warning(int code, int row, int column, String cad) {
      String msg = warningMsg[code];
      msg = msg.replace("<linea>", String.valueOf(row));
      msg = msg.replace("<columna>", String.valueOf(column));
      msg = msg.replace("<cad>", cad);
      if(cad.endsWith("O")) {
          msg = msg.replace("<notacion>", "Octal");
      } else if(cad.endsWith("H")) {
          msg = msg.replace("<notacion>", "Hexadecimal");
      } else if(cad.endsWith("B")) {
          msg = msg.replace("<notacion>", "Binario");
      }
      if(cad.startsWith(".")) {
          msg = msg.replace("<posicion>", "delante");
      } else if(cad.endsWith(".")) {
          msg = msg.replace("<posicion>", "detras");
      }
      System.err.print(msg + "\n    >> ");
  }
  
  /**
   * Imprime la traza informativa
   * @param msg mensaje a imprimir
   */
  public static void info(String msg) {
      System.out.println(msg);
  }
  
  
  // -- FUNCIONES AUXILIARES DEL ANALIZADOR SINTACTICO --
  
  /**
   * Imprime la informacion del token y el valor (si procede)
   * @param token
   */
  public static void printTokenInfo(String token, int line, int column, Object value) {
        StringBuilder msg = new StringBuilder("Token [");
        msg.append(token);
        msg.append("] detectado (linea: ");
        msg.append(line);
        msg.append(" columna: ");
        msg.append(column);
        msg.append(")");
        if(value instanceof Integer) {
            msg.append(": ");
            msg.append(Integer.toString((Integer) value));
        } else if(value instanceof Float) {
            msg.append(": ");
            msg.append(Float.toString((Float) value));
        } else if(value instanceof String) {
            msg.append(": ");
            msg.append((String) value);
        }
        System.out.println(msg.toString());
    }

    /**
     * Devuelve el tipo del token dada una clase Symbol
     * @param s symbol con el token detectado en el analisis morfologico
     * @return tipo del token
     */
    public static String getSymbol(int sym){
        String simbolo = "UNDEFINED";
        switch(sym){
            case 0: simbolo= "EOF"; break;
            case 1: simbolo= "ERROR"; break;
            case 2: simbolo= "program"; break;
            case 3: simbolo= "if"; break;
            case 4: simbolo= "else"; break;
            case 5: simbolo= "while"; break;
            case 6: simbolo= "for"; break;
            case 7: simbolo= "CTE_BOOLEANA"; break;
            case 8: simbolo= "NUM_OCT"; break;
            case 9: simbolo= "NUM_HEX"; break;
            case 10: simbolo= "NUM_BIN"; break;
            case 11: simbolo= "float"; break;
            case 12: simbolo= "scanf"; break;
            case 13: simbolo= "printf"; break;
            case 14: simbolo= "cprintf"; break;
            case 15: simbolo= ";"; break;
            case 16: simbolo= ","; break;
            case 17: simbolo= ":"; break;
            case 18: simbolo= "+"; break;
            case 19: simbolo= "-"; break;
            case 20: simbolo= "/"; break;
            case 21: simbolo= "*"; break;
            case 22: simbolo= "&&"; break;
            case 23: simbolo= "||"; break;
            case 24: simbolo= "!"; break;
            case 25: simbolo= "{"; break;
            case 26: simbolo= "}"; break;
            case 27: simbolo= "("; break;
            case 28: simbolo= ")"; break;
            case 29: simbolo= "="; break;
            case 30: simbolo= "=="; break;
            case 31: simbolo= "!="; break;
            case 32: simbolo= ">="; break;
            case 33: simbolo= "<="; break;
            case 34: simbolo= "<"; break;
            case 35: simbolo= "int"; break;
            case 36: simbolo= ">"; break;
            case 37: simbolo= "ID_VAR"; break;
            case 38: simbolo= "function"; break;
            case 39: simbolo= "array"; break;
            case 40: simbolo= "["; break;
            case 41: simbolo= "]"; break;
            case 42: simbolo= "return"; break;
            case 43: simbolo="boolean"; break;
            case 44: simbolo= "CONSTANTE"; break;
            case 45: simbolo="of"; break;
            case 46: simbolo="malloc"; break;
            case 47: simbolo="free"; break;
            case 48: simbolo="size"; break;
            case 49: simbolo="contains"; break;
            case 50: simbolo= "NUM_ENTERO"; break;
            case 51: simbolo= "NUM_REAL"; break;
        }
        return simbolo;
    }
    
    /* Para el siguiente incremento de la practica... */
    public static void writeToFile(String text) {
        FileWriter file = null;
        PrintWriter pw = null;
        try {
            file = new FileWriter("ficheroTraducido");
            pw = new PrintWriter(file);
            pw.println(text);
            file.close();
        } catch (IOException ex) {
            System.out.println("Problema con el fichero. El programa se cerrara");
            System.exit(0);
        }
   }
  
  /**
   * Transforma el numero dado en octal a binario mientras se encuentre
   * dentro del intervalo representable con MAX_UNSIGNED_SIZE_IN_BITS
   * @param num numero a transformar
   * @param row fila, por si existe un error
   * @param column columna, por si existe un error
   * @return numero transformado en binario
   */
  public static String octalToBinary(String num, int row, int column) {
      if(num.endsWith("O")) {
          warning(W_UPPERCASED_ENDING_CHAR, row, column, num);
      }
      num = num.substring(0, num.length()-1);
      int intNum = Integer.parseInt(num,8);
      String binOut = Integer.toBinaryString(intNum);
      if(binOut.length() > MAX_UNSIGNED_SIZE_IN_BITS) {
          error(E_NUMBER_OUT_OF_RANGE,row,column,num);
          return "";
      } else {
          return binOut;
      }
  }
  
  /**
   * Transforma el numero dado en hexadecimal a binario mientras se encuentre
   * dentro del intervalo representable con MAX_UNSIGNED_SIZE_IN_BITS
   * @param num numero a transformar
   * @param row fila, por si existe un error
   * @param column columna, por si existe un error
   * @return numero transformado en binario
   */
  public static String hexaToBinary(String num, int row, int column) {
      if(num.endsWith("H")) {
          warning(W_UPPERCASED_ENDING_CHAR, row, column, num);
      }
      num = num.substring(0, num.length()-1);
      int intNum = Integer.parseInt(num,16);
      String binOut = Integer.toBinaryString(intNum);
      if(binOut.length() > MAX_UNSIGNED_SIZE_IN_BITS) {
          error(E_NUMBER_OUT_OF_RANGE,row,column,num);
          return "";
      } else {
          return binOut;
      }
  }
  
  /**
   * Transforma un numero decimal a binario mientras se encuentre
   * dentro del intervalo representable:
   *     Si es Entero/Natural se emplea MAX_SIGNED_SIZE_IN_BITS
   *     Si es Real: MAX_SIGNED_SIZE_IN_BITS . MAX_UNSIGNED_SIZE_IN_BITS
   *        para las partes entera y decimal respectivamente
   * 
   * NOTA: Los numeros naturales y enteros se representan en complemento a 2,
   *       explicitando ceros a la izquierda si es necesario, de esta forma
   *       se puede diferenciar correctamente cualquier número decimal.
   * @param num numero a transformar
   * @param row fila, por si existe un error
   * @param column columna, por si existe un error
   * @return numero transformado en binario
   */
  public static String decimalToBinary(String num, int row, int column) {
      if(num.contains(".")) {
          if(num.startsWith(".")) {
              warning(W_IMPLICIT_DOUBLE_PART,row,column,num);
              num = "0" + num;
          }
          if(num.endsWith(".")) {
              warning(W_IMPLICIT_DOUBLE_PART,row,column,num);
              num = num.concat("0");
          }
          String numParts[] = num.split("\\.");
          String binIntPart = integerPartToBinary(numParts[0],row,column);
          if("".equals(binIntPart)) {
              return "";
          }
          String binDecPart = decimalPartToBinary(numParts[1],row,column);
          if("".equals(binIntPart)) {
              return "";
          }
          return binIntPart + "." + binDecPart;
      } else {
          return integerPartToBinary(num,row,column);
      }
  }
  
  /**
   * Funcion auxiliar para transformar la parte entera (natural o entera).
   * El limite de bits viene definido por MAX_SIGNED_SIZE_IN_BITS
   * @param num numero a transformar
   * @param row fila, por si existe un error
   * @param column columna, por si existe un error
   * @return numero transformado en binario
   */
  private static String integerPartToBinary(String num, int row, int column) {
      int intNum = Integer.parseInt(num);
      if(intNum >= Math.pow(2,MAX_SIGNED_SIZE_IN_BITS) ||
            intNum < (-1)*(Math.pow(2,MAX_SIGNED_SIZE_IN_BITS))){
          error(E_NUMBER_OUT_OF_RANGE,row,column,num);
              return "";
      } else {
          String binOut = Integer.toBinaryString(intNum);
          if(intNum < 0) {
              return binOut.substring(binOut.length()-MAX_UNSIGNED_SIZE_IN_BITS,binOut.length());
          }
          while(binOut.length() < MAX_SIGNED_SIZE_IN_BITS) {
              binOut = "0" + binOut;
          }
          binOut = "0" + binOut;
          return binOut;
      }
  }
  
  /**
   * Funcion auxiliar para transformar la parte decimal. El limite de bits
   * viene definido por MAX_UNSIGNED_SIZE_IN_BITS
   * @param num numero a transformar
   * @param row fila, por si existe un error
   * @param column columna, por si existe un error
   * @return numero transformado en binario
   */
  private static String decimalPartToBinary(String num, int row, int column) {
      int decNum = Integer.parseInt(num);
      if(decNum >= Math.pow(2,MAX_UNSIGNED_SIZE_IN_BITS)) {
          error(E_NUMBER_OUT_OF_RANGE,row,column,num);
          return "";
      } else {
          return Integer.toBinaryString(decNum);
      }
  }
  
  /**
   * Devuelve el numero insertado binario mientras se encuentre
   * dentro del intervalo representable con MAX_UNSIGNED_SIZE_IN_BITS
   * @param num numero a transformar
   * @param row fila, por si existe un error
   * @param column columna, por si existe un error
   * @return numero transformado en binario
   */
  public static String binaryToBinary(String num, int row, int column) {
      if(num.endsWith("B")) {
          warning(W_UPPERCASED_ENDING_CHAR, row, column, num);
      }
      num = num.substring(0, num.length()-1);
      if(num.length() > MAX_UNSIGNED_SIZE_IN_BITS) {
          error(E_NUMBER_OUT_OF_RANGE,row,column,num);
          return "";
      }
      return num;
  }
}

