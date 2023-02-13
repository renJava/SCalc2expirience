package academy.kata;

import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

//  "654Ъiёtgh" + "e47и4ergj"     //
//    "YejrgЁ1Ыi7" - "ejrgЁ1"     //
//    "ЫkegjЫ12Йы" - "kegjЫ12"    //
//    "ehjrg12" *   9             //
//      "e47и4ergj" / 10          //
//      "47игrgЫЁё" / 2          //


public class SCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(in);
        String announcement = """

            Правила ввода:
                                        
        Вводите строчные операнды (не более 10 символов каждый). Начинайте ввод строго с двойных кавычек.
        Первый операнд - всегда строчный, не более 10 символов, например, "jbBЪ5678Ёю".
        Второй операнд, как первый, но только при сложении и вычитании (+,-), а второй операнд - также в кавычках.

        При умножении и делении (*,/) второй операнд - натуральное число <=10 - БЕЗ КАВЫЧЕК!!!:
            """;
        out.println(announcement);
        String expression = scanner.nextLine(); // Сканируем всю строку с выражением целиком в expression
        String validateInOut = isValidate(expression);
        out.println("\n\"" + validateInOut + "\"");
    }

    //    Задаем все переменные метода.
    public static String isValidate  (String expression) {
        int lengthT;


//                                      Сложение и вычитание

//     Регулярное выражение для + и - : ^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *[+,-] *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *

        if (expression.matches(
                "^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *[+,-] *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *")) {

            var trimExpressionPM = expression.trim();
            lengthT = trimExpressionPM.length();

            var cutToFindQuotes = trimExpressionPM.substring(1, lengthT -1);
            var quotePosition0 = cutToFindQuotes.indexOf('\"');
            var quotePosition1 = cutToFindQuotes.lastIndexOf('\"');

            var aPM = cutToFindQuotes.substring(0, quotePosition0);
            var bPM = cutToFindQuotes.substring(quotePosition1 + 1, lengthT - 2);

            return  (trimExpressionPM.indexOf('+') > -1) ? PM.sPlus(aPM, bPM) : PM.sCut(aPM, bPM);
        }

//                                      Умножение и деление

//     Регулярное выражение для * и / : ^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\"\s*[*,\/]\s*(?:[1-9]|10) *$

        if (expression.matches(
                "^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *[*,/] *(?:[1-9]|10) *$")) {

            var trimExpressionMD = expression.trim();
            lengthT = trimExpressionMD.length();

            var cutToFindOneQuote = trimExpressionMD.substring(1);
            var quotePosition0 = cutToFindOneQuote.indexOf('\"');

            var aMD = cutToFindOneQuote.substring(0, quotePosition0);
            var bMD = Integer.parseInt(cutToFindOneQuote.substring(lengthT -3, lengthT-1).trim());

            return  (trimExpressionMD.indexOf('*') > -1) ? MD.sMultiple(aMD, bMD) : MD.sDivision(aMD, bMD);
        }

//                Отладка: контроль ввода и промежуточных вычислений:

        return "!!!Некорректный ввод!!!";

    }

//            Методы в 2-х классах

   private static class PM {
        static String sPlus(String a, String b) {        //Сложение
             return a + b;
        }
    // При Сложении - Конкатенация строк

       static String sCut(String a, String b) {             //Вычитание
            var substrBegin = a.indexOf(b);
            return (substrBegin > -1) ? a.substring(0, substrBegin) +
                    a.substring(substrBegin + b.length()) : a;
       }
    // При Вычетании - вырезаем найденное слово из строки или возвращаем уменьшаемое обратно
   }

//          Блок умножения и деления
    private static class MD {
         static String sMultiple(String a, int b) {      //Умножение
            String sMultiple = a.repeat(b);
            return (sMultiple.length() <= 40) ? sMultiple : sMultiple.substring(0, 40) + "...";
         }
    // При Умножении - повторяем заданное слово b раз и обрезаем результат после 40-го символа

        static String sDivision(String a, int b) {       //Деление
            return (a.length() >= b) ? a.substring(0, a.length() / b) : "Делитель больше делимого";
        }
    }

}

