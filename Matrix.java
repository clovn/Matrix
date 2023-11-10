import java.util.Arrays;
import java.util.Scanner;

public class Matrix{
	private double[][] array;
	private int rowsCount;
	private int colsCount;

	public Matrix(){
		enterMatrix();
	}

	private Matrix(int rowsCount, int colsCount){
		this.rowsCount = rowsCount;
		this.colsCount = colsCount;

		array = new double[rowsCount][colsCount];
	}

	private void enterMatrix(){
		Scanner sc = new Scanner(System.in);

		System.out.print("Введите размер матрицы в формате m n: ");
		rowsCount = sc.nextInt();
		colsCount = sc.nextInt();
		sc.nextLine();
		array = new double[rowsCount][colsCount];

		System.out.println("Выберите вариант ввода матрицы:\n1.поэлементно\n2.построчно");
		int choose = Integer.parseInt(sc.nextLine());

		if (choose == 1) {
			for(int i = 0; i < rowsCount; i++){
				for(int j = 0; j < colsCount; j++){
					System.out.print("Введите элемент на позиции (" + (i + 1) + ", " + (j + 1) + "): ");
					array[i][j] = Double.parseDouble(sc.nextLine());
				}
			}
		} else if(choose == 2) {
			Scanner con = new Scanner(System.in);
			for(int i = 0; i < rowsCount; i++){
				System.out.print("Введите строку " + (i + 1) + " через пробел: ");
				array[i] = Arrays.stream(sc.nextLine().split(" ")).mapToDouble(Integer::parseInt).toArray();
			}
		}

	}

	public Matrix product(Matrix matrix){
		if(colsCount == matrix.rowsCount){
			Matrix result = new Matrix(rowsCount, matrix.colsCount);

			for(int i = 0; i < result.rowsCount; i++){
				for(int j = 0; j < result.colsCount; j++){
					double sumValue = 0;

					for(int k = 0; k < colsCount; k++){
						sumValue += array[i][k]*matrix.array[k][j];
					}

					result.array[i][j] = sumValue;
				}
			}

			return result;

		} else if(rowsCount == matrix.colsCount) {
			System.out.println("умножение A*B невозможно, но возможно умножение B*A. Умножить?(y/n)");

			Scanner sc = new Scanner(System.in);
			String ans = sc.nextLine();

			if(!(ans.equals("y") || ans.equals("yes") || ans.equals("да") || ans.equals("д"))) return null;

			Matrix result = new Matrix(matrix.rowsCount, colsCount);

			for(int i = 0; i < result.rowsCount; i++){
				for(int j = 0; j < result.colsCount; j++){
					double sumValue = 0;

					for(int k = 0; k < matrix.colsCount; k++){
						sumValue += matrix.array[i][k]*array[k][j];
					}

					result.array[i][j] = sumValue;
				}
			}

			return result;

		} else return null;
	}

	public Matrix sum(Matrix matrix){
		if(rowsCount != matrix.rowsCount || colsCount != matrix.colsCount) return null;

		Matrix result = new Matrix(rowsCount, colsCount);

		for(int i = 0; i < result.rowsCount; i++){
			for(int j = 0; j < result.colsCount; j++){
				result.array[i][j] = array[i][j] + matrix.array[i][j];
			}
		}

		return result;
	}

	public int rank(){
        int rank = 0;

        // Приведение матрицы к верхнетреугольному виду методом Гаусса
        for (int row = 0; row < rowsCount; row++) {
            // Поиск ведущего элемента
            int lead = 0;
            while (lead < colsCount && array[row][lead] == 0) {
                lead++;
            }

            if (lead < colsCount) {
                // Перестановка строк, чтобы ведущий элемент был ненулевым
                swapRows(row, lead);

                // Обнуление элементов под ведущим
                for (int i = row + 1; i < rowsCount; i++) {
                    double factor = array[i][lead] / array[row][lead];
                    for (int j = 0; j < colsCount; j++) {
                        array[i][j] -= factor * array[row][j];
                    }
                }
                rank++;
            }
        }

        return rank;
	}

	private void swapRows(int row1, int row2){
		double[] temp = array[row1];
        array[row1] = array[row2];
        array[row2] = temp;
	}

	@Override
	public String toString(){
		String res = "Матрица (" + rowsCount + "*" + colsCount + "):\n";

		for(double[] vector : array){
			for(double num : vector){
				res += " " + num + " ";
			}
			res += "\n";
		}

		return res;
	}
}