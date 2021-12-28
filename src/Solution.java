import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Solution {
    private final ArrayList<String> binNumbers;

    public Solution(String fileName) { this.binNumbers = parse(fileName); }

    private ArrayList<String> parse(String filename) {
        ArrayList<String> binNumbers = new ArrayList<>();

        try {
            FileInputStream stream = new FileInputStream(filename);
            Scanner sc             = new Scanner(stream);

            while (sc.hasNext()) {
                binNumbers.add(sc.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return binNumbers;
    }

    private int bin2Dec(ArrayList<Character> bitSequence) {
        int dec = 0;

        for (int i=0; i < bitSequence.size(); ++i) {
            dec += (bitSequence.get(i) - '0') * Math.pow(2, bitSequence.size() - 1 - i);
        }

        return dec;
    }

    private ArrayList<Character> getLifeSupport(ArrayList<String> binNumbers, int index, boolean getOxygen) {
        if (binNumbers.size() == 1) {
            ArrayList<Character> bits = new ArrayList<>();
            for (Character c : binNumbers.get(0).toCharArray()) { bits.add(c); }
            return bits;
        } else {
            ArrayList<String> zeros = new ArrayList<>();
            ArrayList<String> ones  = new ArrayList<>();

            for (String number : binNumbers) {
                if (number.charAt(index) == '1') { ones.add(number); }
                else { zeros.add(number); }
            }

            if (getOxygen) {
                if (zeros.size() > ones.size()) { return getLifeSupport(zeros, ++index, true); }
                else { return getLifeSupport(ones, ++index, true); }
            } else {
                if (ones.size() < zeros.size()) { return getLifeSupport(ones, ++index, false); }
                else { return getLifeSupport(zeros, ++index, false); }
            }
        }
    }

    public void solveP1() {
        ArrayList<Character> gammaRate  = new ArrayList<>();
        int numOnes;
        int numZeros;

        for (int i=0; i < this.binNumbers.get(0).length(); ++i) {
            numOnes  = 0;
            numZeros = 0;

            for (String binNumber : this.binNumbers) {
                if (binNumber.charAt(i) == '1') { numOnes++; }
                else { numZeros++; }
            }

            if (numOnes > numZeros) { gammaRate.add('1'); }
            else { gammaRate.add('0'); }
        }

        int gammaValue  = bin2Dec(gammaRate);
        int MaxValue    = (int) Math.pow(2, binNumbers.get(0).length()) - 1;
        int epsilonRate = MaxValue - gammaValue;

        System.out.println("Power Consumption: " + (gammaValue * epsilonRate));
    }

    public void solveP2() {
        int oxygenRating = bin2Dec(getLifeSupport(this.binNumbers, 0, true));
        int co2Rating    = bin2Dec(getLifeSupport(this.binNumbers, 0, false));

        System.out.println("Life support rating: " + (co2Rating * oxygenRating));
    }
}