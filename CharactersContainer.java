
package com.example.androidtts;

import java.util.Arrays;
import java.util.Comparator;


/*
 * This class contains the arrays with all the characters that other classes need to use.
 * It provides all the functions to manage these characters like 'is' functions, conversion functions and index functions.
 * Practically this is the database with the supported characters and phonemes of this program.
 */
public class CharactersContainer {
	private static GreekPhoneme[] greekPhonemesSingle, greekPhonemesDual;
	private static TextSymbol[] symbols;
	private static EnglishLetter[] englishLetters;
	private static int greekPhonemesSingleSize = 0;
	private static int greekPhonemesDualSize = 0;
	private static int symbolsSize = 0;
	private static int englishLettersSize = 0;

	private static final String[] numbersNames = {
	    "μηδέν",
	    "ένα",
	    "δύο",
	    "τρία",
	    "τέσσερα",
	    "πέντε",
	    "έξι",
	    "εφτά",
	    "οχτώ",
	    "εννιά"
	};

	//Comparators that are used for sorting the arrays so the I can use binary search in all the functions that search them
    public static Comparator<GreekPhoneme> GreekPhonemesComparator = new Comparator<GreekPhoneme>() {

        @Override
        public int compare(GreekPhoneme g1, GreekPhoneme g2) {
        	return g1.getPhoneme().compareTo(g2.getPhoneme());
        }
    };

    public static Comparator<EnglishLetter> EnglishLettersComparator = new Comparator<EnglishLetter>() {

        @Override
        public int compare(EnglishLetter e1, EnglishLetter e2) {
        	return e1.getLetter()-e2.getLetter();
        }
    };

    public static Comparator<TextSymbol> SymbolsComparator = new Comparator<TextSymbol>() {

        @Override
        public int compare(TextSymbol c1, TextSymbol c2) {
        	return c1.getSymbol()-c2.getSymbol();
        }
    };



	/*
	 * Initializing all the static variables
	 */
	static{
		symbols = new TextSymbol[] {
				new TextSymbol('!', "θαυμαστικό","παραγωντικό"),
				new TextSymbol('"', "διπλά εισαγωγικά","διπλά εισαγωγικά"),
				new TextSymbol('#', "δίεση","νούμερο"),
				new TextSymbol('$', "δολλάριο","δολλάριο"),
				new TextSymbol('&', "συμπλεκτικό σύμβολο","συμπλεκτικό σύμβολο"),
				new TextSymbol('\'', "μονό εισαγωγικό","μονό εισαγωγικό"),
				new TextSymbol('(', "ανοιχτή παρένθεση","ανοιχτή παρένθεση"),
				new TextSymbol(')', "κλειστή παρένθεση","κλειστή παρένθεση"),
				new TextSymbol('*', "αστερίσκος","επί"),
				new TextSymbol('+', "σύν","σύν"),
				new TextSymbol(',', "κόμμα","κόμμα"),
				new TextSymbol('-', "παύλα","μείον"),
				new TextSymbol('.', "τελεία","κόμμα"),
				new TextSymbol('/', "κάθετος","πρός"),
				new TextSymbol(':', "άνω κάτω τελεία","άνω κάτω τελεία"),
				new TextSymbol(';', "ερωτηματικό","ερωτηματικό"),
				new TextSymbol('<', "μικρότερο από","μικρότερο από"),
				new TextSymbol('=', "είσον","είσον"),
				new TextSymbol('>', "μεγαλύτερο από","μεγαλύτερο από"),
				new TextSymbol('?', "αγγλικό ερωτηματικό","αγγλικό ερωτηματικό"),
				new TextSymbol('@', "παπάκι","παπάκι"),
				new TextSymbol('[', "ανοιχτή αγγύλη","ανοιχτή αγγύλη"),
				new TextSymbol('\\', "κάθετος","κάθετος"),
				new TextSymbol(']', "κλειστή αγγύλη","κλειστή αγγύλη"),
				new TextSymbol('^', "αγκύλη","εις την"),
				new TextSymbol('_', "κάτω παύλα","κάτω παύλα"),
				new TextSymbol('`', "τόννος","τόννος"),
				new TextSymbol('{', "ανοιχτή αγγύλη","ανοιχτή αγγύλη"),
				new TextSymbol('|', "κάθετoς","κάθετoς"),
				new TextSymbol('}', "κλειστή αγγύλη","κλειστή αγγύλη"),
				new TextSymbol('~', "περισπωμένη","περισπωμένη"),
				new TextSymbol('%', "τοις εκατό","τοις εκατό"),
				new TextSymbol('€', "ευρώ","ευρώ"),
				new TextSymbol('£', "λίρες","λίρα"),
				new TextSymbol('·', "άνω τελεία","άνω τελεία"),
				new TextSymbol('≪', "διπλά εισαγωγικά","πολύ μικρότερο από"),
				new TextSymbol('≫', "διπλά εισαγωγικά","πολύ μεγαλύτερο από"),
				new TextSymbol('×', "επί","επί"),
				new TextSymbol('÷', "διά","διά"),
				new TextSymbol('√', "τετραγωνική ρίζα","τετραγωνική ρίζα"),
				new TextSymbol('≠', "διαφορετικό του","διαφορετικό του"),
				new TextSymbol('≥', "μεγαλύτερο ή ίσο του","μεγαλύτερο ή ίσο του"),
				new TextSymbol('≤', "μικρότερο ή ίσο του","μικρότερο ή ίσο του"),
				new TextSymbol('±', "σύν πλήν","σύν πλήν"),
				new TextSymbol('∓', "πλήν σύν","πλήν σύν"),
				new TextSymbol('∞', "άπειρο","άπειρο"),
				new TextSymbol('∫', "ολοκλήρωμα","ολοκλήρωμα"),
				new TextSymbol('∑', "σύνολο","σύνολο"),
				new TextSymbol('ƒ', "γκούλντεν","γκούλντεν"),
				new TextSymbol('؋', "αφγάνια","αφγάνια"),
				new TextSymbol('₫', "ντονγκ","ντονγκ"),
				new TextSymbol('ლ', "λάρια","λάρια"),
				new TextSymbol('₵', "σέντια","σέντια"),
				new TextSymbol('¥', "γιέν","γιέν"),
				new TextSymbol('₪', "νέο σέκελ","νέο σέκελ"),
				new TextSymbol('₩', "γουόν κορέας","γουόν κορέας"),
				new TextSymbol('₡', "κολόν","κολόν"),
				new TextSymbol('₭', "κίπ","κίπ"),
				new TextSymbol('ރ', "ρουφίγια","ρουφίγια"),
				new TextSymbol('₮', "τουγκρίκ","τουγκρίκ"),
				new TextSymbol('৲', "τάκα","τάκα"),
				new TextSymbol('৳', "τάκα","τάκα"),
				new TextSymbol('₦', "Νάιρα","Νάιρα"),
				new TextSymbol('₴', "χρίβνια","χρίβνια"),
				new TextSymbol('₲', "γκουαρανί","γκουαρανί"),
				new TextSymbol('₤', "λίρες του σουδάν","λίρες του σουδάν"),
				new TextSymbol('฿', "μπάχτ","μπάχτ"),
				new TextSymbol('₱', "πέσο φιλιππίνων","πέσο φιλιππίνων"),
				new TextSymbol('₣', "φράγκα σί έφ πί","φράγκα σί έφ πί"),
				new TextSymbol('圓', "δολλάρια του χονγκ κονγκ","δολλάρια του χονγκ κονγκ")};

		englishLetters = new EnglishLetter[] {
				new EnglishLetter('a', "α", "έι"),
				new EnglishLetter('b', "μπ", "μπί"),
				new EnglishLetter('c', "κ", "σί"),
				new EnglishLetter('d', "ντ", "ντί"),
				new EnglishLetter('e', "ε", "ί"),
				new EnglishLetter('f', "φ", "έφ"),
				new EnglishLetter('g', "γκ", "τζί"),
				new EnglishLetter('h', "χ", "έιτζ"),
				new EnglishLetter('i', "ι", "άι"),
				new EnglishLetter('j', "τζ", "τζέι"),
				new EnglishLetter('k', "κ", "κέι"),
				new EnglishLetter('l', "λ", "έλ"),
				new EnglishLetter('m', "μ", "έμ"),
				new EnglishLetter('n', "ν", "έν"),
				new EnglishLetter('o', "ο", "όου"),
				new EnglishLetter('p', "π", "πί"),
				new EnglishLetter('q', "κ", "κιού"),
				new EnglishLetter('r', "ρ", "άρ"),
				new EnglishLetter('s', "σ", "ές"),
				new EnglishLetter('t', "τ", "τί"),
				new EnglishLetter('u', "ου", "γιού"),
				new EnglishLetter('v', "β", "βί"),
				new EnglishLetter('w', "γ", "ντάμπλεγιου"),
				new EnglishLetter('x', "ξ", "έξ"),
				new EnglishLetter('y', "ι", "γουάι"),
				new EnglishLetter('z', "ζ", "ζέντ"),
				new EnglishLetter('A', "α", "έι"),
				new EnglishLetter('B', "μπ", "μπί"),
				new EnglishLetter('C', "κ", "σί"),
				new EnglishLetter('D', "ντ", "ντί"),
				new EnglishLetter('E', "ε", "ί"),
				new EnglishLetter('F', "φ", "έφ"),
				new EnglishLetter('G', "γκ", "τζί"),
				new EnglishLetter('H', "χ", "έιτζ"),
				new EnglishLetter('I', "ι", "άι"),
				new EnglishLetter('J', "τζ", "τζέι"),
				new EnglishLetter('K', "κ", "κέι"),
				new EnglishLetter('L', "λ", "έλ"),
				new EnglishLetter('M', "μ", "έμ"),
				new EnglishLetter('N', "ν", "έν"),
				new EnglishLetter('O', "ο", "όου"),
				new EnglishLetter('P', "π", "πί"),
				new EnglishLetter('Q', "κ", "κιού"),
				new EnglishLetter('R', "ρ", "άρ"),
				new EnglishLetter('S', "σ", "ές"),
				new EnglishLetter('T', "τ", "τί"),
				new EnglishLetter('U', "ου", "γιού"),
				new EnglishLetter('V', "β", "βί"),
				new EnglishLetter('W', "γ", "ντάμπλεγιου"),
				new EnglishLetter('X', "ξ", "έξ"),
				new EnglishLetter('Y', "ι", "γουάι"),
				new EnglishLetter('Z', "ζ", "ζέντ") };

		greekPhonemesSingle = new GreekPhoneme[] {
				new GreekPhoneme("α", 'a'), new GreekPhoneme("β", 'v'),
				new GreekPhoneme("γ", 'y'), new GreekPhoneme("δ", 'w'),
				new GreekPhoneme("ε", 'e'), new GreekPhoneme("ζ", 'z'),
				new GreekPhoneme("η", 'i'), new GreekPhoneme("θ", '8'),
				new GreekPhoneme("ι", 'i'), new GreekPhoneme("κ", 'k'),
				new GreekPhoneme("λ", 'l'), new GreekPhoneme("μ", 'm'),
				new GreekPhoneme("ν", 'n'), new GreekPhoneme("ξ", 'x'),
				new GreekPhoneme("ο", 'o'), new GreekPhoneme("π", 'p'),
				new GreekPhoneme("ρ", 'r'), new GreekPhoneme("σ", 's'),
				new GreekPhoneme("ς", 's'), new GreekPhoneme("τ", 't'),
				new GreekPhoneme("υ", 'i'), new GreekPhoneme("φ", 'f'),
				new GreekPhoneme("χ", 'h'), new GreekPhoneme("ψ", 'q'),
				new GreekPhoneme("ω", 'o'), new GreekPhoneme("Α", 'a'),
				new GreekPhoneme("Β", 'v'), new GreekPhoneme("Γ", 'y'),
				new GreekPhoneme("Δ", 'w'), new GreekPhoneme("Ε", 'e'),
				new GreekPhoneme("Ζ", 'z'), new GreekPhoneme("Η", 'i'),
				new GreekPhoneme("Θ", '8'), new GreekPhoneme("Ι", 'i'),
				new GreekPhoneme("Κ", 'k'), new GreekPhoneme("Λ", 'l'),
				new GreekPhoneme("Μ", 'm'), new GreekPhoneme("Ν", 'n'),
				new GreekPhoneme("Ξ", 'x'), new GreekPhoneme("Ο", 'o'),
				new GreekPhoneme("Π", 'p'), new GreekPhoneme("Ρ", 'r'),
				new GreekPhoneme("Σ", 's'), new GreekPhoneme("Τ", 't'),
				new GreekPhoneme("Υ", 'i'), new GreekPhoneme("Φ", 'f'),
				new GreekPhoneme("Χ", 'h'), new GreekPhoneme("Ψ", 'q'),
				new GreekPhoneme("Ω", 'o'), new GreekPhoneme("ά", 'A'),
				new GreekPhoneme("έ", 'E'), new GreekPhoneme("ή", 'I'),
				new GreekPhoneme("ί", 'I'), new GreekPhoneme("ό", 'O'),
				new GreekPhoneme("ύ", 'I'), new GreekPhoneme("ώ", 'O'),
				new GreekPhoneme("Ά", 'A'), new GreekPhoneme("Έ", 'E'),
				new GreekPhoneme("Ή", 'I'), new GreekPhoneme("Ί", 'I'),
				new GreekPhoneme("Ό", 'O'), new GreekPhoneme("Ύ", 'I'),
				new GreekPhoneme("Ώ", 'O'), new GreekPhoneme("ϊ", 'i'),
				new GreekPhoneme("ϋ", 'i'), new GreekPhoneme("Ϊ", 'i'),
				new GreekPhoneme("Ϋ", 'i'), new GreekPhoneme("ΐ", 'I'),
				new GreekPhoneme("ΰ", 'I') };

		greekPhonemesDual = new GreekPhoneme[] {
				new GreekPhoneme("αι", 'e'), new GreekPhoneme("ει", 'i'),
				new GreekPhoneme("οι", 'i'), new GreekPhoneme("ου", 'u'),
				new GreekPhoneme("αί", 'E'), new GreekPhoneme("εί", 'I'),
				new GreekPhoneme("οί", 'I'), new GreekPhoneme("ού", 'U'),
				new GreekPhoneme("μπ", 'b'), new GreekPhoneme("ντ", 'd'),
				new GreekPhoneme("γκ", 'g'), new GreekPhoneme("γγ", 'g'),
				new GreekPhoneme("τσ", 'c'), new GreekPhoneme("τζ", 'j'),
				new GreekPhoneme("Αι", 'e'), new GreekPhoneme("Ει", 'i'),
				new GreekPhoneme("Οι", 'i'), new GreekPhoneme("Ου", 'u'),
				new GreekPhoneme("Αί", 'E'), new GreekPhoneme("Εί", 'I'),
				new GreekPhoneme("Οί", 'I'), new GreekPhoneme("Ού", 'Y'),
				new GreekPhoneme("Μπ", 'b'), new GreekPhoneme("Ντ", 'd'),
				new GreekPhoneme("Γκ", 'g'), new GreekPhoneme("Γγ", 'g'),
				new GreekPhoneme("Τσ", 'c'), new GreekPhoneme("Τζ", 'j'),
				new GreekPhoneme("ββ", 'v'), new GreekPhoneme("γγ", 'g'),
				new GreekPhoneme("δδ", 'w'), new GreekPhoneme("ζζ", 'z'),
				new GreekPhoneme("θθ", '8'), new GreekPhoneme("κκ", 'k'),
				new GreekPhoneme("λλ", 'l'), new GreekPhoneme("μμ", 'm'),
				new GreekPhoneme("νν", 'n'), new GreekPhoneme("ξξ", 'x'),
				new GreekPhoneme("ππ", 'p'), new GreekPhoneme("ρρ", 'r'),
				new GreekPhoneme("σσ", 's'), new GreekPhoneme("ττ", 't'),
				new GreekPhoneme("φφ", 'f'), new GreekPhoneme("χχ", 'h'),
				new GreekPhoneme("ψψ", 'q'), new GreekPhoneme("ΒΒ", 'v'),
				new GreekPhoneme("ΜΠ", 'b'), new GreekPhoneme("ΝΤ", 'd'),
				new GreekPhoneme("ΓΚ", 'g'), new GreekPhoneme("ΓΓ", 'g'),
				new GreekPhoneme("ΤΣ", 'c'), new GreekPhoneme("ΤΖ", 'j'),
				new GreekPhoneme("ΑΙ", 'e'), new GreekPhoneme("ΕΙ", 'i'),
				new GreekPhoneme("ΟΙ", 'i'), new GreekPhoneme("ΟΥ", 'u'),
				new GreekPhoneme("ΑΊ", 'E'), new GreekPhoneme("ΕΊ", 'I'),
				new GreekPhoneme("ΟΊ", 'I'), new GreekPhoneme("ΟΎ", 'U'),
				new GreekPhoneme("ΓΓ", 'g'), new GreekPhoneme("ΔΔ", 'w'),
				new GreekPhoneme("ΖΖ", 'z'), new GreekPhoneme("ΘΘ", '8'),
				new GreekPhoneme("ΚΚ", 'k'), new GreekPhoneme("ΛΛ", 'l'),
				new GreekPhoneme("ΜΜ", 'm'), new GreekPhoneme("ΝΝ", 'n'),
				new GreekPhoneme("ΞΞ", 'x'), new GreekPhoneme("ΠΠ", 'p'),
				new GreekPhoneme("ΡΡ", 'r'), new GreekPhoneme("ΣΣ", 's'),
				new GreekPhoneme("ΤΤ", 't'), new GreekPhoneme("ΦΦ", 'f'),
				new GreekPhoneme("ΧΧ", 'h'), new GreekPhoneme("ΨΨ", 'q'),
				new GreekPhoneme("μΠ", 'b'), new GreekPhoneme("νΤ", 'd'),
				new GreekPhoneme("γΚ", 'g'), new GreekPhoneme("γΓ", 'g'),
				new GreekPhoneme("τΣ", 'c'), new GreekPhoneme("τΖ", 'j'),
				new GreekPhoneme("αΙ", 'e'), new GreekPhoneme("εΙ", 'i'),
				new GreekPhoneme("οΙ", 'i'), new GreekPhoneme("οΥ", 'u'),
				new GreekPhoneme("αΊ", 'E'), new GreekPhoneme("εΊ", 'I'),
				new GreekPhoneme("οΊ", 'I'), new GreekPhoneme("οΎ", 'U'),
				new GreekPhoneme("βΒ", 'v'), new GreekPhoneme("γΓ", 'g'),
				new GreekPhoneme("δΔ", 'w'), new GreekPhoneme("ζΖ", 'z'),
				new GreekPhoneme("θΘ", '8'), new GreekPhoneme("κΚ", 'k'),
				new GreekPhoneme("λΛ", 'l'), new GreekPhoneme("μΜ", 'm'),
				new GreekPhoneme("νΝ", 'n'), new GreekPhoneme("ξΞ", 'x'),
				new GreekPhoneme("πΠ", 'p'), new GreekPhoneme("ρΡ", 'r'),
				new GreekPhoneme("σΣ", 's'), new GreekPhoneme("τΤ", 't'),
				new GreekPhoneme("φΦ", 'f'), new GreekPhoneme("χΧ", 'h'),
				new GreekPhoneme("ψΨ", 'q'), new GreekPhoneme("Ββ", 'v'),
				new GreekPhoneme("Γγ", 'g'), new GreekPhoneme("Δδ", 'w'),
				new GreekPhoneme("Ζζ", 'z'), new GreekPhoneme("Θθ", '8'),
				new GreekPhoneme("Κκ", 'k'), new GreekPhoneme("Λλ", 'l'),
				new GreekPhoneme("Μμ", 'm'), new GreekPhoneme("Νν", 'n'),
				new GreekPhoneme("Ξξ", 'x'), new GreekPhoneme("Ππ", 'p'),
				new GreekPhoneme("Ρρ", 'r'), new GreekPhoneme("Σσ", 's'),
				new GreekPhoneme("Ττ", 't'), new GreekPhoneme("Φφ", 'f'),
				new GreekPhoneme("Χχ", 'h'), new GreekPhoneme("Ψψ", 'q') };

		greekPhonemesSingleSize = greekPhonemesSingle.length;
		greekPhonemesDualSize = greekPhonemesDual.length;
		symbolsSize = symbols.length;
		englishLettersSize = englishLetters.length;

		Arrays.sort(symbols,SymbolsComparator);
		Arrays.sort(greekPhonemesSingle,GreekPhonemesComparator);
		Arrays.sort(greekPhonemesDual,GreekPhonemesComparator);
		Arrays.sort(englishLetters,EnglishLettersComparator);

	}//end of default constructor





    //GETTER FUNCTIONS
	public static EnglishLetter[] getEnglishLetters() {
		return englishLetters;
	}

	public static TextSymbol[] getSymbols() {
		return symbols;
	}

	public static GreekPhoneme[] getGreekPhonemesSingle() {
		return greekPhonemesSingle;
	}

	public static GreekPhoneme[] getGreekPhonemesDual() {
		return greekPhonemesDual;
	}

	public static int getEnglishLettersSize() {
		return englishLettersSize;
	}

	public static int getGreekPhonemesSingleSize() {
		return greekPhonemesSingleSize;
	}

	public static int getGreekPhonemesDualSize() {
		return greekPhonemesDualSize;
	}

	public static int getSymbolsSize() {
		return symbolsSize;
	}



	//BINARY SEARCH IS USED IN ALL THE FUNCTIONS THAT THE SEARCH THE ARRAYS

	//INDEXES FUNCTIONS USING BINARY SEARCH
	public static int indexOfSymbol(char c) {
		 	int first = 0;
		    int last  = symbolsSize;
		    int mid;
		    while (first < last) {
		        mid = first + ((last - first) / 2);
		        if (c-symbols[mid].getSymbol() < 0) {
		            last = mid;
		        } else if (c-symbols[mid].getSymbol() > 0) {
		            first = mid + 1;
		        } else {
		            return mid;
		        }
		    }
		    return -1;
	}

	public static int indexOfGreekPhonemeSingle(String s) {
		int first = 0;
	    int last  = greekPhonemesSingleSize;
	    int mid;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (s.compareTo(greekPhonemesSingle[mid].getPhoneme()) < 0) {
	            last = mid;
	        } else if (s.compareTo(greekPhonemesSingle[mid].getPhoneme()) > 0) {
	            first = mid + 1;
	        } else {
	            return mid;
	        }
	    }
	    return -1;
	}

	public static int indexOfGreekPhonemeDual(String s) {
		int first = 0;
	    int last  = greekPhonemesDualSize;
	    int mid;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (s.compareTo(greekPhonemesDual[mid].getPhoneme()) < 0) {
	            last = mid;
	        } else if (s.compareTo(greekPhonemesDual[mid].getPhoneme()) > 0) {
	            first = mid + 1;
	        } else {
	            return mid;
	        }
	    }
	    return -1;
	}

	public static int indexOfEnglishLetter(char c) {
		int first = 0;
	    int last  = englishLettersSize;
	    int mid;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (c-englishLetters[mid].getLetter() < 0) {
	            last = mid;
	        } else if (c-englishLetters[mid].getLetter() > 0) {
	            first = mid + 1;
	        } else {
	            return mid;
	        }
	    }
	    return -1;
	}




	//CONVERSION FUNCTIONS USING BINARY SEARCH
	public static char convertToSimplePhoneme(String s) {
		if (s.length() == 1) {
			int first = 0;
		    int last  = greekPhonemesSingleSize;
		    int mid,index=0;
		    while (first < last) {
		        mid = first + ((last - first) / 2);
		        if (s.compareTo(greekPhonemesSingle[mid].getPhoneme()) < 0) {
		            last = mid;
		        } else if (s.compareTo(greekPhonemesSingle[mid].getPhoneme()) > 0) {
		            first = mid + 1;
		        } else {
		            index= mid;
		            return  greekPhonemesSingle[index].getSimplePhoneme();
		        }
		    }
		    return ' ';
		} else if (s.length() == 2) {
			int first = 0;
		    int last  = greekPhonemesDualSize;
		    int mid,index=0;
		    while (first < last) {
		        mid = first + ((last - first) / 2);
		        if (s.compareTo(greekPhonemesDual[mid].getPhoneme()) < 0) {
		            last = mid;
		        } else if (s.compareTo(greekPhonemesDual[mid].getPhoneme()) > 0) {
		            first = mid + 1;
		        } else {
		            index= mid;
		            return  greekPhonemesDual[index].getSimplePhoneme();
		        }
		    }
			return ' ';
		}

		return ' ';
	}

	public static String symbolToWords(char c) {
		int first = 0;
	    int last  = symbolsSize;
	    int mid,index=0;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (c-symbols[mid].getSymbol() < 0) {
	            last = mid;
	        } else if (c-symbols[mid].getSymbol() > 0) {
	            first = mid + 1;
	        } else {
	            index= mid;
	            return  symbols[index].getSymbolToWords();
	        }
	    }
		return "";
	}

	public static String symbolToMathMeaning(char c) {
		int first = 0;
	    int last  = symbolsSize;
	    int mid,index=0;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (c-symbols[mid].getSymbol() < 0) {
	            last = mid;
	        } else if (c-symbols[mid].getSymbol() > 0) {
	            first = mid + 1;
	        } else {
	            index= mid;
	            return  symbols[index].getMathMeaning();
	        }
	    }
		return "";
	}

	public static String engLetterToGreekPhoneme(char c) {
		int first = 0;
	    int last  = englishLettersSize;
	    int mid,index=0;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (c-englishLetters[mid].getLetter() < 0) {
	            last = mid;
	        } else if (c-englishLetters[mid].getLetter() > 0) {
	            first = mid + 1;
	        } else {
	            index= mid;
	            return  englishLetters[index].getToGreekPhoneme();
	        }
	    }
		return "";
	}

	public static String engLetterToGreekWord(char c) {
		int first = 0;
	    int last  = englishLettersSize;
	    int mid,index=0;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (c-englishLetters[mid].getLetter() < 0) {
	            last = mid;
	        } else if (c-englishLetters[mid].getLetter() > 0) {
	            first = mid + 1;
	        } else {
	            index= mid;
	            return  englishLetters[index].getToGreekLetters();
	        }
	    }
		return "";
	}

	public static String numberToGreekWord(char c){
		if(!Character.isDigit(c)) return "";
		return numbersNames[Character.getNumericValue(c)];
	}

	public static char toneTheGreekLetter(char c){
		switch(c){
			case 'α': return 'ά';
			case 'ε': return 'έ';
			case 'η': return 'ή';
			case 'ι': return 'ί';
			case 'ο': return 'ό';
			case 'υ': return 'ύ';
			case 'ω': return 'ώ';
			case 'Α': return 'Ά';
			case 'Ε': return 'Έ';
			case 'Η': return 'Ή';
			case 'Ι': return 'Ί';
			case 'Ο': return 'Ό';
			case 'Υ': return 'Ύ';
			case 'Ω': return 'Ώ';
			default: return c;
		}
	}



	//is FUNCTIONS USING BINARY SEARCH
	public static boolean isNumber(char c){
		return Character.isDigit(c);
	}

	public static boolean isSymbol(char c) {
		int first = 0;
	    int last  = symbolsSize;
	    int mid;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (c-symbols[mid].getSymbol() < 0) {
	            last = mid;
	        } else if (c-symbols[mid].getSymbol() > 0) {
	            first = mid + 1;
	        } else {
	            return true;
	        }
	    }
	    return false;
	}

	public static boolean isEnglishLetter(char c) {
		return Character.isLetter(c);
	}

	public static boolean isGreekLetter(char c) {
		int first = 0;
	    int last  = greekPhonemesSingleSize;
	    int mid;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (c-greekPhonemesSingle[mid].getPhoneme().charAt(0) < 0) {
	            last = mid;
	        } else if (c-greekPhonemesSingle[mid].getPhoneme().charAt(0) > 0) {
	            first = mid + 1;
	        } else {
	            return true;
	        }
	    }
	    return false;
	}

	public static boolean isGreekPhonemeDual(String s) {
		int first = 0;
	    int last  = greekPhonemesDualSize;
	    int mid;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (s.compareTo(greekPhonemesDual[mid].getPhoneme()) < 0) {
	            last = mid;
	        } else if (s.compareTo(greekPhonemesDual[mid].getPhoneme()) > 0) {
	            first = mid + 1;
	        } else {
	            return true;
	        }
	    }
	    return false;
	}

	public static boolean isGreekPhonemeSingle(String s) {
		int first = 0;
	    int last  = greekPhonemesSingleSize;
	    int mid;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (s.compareTo(greekPhonemesSingle[mid].getPhoneme()) < 0) {
	            last = mid;
	        } else if (s.compareTo(greekPhonemesSingle[mid].getPhoneme()) > 0) {
	            first = mid + 1;
	        } else {
	            return true;
	        }
	    }
	    return false;
	}

	public static boolean isGreekPhoneme(String s) {
		if (s.length() == 1) {
			int first = 0;
		    int last  = greekPhonemesSingleSize;
		    int mid;
		    while (first < last) {
		        mid = first + ((last - first) / 2);
		        if (s.compareTo(greekPhonemesSingle[mid].getPhoneme()) < 0) {
		            last = mid;
		        } else if (s.compareTo(greekPhonemesSingle[mid].getPhoneme()) > 0) {
		            first = mid + 1;
		        } else {
		            return true;
		        }
		    }
		    return false;
		}

		else if (s.length() == 2) {
			int first = 0;
		    int last  = greekPhonemesDualSize;
		    int mid;
		    while (first < last) {
		        mid = first + ((last - first) / 2);
		        if (s.compareTo(greekPhonemesDual[mid].getPhoneme()) < 0) {
		            last = mid;
		        } else if (s.compareTo(greekPhonemesDual[mid].getPhoneme()) > 0) {
		            first = mid + 1;
		        } else {
		            return true;
		        }
		    }
		    return false;
		}

		return false;
	}

	//This function checks if the character is a Greek or English letter
	public static boolean isLetter(char c) {
		//check if it is a Greek letter first
		int first = 0;
	    int last  = greekPhonemesSingleSize;
	    int mid;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (c-greekPhonemesSingle[mid].getPhoneme().charAt(0) < 0) {
	            last = mid;
	        } else if (c-greekPhonemesSingle[mid].getPhoneme().charAt(0) > 0) {
	            first = mid + 1;
	        } else {
	            return true;
	        }
	    }

	    //then check if it is an English letter
	    return Character.isLetter(c);
	}

}













