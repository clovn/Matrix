import java.util.Arrays;
import java.util.Scanner;

public class Matrix{
	public double[][] array;
	public int rowsCount;
	public int colsCount;

	public Matrix(){
		enterMatrix();
	}

	private Matrix(int rowsCount, int colsCount){
		this.rowsCount = rowsCount;
		this.colsCount = colsCount;

		array = new double[rowsCount][colsCount];
	}

	public static Matrix getSingleMatrix(int rowsCount, int colsCount){
		Matrix e = new Matrix(rowsCount, colsCount);

		for(int i = 0; i < rowsCount; i++){
			for(int j = 0; j < colsCount; j++){
				if(i == j){
					e.array[i][j] = 1;
				} else {
					e.array[i][j] = 0;
				}
			}
		}

		return e;
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
				array[i] = Arrays.stream(sc.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
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
		double[][] tmp = new double[rowsCount][colsCount];
		int rank = 0;
		boolean notEmpty = false;

		for(int i = 0; i < rowsCount; i++){
			tmp[i] = Arrays.copyOf(array[i], array[i].length);
		}

		for (int row = 0; row < rowsCount; row++) {
            for (int i = row + 1; i < rowsCount; i++) {
                double factor = -1 * (tmp[i][row] / tmp[row][row]);
                for (int j = 0; j < colsCount; j++) {
                	tmp[i][j] += factor * tmp[row][j];
                }
            }
        }

        for(int i = 0; i < rowsCount; i++){
        	notEmpty = false;
        	for(int j = 0; j < colsCount; j++){
        		if(tmp[i][j] != 0) notEmpty = true;
        	}
        	if(notEmpty) rank++;
        }

        return rank;
	}

	public Matrix solve(Matrix b){
		if(rowsCount != colsCount) return null;

		Matrix solved = gauss(Matrix.getSingleMatrix(rowsCount, colsCount)).product(b);

		return solved;
	}

	public Matrix gauss(Matrix single){
		for (int row = 0; row < rowsCount; row++) {
            for (int i = row + 1; i < rowsCount; i++) {
                double factor = -1 * (array[i][row] / array[row][row]);
                for (int j = 0; j < colsCount; j++) {
                	array[i][j] += factor * array[row][j];
					single.array[i][j] += factor * single.array[row][j];
                }
            }
                
           	int rowInv = rowsCount - row - 1;

			for(int i = rowInv - 1; i >= 0; i--){
               	double factor = -1*(array[i][rowInv] / array[rowInv][rowInv]);
                for(int j = 0; j < colsCount; j++){
                	array[i][j] += factor * array[rowInv][j];
                	single.array[i][j] += factor * single.array[rowInv][j];
                }
            }
    	}

    	for(int i = 0; i < rowsCount; i++){
        	double del = array[i][i];

        	for(int j = 0; j < colsCount; j++){
            	single.array[i][j] /= del;
       		}
    	}

        return single;
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