import java.util.Scanner;

public class Model {
	public static void main(String[] args){
		boolean isClose = false;
		Scanner sc = new Scanner(System.in);

		while(!isClose){
			System.out.println("1.умножение матриц\n2.поиск ранга матрицы\n3.сложение матриц\n4.Решение обратной матрицей\n5.Решение методом крамера\n6.выход");
			int choose;
			try{
				choose = Integer.parseInt(sc.nextLine());
			} catch (Exception e){
				System.out.println("Wrong input. Try again.");
				continue;
			}
			switch(choose){
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
					Matrix a = new Matrix();
					Matrix b = Matrix.enterFree(a.rowsCount);
					Matrix solved = a.solve(b);
					if(solved != null){
						System.out.println(solved);
					}
				}

				case 5 -> {
					Matrix a = new Matrix();
					Matrix b = Matrix.enterFree(a.rowsCount);
					a.cramer(b);
				}

				case 6 -> {
					isClose = true;
				}

				default -> {
					System.out.println("Wrong input"); 
				}
			}
		}
	}
}