package sudoku;

public class SolveSudoku
{
	public static void main(String args[]) {
		
		String sdatei = "sudoku.txt";
		if (args.length>0)
			sdatei=args[0];
		Sudoku s = new Sudoku(sdatei);
		System.out.println("Eingabe:");
		s.ausgabe();
		System.out.println();
		if (!s.check_sudoku()) {
			System.out.println("Fehlerhaftes Sudoku!");
			System.exit(0);
		}
		System.out.println("Lösung:");
		if (!s.solve_sudoku(1)) {
			System.out.println("Keine Lösung gefunden");
		}
		System.out.println("Anzahl Loesungen: " + s.geloeste);
		
		// System.out.println(s.numbers_row(2));
		// System.out.println(s.numbers_col(2));
		// System.out.println(s.numbers_square(2,2));
		// System.out.println(s.valid_numbers(2,2));
		// System.out.println(s.valid_numbers(8,8));
		// System.out.println(s.is_complete());
		// System.out.println(s.check_row(2));
		// System.out.println(s.check_col(2));
		// System.out.println(s.check_square(2,2));
		// System.out.println(s.is_complete());
		// s.fill_obvious();
		// s.ausgabe();
		// System.out.println(s.is_complete());
	}
}
