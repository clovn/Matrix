import java.util.Scanner;

public class Model {
	public static void main(String[] args){
		boolean isClose = false;
		Scanner sc = new Scanner(System.in);

		while(!isClose){
			System.out.println("1.умножение матриц\n2.поиск ранга матрицы\n3.сложение матриц\n4.выход");
			switch(sc.nextInt()){
				case 1 -> {
					Matrix a = new Matrix();
					Matrix b = new Matrix();
					Matrix res = a.product(b);

					if(res == null){
						System.out.println("умножение невозможно");
					} else {
						System.out.print(res);
					}
				}
				case 2 -> {
					Matrix a = new Matrix();
					int rank = a.rank();
					System.out.println(rank);
				}
				case 3 -> {
					Matrix a = new Matrix();
					Matrix b = new Matrix();
					Matrix res = a.sum(b);

					if(res == null){
						System.out.println("сложение невозможно");
					} else {
						System.out.print(res);
					}

				}
				case 4 -> {
					isClose = true;
				}
				default -> {
					System.out.println("Wrong input"); 
				}
			}
		}
	}
}