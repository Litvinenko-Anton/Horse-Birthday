public class Main {

/*
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
*/

    private static int boardSize = 8;

    private static int[][] boardB = new int[boardSize][boardSize]; // двухмерный массив синего коня
    private static int[][] boardG = new int[boardSize][boardSize];

    private static boolean[][] filterG = new boolean[boardSize][boardSize]; //фильтр синего коня
    private static boolean[][] filterB = new boolean[boardSize][boardSize];

    private static int yb; //координаты коня для массива
    private static int xb;
    private static int yg;
    private static int xg;

    private static int que[][] = new int[boardSize * boardSize][3];     // массив шагов для всей доски
    private static int qb, qe;
    private static int min_step = 11111;

    // Синий конь
    private static char xb_char = 'a'; // a,b,c,d,e,f,g,h
    private static int yb_int = 1;     // 1,2,3,4,5,6,7,8
    // Синий конь
    private static char xg_char = 'h';
    private static int yg_int = 8;

    public static void main(String[] args) {

        //конвертирование
        xb = convertX(xb_char);
        xg = convertX(xg_char);
        yb = convertY(yb_int);
        yg = convertY(yg_int);

     /*   // позиция коня в массиве
        System.out.print("Синий конь:    Y" + yb + "  ");
        System.out.println("X" + xb);
        System.out.print("Зеленый конь:  Y" + yg + "  ");
        System.out.println("X" + xg);
        System.out.println();   */

        // проверка постановки коней на доску
        if ((xb | yb | xg | yg) > boardSize - 1 || (xb | yb | xg | yg) < 0) {

            System.out.println("Один из коней стоит не на шахматной доске!");
            System.out.println("Для доски 8х8 значения могут быть:");
            System.out.println("X = a,b,c,d,e,f,g,h");
            System.out.println("Y = 1,2,3,4,5,6,7,8");
            System.out.println("Проверте своих коней.");

        } else {

            System.out.println("Синий конь стоит на:   " + xb_char + yb_int);
            System.out.println("Зеленый конь стоит на: " + xg_char + yg_int);
            System.out.println();


            // запуск цикла для синего коня
            qb = -1;
            qe = -1;
            add(xb, yb, 0, true);
            while (qb < qe) stepAll(true);

            // запуск цикла для зеленого коня
            qb = -1;
            qe = -1;
            add(xg, yg, 0, false);
            while (qb < qe) stepAll(false);

            //поиск точки пересечения
            int h = find();

            //формирование ответа
            answer(h);

        }

    }

    //заполняем 2 массива
    static void add(int x, int y, int k, boolean blue) {
        qe++;
        que[qe][0] = x;
        que[qe][1] = y;
        que[qe][2] = k;
        if (blue) {
            filterB[x][y] = true;
            boardB[x][y] = k;
        } else {
            filterG[x][y] = true;
            boardG[x][y] = k;
        }
    }

    //за сколько ходов можно дойти в каждую точку поля
    static void stepAll(boolean blue) {

        int[][] step = {
                {2, 1},
                {2, -1},
                {1, 2},
                {1, -2},
                {-1, 2},
                {-1, -2},
                {-2, 1},
                {-2, -1}
        };


        qb++;

        for (int i = 0; i < boardSize; i++) {
            int u = que[qb][0] + step[i][0];
            int v = que[qb][1] + step[i][1];
            int z = que[qb][2] + 1;

            if (blue) {
                if (u >= 0 && u < boardSize && v >= 0 && v < boardSize && !filterB[u][v])
                    add(u, v, z, true);

            } else {
                if (u >= 0 && u < boardSize && v >= 0 && v < boardSize && !filterG[u][v])
                    add(u, v, z, false);

            }

        }

    }


    // находит кратчайшую точку пересечения
    static int find() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int u = boardG[i][j];
                int v = boardB[i][j];

                if (u == v && u < min_step)
                    min_step = u;
                //Если значение min_step не изменилось - значит точка пересечения не найдена
                // и коням ожидается IMPOSSIBLE
            }
        }

        return min_step;
    }


    static void answer(int h) {
        if (h == 11111) {
            System.out.println("IMPOSSIBLE");
        } else if (h == 0) {
            System.out.println("Кони уже могут праздновать день рождения!!!");
        } else {
            System.out.println("Необходимое количество ходов до вечеринки: " + find());
        }

    }


    //конвертирует char в arrInt
    static int convertX(char x_char) {
        int x;
        switch (x_char) {
            case 'a':
                x = 0;
                break;

            case 'b':
                x = 1;
                break;

            case 'c':
                x = 2;
                break;

            case 'd':
                x = 3;
                break;

            case 'e':
                x = 4;
                break;

            case 'f':
                x = 5;
                break;

            case 'g':
                x = 6;
                break;

            case 'h':
                x = 7;
                break;

            default:
                x = 0;
        }

        return x;
    }

    //конвертирует int в arrInt
    static int convertY(int y_int) {
        int x = y_int - 1;
        return x;
    }

}
