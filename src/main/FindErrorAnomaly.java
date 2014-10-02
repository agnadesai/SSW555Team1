package main;

import java.util.Date;
import java.util.TreeMap;

public class FindErrorAnomaly {

	public static boolean hasmorethanonespouse(TreeMap<String, Individual> i,
			TreeMap<String, Family> fam, Individual indi) {
		for (String s : indi.getSpouseOfFamilyIDs()) {
			for (String t : indi.getSpouseOfFamilyIDs()) {
				if (!fam.get(s).equals(fam.get(t))) {
					//System.out.println(" s = " + fam.get(s));
					//System.out.println(" t = " + fam.get(t));

					Date md = fam.get(s).getMarried();
					Date dd = fam.get(t).getDivorced();
					if (dd != null) {
						if (md.before(dd)) {
							System.out
									.println(indi.getName()
											+ " Married to another person before taking divorce");

						}
						return true;

					} else {
						System.out.println(indi.getName()
								+ "  is married more than person at same time");

						return true;
					}

				}
			}
		}
		return false;

	}
}
