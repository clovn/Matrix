import java.util.Arrays;
import java.util.Scanner;

public class Matrix{
	public double[][] array;
	public int rowsCount;
	public int colsCount;

	public Matrix(){
		enterMatrix();
	}

	public Matrix(Matrix toCopy){
		rowsCount = toCopy.rowsCount;
		colsCount = toCopy.colsCount;

		array = new double[rowsCount][colsCount];

		for(int i = 0; i < rowsCount; i++){
			array[i] = Arrays.copyOf(toCopy.array[i], toCopy.array[i].length);
		}
	}

	private Matrix(int rowsCount, int colsCount){
		this.rowsCount = rowsCount;
		this.colsCount = colsCount;

		array = new double[rowsCount][colsCount];
	}

	private Matrix(Matrix matrix, Matrix free){
		array = new double[matrix.rowsCount][matrix.colsCount + 1];

		for(int i = 0; i < matrix.rowsCount; i++){
			for(int j = 0; j < matrix.colsCount; j++){
				array[i][j] = matrix.array[i][j];
			}
			array[i][matrix.colsCount] = free.array[i][0];
		}
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

		

		while(true){
			System.out.print("Введите размер матрицы в формате m n: ");

			try{
				rowsCount = sc.nextInt();
				colsCount = sc.nextInt();

				if(rowsCount >= 1 && colsCount >= 1) break;
				else throw new Exception();


			} catch (Exception e){
				System.out.println("Wrong input. Try again.\n");
				sc.nextLine();
			}
		}
			
		sc.nextLine();
		array = new double[rowsCount][colsCount];

		int choose;

		while(true){
			try{
				System.out.println("Выберите вариант ввода матрицы:\n1.поэлементно\n2.построчно");
				choose = Integer.parseInt(sc.nextLine());
				break;
			} catch (Exception e){
				System.out.println("Wrong input. Try again.");
			}
		}

		if (choose == 1) {
			for(int i = 0; i < rowsCount; i++){
				for(int j = 0; j < colsCount; j++){
					while(true){
						try{
							System.out.print("Введите элемент на позиции (" + (i + 1) + ", " + (j + 1) + "): ");
							array[i][j] = Double.parseDouble(sc.nextLine());
							break;
						} catch (Exception e){
							System.out.println("Wrong input. Try again.");
						}
					}
				}
			}
		} else if(choose == 2) {
			Scanner con = new Scanner(System.in);
			for(int i = 0; i < rowsCount; i++){
				while(true){
					try{
						System.out.print("Введите строку " + (i + 1) + " через пробел: ");
						array[i] = Arrays.stream(sc.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
						break;
					} catch (Exception e) {
						System.out.println("Wrong input. Try again.");
					}
				}
			}
		}
	}

	public static Matrix enterFree(int rowsCount){
		Scanner sc = new Scanner(System.in);
		Matrix res = new Matrix(rowsCount, 1);

		System.out.println("Введите матрицу-столбец свободных членов");
		for(int i = 0; i < rowsCount; i ++){
			for(int j = 0; j < 1; j++){
				while(true){
					try{
						System.out.print("Введите элемент на позиции (" + (i + 1) + ", " + (j + 1) + "): ");
						res.array[i][j] = Double.parseDouble(sc.nextLine());
						break;
					} catch (Exception e) {
						System.out.println("Wrong input. Try again");
					}	
				}
			}	
		}
		return res;
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
        		if(Math.abs(tmp[i][j]) > 1e-10) notEmpty = true;
        	}
        	if(notEmpty) rank++;
        }

        return rank;
	}

	public Matrix solve(Matrix b){
		Matrix ras = new Matrix(this, b);
		if(ras.rank() != rank()) {
			System.out.println("Нет решений");
			return null;
		} else {
			if(rank() < colsCount){
				System.out.println("Бесконечное количество решений");
				return null;
			}
		}

		Matrix solved = gauss(Matrix.getSingleMatrix(rowsCount, colsCount)).product(b);

		return solved;
	}

	private Matrix gauss(Matrix single){
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

	private Matrix swapCols(int s, Matrix free){
		Matrix res = new Matrix(this);

		for(int i = 0; i < rowsCount; i++){
			res.array[i][s] = free.array[i][0];
		}

		return res;
	}

	private double det(){
		Matrix a = new Matrix(this);

        for (int i = 0; i < a.rowsCount - 1; i++) {
            for (int j = i + 1; j < a.rowsCount; j++) {
                double factor = a.array[j][i] / a.array[i][i];
                for (int k = i; k < a.rowsCount; k++) {
                    a.array[j][k] -= factor * a.array[i][k];
                }
            }
        }

        double determinant = 1.0;
        for (int i = 0; i < a.rowsCount; i++) {
            determinant *= a.array[i][i];
        }

        return determinant;
	}

	public void cramer(Matrix free){
		double det = det();

		if(rowsCount == colsCount && det != 0){
			for(int i = 0; i < colsCount; i++){
				Matrix tmp = swapCols(i, free);

				System.out.println(tmp);
				System.out.println(tmp.det());

				System.out.println("x" + (i + 1) + " = " + (tmp.det()/det));
			}
		} else {
			System.out.println("Решение системы методом крамера невозможно");
		}
	}


	@Override
	public String toString(){
		String res = "Матрица (" + rowsCount + "*" + colsCount + "):\n";

		for(double[] vector : array){
			for(double num : vector){
				res += num + " ";
			}
			res += "\n";
		}

		return res;
	}
}