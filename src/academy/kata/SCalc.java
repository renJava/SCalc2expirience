package academy.kata;

import java.util.Scanner;

//  "654Ъiёtgh" + "e47и4ergj"     //
//    "ejrgЁ12" - "kegjЫ123"     //
//    "ЫkegjЫ12Йы" - "kegjЫ12"     //
//    "ehjrg12" *   9           //
//      "e47и4ergj" / 10       //


public class SCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String announcement = """
                Вводите строчные операнды (не более 10 символов каждый) и строго в двойных кавычках.
                Первый операнд - всегда строчный, не более 10 символов, например, "123456789Ё".
                Второй - как первый, но только при сложении или вычитании.
                При умножении или делении второй операнд - натуральное число <=10 - без кавычек:
                """;
        System.out.println(announcement);
        String expression = scanner.nextLine(); // Сканируем всю строку с выражением целиком в expression
        String validate = isValidate(expression);
        System.out.println("\"" + validate + "\"");
    }

    //     Регулярное выражение для + и - : ^\s*\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\"\s*[+,-]\s*\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\"\s*
    //                                      ^ *"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}" *[+,-] *"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}" *
    //
    //     Регулярное выражение для * и / : ^\s*\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\"\s*[*,\/]\s*(?:[1-9]|10)\s*$
    //      3-й вариант

    //    Задаем все переменные метода.
    public static String isValidate  (String expression) {
        boolean validateSC;
        boolean validateMD;
        String validateControlPrint;
        String trimExpressionSC = "";
        String trimExpressionMD = "";
        String operandSC1 = "";
        String operandSC2 = "";
        String operandMD1 = "";
        int operandMD2 = 0;
        int[] quotePosition = new int[2];
        int lengthT;

        if (expression.matches(
                "^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *[+,-] *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *")) {
            validateSC = true;
            trimExpressionSC = expression.trim();

            lengthT = trimExpressionSC.length();
            int i = 0;                                      //Запоминаем позиции кавычек в теле выражения
            int j = 0;
            while (i < (lengthT-1) && j <= 1) {
                i++;
                if (trimExpressionSC.charAt(i) == '\"') {
                    quotePosition[j] = i; j++;
                }
            }

            operandSC1 = trimExpressionSC.substring(1, quotePosition[0]);
            operandSC2 = trimExpressionSC.substring(quotePosition[1]+1, lengthT-1);

            if (trimExpressionSC.indexOf('+') > -1) {
                System.out.println("\nРезультат сложения: "+"\"" + sPlus(operandSC1, operandSC2) + "\""+"\n\n");

            } else {
                System.out.println("\nРезультат вычитания: "+"\"" + sCut(operandSC1, operandSC2) + "\""+"\n\n");
            }
        }


        else validateSC = false;



                            //        Умножение и деление


        if (expression.matches(
                "^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *[*,/] *(?:[1-9]|10) *$")) {
            validateMD = true;
            trimExpressionMD = expression.trim();


            lengthT = trimExpressionMD.length();
            int i = 0;                                      //Запоминаем позиции кавычек в теле выражения
            int j = 0;
            while (i < (lengthT-1) && j == 0) {
                i++;
                if (trimExpressionMD.charAt(i) == '\"') {
                    quotePosition[j] = i; j++;
                }
            }
            operandMD1 = trimExpressionMD.substring(1, quotePosition[0]);
            operandMD2 = Integer.parseInt(trimExpressionMD.substring(lengthT-2, lengthT).trim());

            if (trimExpressionMD.indexOf('*') > -1) {
                System.out.println("\nРезультат умножения: "+"\"" + sMultiple(operandMD1, operandMD2) + "\""+"\n\n");

            }

            else {
                System.out.println("\nРезультат деления: "+"\"" + sDivision(operandMD1, operandMD2) + "\""+"\n\n");
            }

        }
        else validateMD = false;


//                Контроль ввода и промежуточных вычислений:

        if (validateSC || validateMD) {
            validateControlPrint = "Правильный ввод";
            System.out.println("trimExpressionSC: " + trimExpressionSC + "trimExpressionMD: " + trimExpressionMD + "\n");


            System.out.println("\nvalidateSC: " + validateSC + ", operandSC1: " + operandSC1 +
                    ", operandSC2: " + operandSC2 + "\n");
            System.out.println("\nvalidateMD: " + validateMD + ", operandMD1: " + operandMD1 +
                    ", operandMD2: " + operandMD2 + "\n");

        }

        else {
            validateControlPrint = "\n!!!Неправильный ввод!!!";}
        return validateControlPrint;

    }


//            Работающие методы
        public static String sPlus(String a, String b) {      //Сложение
            return a + b;
        }
        // При Сложении - Конкатенация строк

        public static String sCut(String a, String b) {       //Вычитание
            var substBegin = a.indexOf(b);
            return (substBegin > -1) ? a.substring(0, substBegin) +
                    a.substring(substBegin + b.length()) : a;
        }
        // При Вычетании - вырезаем найденное слово из строки или возвращаем уменьшаемое обратно

        public static String sMultiple(String a, int b) {      //Умножение
            String sMultiple = a.repeat(b);
            return (sMultiple.length() <= 40) ? sMultiple : sMultiple.substring(0, 40) + "...";
        }
        // При Умножении - повторяем заданное слово b раз и обрезаем результат после 40-го символа

        public static String sDivision(String a, int b) {       //Деление
            return (a.length()>=b)? a.substring(0, a.length() / b): "Делитель больше делимого";
        }



//    Донор МЕТОДОВ

/*    public enum Operation {

        sPlus {
            public String action(String a, String b) {      //Сложение
                return (a.length() <= 10 || b.length() <= 10) ? a + b :
                        "Ошибка ввода: длина строчных слагаемых - \n" +
                                "не более 10 символов (без учета кавычек)";
            }
        // При Сложении - Конкатенация строк
        },
        sMinus {
            public String action(String a, String b) {       //Вычитание
                if (a.length() > 10 || b.length() > 10) {
                    return "Ошибка ввода: длина делимой строки - \n" +
                            "не более 10 символов (без учета кавычек), а делитель - целое не больше 10";
                }
                var subst = a.indexOf(b);
                return (subst > -1) ? a.substring(0, subst - 1) + a.substring(subst + b.length()) : a;
            }
        // При Вычетании - вырезаем найденное слово из строки или возвращаем уменьшаемое обратно
        },


        sMultiple {
            public String action(String a, int b) {      //Умножение
                if (a.length() > 10 || b > 10) {
                    return "Ошибка ввода: множимое - строка не более 10 символов, \n" +
                            "без учёта кавычек, а множитель - целое не больше 10";
                }
                String sMultiple = a.repeat(b);
                return (sMultiple.length() <= 40) ? sMultiple : sMultiple.substring(0, 39) + "...";
            }
        // При Умножении - повторяем заданное слово b раз и обрезаем результат на 40 символе
        },


        sDivision {
            public String action(String a, int b) {       //Деление
                return (a.length() <= 10 || b <= 10) ? a.substring(0, a.length() / b - 1) :
                        "Ошибка ввода: длина делимой строки - \n" +
                                "не более 10 символов (без учета кавычек), а делитель - целое не больше 10";
            }
        // При Делении - делим длину строки на b и получаем целое число знаков в результате
        };

    }
 */
}
