import java.util.Scanner;

public class Hauss {
    public static void main(String[] args) {

        final int n = 4;
        // Создаём расширенную матрицу: n строк, n+1 столбцов
        double[][] matrix_a = new double[n][n + 1];
        double[] mass_b = new double[n];

        Scanner scanner = new Scanner(System.in);

        // Вводим элементы матрицы построчно
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                System.out.print("Введите элемент [" + i + "][" + j + "]: ");
                matrix_a[i][j] = scanner.nextDouble();
            }
        }


        //проверка условия matrix_a(k,k)
        for (int k = 0; k < n-1; k++ ) {
            if (Math.abs(matrix_a[k][k]) < 0.00001){
                int m = k+1;
                while ((Math.abs(matrix_a[m][k]) > 0.00001) || (m > n)){

                    m=+1;

                    }

                //меняем местами строки k и m
                for (int j = 0; j < n; j++){

                    double temp = matrix_a[k][j];
                    matrix_a[k][j]=matrix_a[m][j];
                    matrix_a[m][j]=temp;

                }
            }


        }






        // Выводим матрицу для проверки
        System.out.println("\nВаша матрица:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                System.out.printf("%8.2f ", matrix_a[i][j]);
            }
            System.out.println();
        }

        scanner.close(); // Закрываем Scanner
    }
}