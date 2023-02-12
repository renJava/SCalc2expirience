package academy.kata;

import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

//  "654Ъiёtgh" + "e47и4ergj"     //
//    "ejrgЁ12" - "kegjЫ123"     //
//    "ЫkegjЫ12Йы" - "kegjЫ12"     //
//    "ehjrg12" *   9           //
//      "e47и4ergj" / 10       //


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
        String validate = isValidate(expression);
        out.println("\n\"" + validate + "\"");
    }

    //    Задаем все переменные метода.
    public static String isValidate  (String expression) {
        boolean validatePM;
        boolean validateMD;
        String validateControlPrint;
        String trimExpressionPM = "";
        String trimExpressionMD = "";
        String aPM = "";
        String bPM = "";
        String aMD = "";
        int bMD = 0;
        int[] quotePosition = new int[3];
        int lengthET;


//                                      Сложение и вычитание

//     Регулярное выражение для + и - : ^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *[+,-] *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *

        if (expression.matches(
                "^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *[+,-] *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *")) {
            validatePM = true;

            trimExpressionPM = expression.trim();
            lengthET = trimExpressionPM.length();

            var cutTofFindQuote = trimExpressionPM.substring(1, lengthET-1);
            var quotePosition0 = cutTofFindQuote.indexOf('\"');
            var quotePosition1 = cutTofFindQuote.lastIndexOf('\"');
/*
            int i = 0;
            int j = 0;
            while (i < (lengthET -1) && j <= 1) {        //Запоминаем позиции кавычек в теле выражения PM без краёв
                i++;
                if (trimExpressionPM.charAt(i) == '\"') {
                    quotePosition[j] = i; j++;
                }
            }
*/

            aPM = trimExpressionPM.substring(1, quotePosition0+1);
            bPM = trimExpressionPM.substring(quotePosition1 + 2, lengthET -1);

            return  (trimExpressionPM.indexOf('+') > -1) ? PM.sPlus(aPM, bPM) : PM.sCut(aPM, bPM);
        }
        else validatePM = false;


//                                      Умножение и деление

//     Регулярное выражение для * и / : ^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\"\s*[*,\/]\s*(?:[1-9]|10) *$

        if (expression.matches(
                "^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *[*,/] *(?:[1-9]|10) *$")) {
            validateMD = true;

            trimExpressionMD = expression.trim();

            lengthET = trimExpressionMD.length();
//            var cutTofFindQuote = trimExpressionPM.substring(1);
//            var quotePosition0 = cutTofFindQuote.indexOf('\"');
            int i = 0;
            int j = 0;
            while (i < (lengthET -1) && j <= 0) {        //Запоминаем позиции кавычек в теле выражения MD без краёв
                i++;
                if (trimExpressionMD.charAt(i) == '\"') {
                    quotePosition[j] = i; j++;
                }
            }
            aMD = trimExpressionMD.substring(1, quotePosition[0]);
            bMD = Integer.parseInt(trimExpressionMD.substring(lengthET -2, lengthET).trim());

            return  (trimExpressionMD.indexOf('*') > -1) ? MD.sMultiple(aMD, bMD) : MD.sDivision(aMD, bMD);

        }
        else validateMD = false;


//                Отладка: контроль ввода и промежуточных вычислений:

        if (validatePM || validateMD) {
            validateControlPrint = "Правильный ввод";
            out.println("trimExpressionPM: " + trimExpressionPM + "trimExpressionMD: " + trimExpressionMD + "\n");

                out.println("\nvalidatePM: " + validatePM + ", aPM: " + aPM + ", bPM: " + bPM + "\n");
                out.println("\nvalidateMD: " + validateMD + ", aMD: " + aMD + ", bMD: " + bMD + "\n");
        }
        else validateControlPrint = "!!!Некорректный ввод!!!";

    return validateControlPrint;
    }


//            Работающие методы в классах

    static class PM {
        static String sPlus(String a, String b) {        //Сложение
        return a + b;
        }
    // При Сложении - Конкатенация строк

    static String sCut(String a, String b) {             //Вычитание
        var substBegin = a.indexOf(b);
        return (substBegin > -1) ? a.substring(0, substBegin) +
                a.substring(substBegin + b.length()) : a;
    }
    // При Вычетании - вырезаем найденное слово из строки или возвращаем уменьшаемое обратно
    }


    static class MD {
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

