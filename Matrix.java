import java.util.Arrays;
import java.util.Scanner;

public class Matrix{
	private double[][] matrix;
	private int height;
	private int width;

	public Matrix(double[][] matrix){
		height = matrix.length;
		width = matrix[0].length;
		this.matrix = new double[height][width];
		
		for(int i = 0; i < height; i++){
			this.matrix[i] = Arrays.copyOf(matrix[i], width);
		}
	}

	public Matrix(int height, int width){
		this.height = height;
		this.width = width;

		matrix = new double[height][width];
	}

	public static Matrix sum(Matrix m1, Matrix m2){
		if(m1.height != m2.height || m1.width != m2.width){
			throw new WrongMatrixSizeException();
		}

		Matrix result = new Matrix(m1.height, m1.width);

		for(int i = 0; i < result.height; i++){
			for(int j = 0; j < result.width; j++){
				result.matrix[i][j] = m1.matrix[i][j] + m2.matrix[i][j];
			}
		}

		return result;
	}

	public static Matrix product(Matrix m1, Matrix m2){
		if(m1.width != m2.height){
			throw new WrongMatrixSizeException();
		}

		Matrix result = new Matrix(m1.height, m2.width);

		for(int i = 0; i < result.height; i++){
			for(int j = 0; j < result.width; j++){
				double sumValue = 0;

				for(int k = 0; k < m1.width; k++){
					sumValue += m1.matrix[i][k]*m2.matrix[k][j];
				}

				result.matrix[i][j] = sumValue;
			}
		}
		return result;
	}

	public void gauss(){
		for(int i = 1; i < height; i++){
			for(int j = 0; j < i; j++){
				double factor = -1 * (matrix[i][j] / matrix[j][j]);

				matrix[i][j] = 0;
				for(int k = 0; k < width; k++){
					if(j == k) continue;
					matrix[i][k] += matrix[j][k]*factor;
				}
			}
		}
	}

	public static Matrix enterMatrix(int height, int width, Scanner reader){
		double[][] dataDouble = new double[height][width];

		System.out.println("Введите матрицу " + height + "x" + width + " :");

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				dataDouble[i][j] = reader.nextInt();
			}
		}

		System.out.println("Ввод закончен");
		return new Matrix(dataDouble);
	}

	@Override
	public String toString(){
		String res = "";

		for(double[] vector : matrix){
			res += "|";
			for(double num : vector){
				res += " " + num + " ";
			}
			res += "|\n";
		}

		return res;
	}
}

class WrongMatrixSizeException extends RuntimeException{

}