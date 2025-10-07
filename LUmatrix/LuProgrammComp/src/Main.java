import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите размерность матрицы: ");
        int n = scanner.nextInt();

        // Расширенная матрица: n строк, n+1 столбцов (последний — свободные члены)
        double[][] matrix_a = new double[n][n + 1];
        double[][] matrix_l = new double[n][n];
        double[][] matrix_u = new double[n][n];
        double[] mas_x = new double[n];
        double[] mas_y = new double[n];

        // === Ввод расширенной матрицы A ===
        System.out.println("Введите расширенную матрицу (" + n + "x" + (n + 1) + "):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (j == n) {
                    System.out.print("Свободный член [" + (i + 1) + "][" + (j + 1) + "]: ");
                } else {
                    System.out.print("Элемент [" + (i + 1) + "][" + (j + 1) + "]: ");
                }
                matrix_a[i][j] = scanner.nextDouble();
            }
        }

        // === Инициализация L и U  ===
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix_l[i][j] = 0.0;
                matrix_u[i][j] = 0.0;
                if (i == j) {
                    matrix_u[i][j] = 1.0; // U — единичная диагональ
                }
            }
        }

        // ==== ЭТАП 1: LU-разложение  ====
        // Первый столбец L
        for (int i = 0; i < n; i++) {
            matrix_l[i][0] = matrix_a[i][0];
        }

        // Первая строка U (кроме диагонали, она = 1)
        for (int j = 1; j < n; j++) {
            matrix_u[0][j] = matrix_a[0][j] / matrix_l[0][0];
        }

        // Остальные элементы
        for (int k = 1; k < n; k++) {
            // Вычисление k-го столбца L (от строки k до n-1)
            for (int i = k; i < n; i++) {
                matrix_l[i][k] = matrix_a[i][k];
                for (int m = 0; m < k; m++) {
                    matrix_l[i][k] -= matrix_l[i][m] * matrix_u[m][k];
                }
            }

            // Вычисление k-й строки U (от столбца k+1 до n-1)
            for (int j = k + 1; j < n; j++) {
                matrix_u[k][j] = matrix_a[k][j];
                for (int m = 0; m < k; m++) {
                    matrix_u[k][j] -= matrix_l[k][m] * matrix_u[m][j];
                }
                matrix_u[k][j] /= matrix_l[k][k]; // деление на диагональный элемент L
            }
        }

        // === Вывод матриц L и U ===
        System.out.println("\nМатрица L:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%10.4f ", matrix_l[i][j]);
            }
            System.out.println();
        }

        System.out.println("\nМатрица U:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%10.4f ", matrix_u[i][j]);
            }
            System.out.println();
        }

        // ==== ЭТАП 2: Прямая подстановка (L * y = b) ====
        mas_y[0] = matrix_a[0][n] / matrix_l[0][0];
        for (int k = 1; k < n; k++) {
            mas_y[k] = matrix_a[k][n];
            for (int m = 0; m < k; m++) {
                mas_y[k] -= matrix_l[k][m] * mas_y[m];
            }
            mas_y[k] /= matrix_l[k][k];
        }

        System.out.println("\nМассив Y (решение L*y = b):");
        for (int i = 0; i < n; i++) {
            System.out.printf("%10.4f ", mas_y[i]);
        }
        System.out.println();

        // ==== ЭТАП 3: Обратная подстановка (U * x = y) ====
        mas_x[n - 1] = mas_y[n - 1]; // т.к. U[n-1][n-1] = 1
        for (int k = n - 2; k >= 0; k--) {
            mas_x[k] = mas_y[k];
            for (int m = k + 1; m < n; m++) {
                mas_x[k] -= matrix_u[k][m] * mas_x[m];
            }
            // деление не требуется, так как U[k][k] = 1
        }

        System.out.println("\nРешение системы (массив X):");
        for (int i = 0; i < n; i++) {
            System.out.printf("%10.4f ", mas_x[i]);
        }
        System.out.println();
    }
}
