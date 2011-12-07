package pdlcompilerproyect;

/**
 * Clase de apoyo para el lexer, contiene mensajes de error y avisos,
 * metodos para imprimirlos, limites del tamaño de bits y las funciones para
 * transformar los distintos formatos de numeros a binario
 * 
 * @version 1
 * @author JRuiz
 */
class Utility {
    
  public static final int MAX_SIGNED_SIZE_IN_BITS = 15;
  public static final int MAX_UNSIGNED_SIZE_IN_BITS = 16;
  
  public static final int E_NUMBER_OUT_OF_RANGE = 0; 
  public static final int E_ILLEGAL_ENDING_CHAR = 1; 
  public static final int W_UPPERCASED_ENDING_CHAR = 0; 
  public static final int W_IMPLICIT_DOUBLE_PART = 1;
  
  private static final String errorMsg[] = {
    "Error Lexico: linea[<linea>] columna[<columna>] El numero [<numero>] esta fuera de rango",
    "Error Lexico: linea[<linea>] columna[<columna>] El numero [<numero>] no tiene una notacion conocida",
    };

  private static final String warningMsg[] = {
    "Alerta lexico: linea[<linea>] columna[<columna>] El numero [<numero>] se supone que esta en <notacion>",
    "Alerta lexico linea[<linea>] columna[<columna>] El numero [<numero>] necesita como minimo un digito <posicion> de la parte decimal"
    }; 
  
  /**
   * Imprime el error asociado al codigo que se le pasa como parametro
   * @param code codigo del error
   * @param row fila donde se da el error
   * @param column columna donde se da el error
   * @return numero que ha provocado el error
   */
  public static void error(int code, int row, int column, String number) {
      String msg = errorMsg[code];
      msg = msg.replace("<linea>", String.valueOf(row));
      msg = msg.replace("<columna>", String.valueOf(column));
      msg = msg.replace("<numero>", number);
      System.out.print(msg);
  }
  
  /**
   * Imprime el aviso/incidencia asociado al codigo que se le pasa como parametro
   * @param code codigo de la incidencia
   * @param row fila donde se dio la incidencia
   * @param column columna donde se dio la incidencia
   * @return numero que ha provocado la incidencia
   */
  public static void warning(int code, int row, int column, String number) {
      String msg = warningMsg[code];
      msg = msg.replace("<linea>", String.valueOf(row));
      msg = msg.replace("<columna>", String.valueOf(column));
      msg = msg.replace("<numero>", number);
      if(number.endsWith("O")) {
          msg = msg.replace("<notacion>", "Octal");
      } else if(number.endsWith("H")) {
          msg = msg.replace("<notacion>", "Hexadecimal");
      } else if(number.endsWith("B")) {
          msg = msg.replace("<notacion>", "Binario");
      }
      if(number.startsWith(".")) {
          msg = msg.replace("<posicion>", "delante");
      } else if(number.endsWith(".")) {
          msg = msg.replace("<posicion>", "detras");
      }
      System.out.print(msg + "\n    >> ");
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

