package utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Validador extends PlainDocument {
	//váriavel que irá armazenar o número máximo de caracteres permitido
	private int Limite;
	//construtor personalizado
	
	public Validador(int limite) {
		super();
		Limite = limite;
	}
	//método interno para validar o limite de caracteres
		//Badlocation é o tratamento de exceções
		public void insertString(int cfs, String str, AttributeSet a) throws BadLocationException {
			//se o leimite não for ultrapasado permitir a digitação 
			if ((getLength() + str.length()) <= Limite) {
				super.insertString(cfs, str, a);
				}
		}
}
