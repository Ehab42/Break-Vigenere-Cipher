package t1;

import java.util.ArrayList;

public class VigenereCipherBreaker implements CipherBreaker {

	String text;

	@Override
	public void setCipherText(String text) {
		// TODO Auto-generated method stub
		this.text = text;
	}

	@Override
	public int computeKeyLength() {
		// TODO Auto-generated method stub

		// Array of ciphertext partitions according to the key
		String[] parts;
		ArrayList<Integer> Keys = new ArrayList<>();
		ArrayList<Double> avgICs = new ArrayList<>();

		// Adding each possible key into "keys" arraylist
		for (int i = 2; i <= 22; i++) {
			Keys.add(i);
		}

		// Calculating average IC for each key
		for (int z = 0; z < Keys.size(); z++) {
			int k = 0;
			int Key = Keys.get(z);

			// Number of partitions equals key
			parts = new String[Key];

			for (int i = 0; i < parts.length; i++) {
				parts[i] = "";
			}

			// dividing ciohertext according to the key
			while (k < Key) {

				for (int i = k; i <= text.length() - 1; i = i + Key) {
					if (parts[k] == null) {
						
						parts[k] = "" + text.charAt(i);

					} else {
						
						parts[k] = parts[k] + text.charAt(i);
					}
				}
				
				k++;
			}

			// Calculating IC for each partition
			double[] ICs = new double[parts.length];
			for (int i = 0; i < parts.length; i++) {
				double ic = calcIC(parts[i]);
				ICs[i] = ic;
			}

			double sum = 0;
			for (int i = 0; i < ICs.length; i++) {
				sum = sum + ICs[i];
			}

			// Calculating average IC for each key
			double averageIC = sum / (ICs.length);
			avgICs.add(averageIC);

		}

		ArrayList<Integer> newKeys = new ArrayList<>();

		// ICs greater than 0.053
		for (int i = 0; i < avgICs.size(); i++) {
			if (avgICs.get(i) > 0.053) {
				newKeys.add(Keys.get(i));
			}
		}

		
		// Getting the key which have the most divisors
		int finalKey = 0;
		int max = 0;
		for (int i = 0; i < newKeys.size(); i++) {
			int k = newKeys.get(i);
			int b = 0;
			for (int j = 0; j < newKeys.size(); j++) {
				if (!(k == newKeys.get(j))) {
					if (newKeys.get(j) % k == 0) {
						b++;
					}
				}
				if (b > max) {
					max = b;
					finalKey = k;
				}
			}
		}
		
		// returning the key
		return finalKey;
	}

	public static double calcIC(String s) {

		int i;
		int N = 0;
		double sum = 0.0;
		double total = 0.0;
		s = s.toUpperCase();

		// initialize array of values to count frequency of each letter
		int[] values = new int[26];
		for (i = 0; i < 26; i++) {
			values[i] = 0;
		}

		// calculate frequency of each letter in s
		int ch;
		for (i = 0; i < s.length(); i++) {
			ch = s.charAt(i) - 65;
			if (ch >= 0 && ch < 26) {
				values[ch]++;
				N++;
			}
		}

		// calculate the sum of each frequency
		for (i = 0; i < 26; i++) {
			ch = values[i];
			sum = sum + (ch * (ch - 1));
		}

		// divide by N(N-1)
		total = sum / (N * (N - 1));

		// return the result
		return total;

	}

}
